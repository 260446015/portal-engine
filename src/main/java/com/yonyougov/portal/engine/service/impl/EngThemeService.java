package com.yonyougov.portal.engine.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yonyougov.portal.engine.common.MsgConstant;
import com.yonyougov.portal.engine.dto.EngThemeDTO;
import com.yonyougov.portal.engine.util.PageUtil;
import com.yonyougov.portal.engine.entity.*;
import com.yonyougov.portal.engine.mapper.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yindwe@yonyu.com
 * @Date 2019/5/5 9:29
 * @Description
 */
@Service
public class EngThemeService {

    @Resource
    private EngThemeMapper engThemeMapper;
    @Resource
    private EngCompMapper engCompMapper;
    @Resource
    private EngThemeRefUserMapper engThemeRefUserMapper;
    @Resource
    private EngThemeRefCompUserMapper engThemeRefCompUserMapper;
    @Resource
    private EngThemeRefCompMapper engThemeRefCompMapper;

    @Transactional
    public int deleteByPrimaryKey(String id) {
        engThemeRefCompMapper.deleteByThemeId(id);
        return engThemeMapper.deleteByPrimaryKey(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public void insertToBackstage(EngTheme record) {
        Document html = Jsoup.parse(record.getTemplate());
        //去除所有模板标识，并用占位符替换，返回出所有的模板
        List<JSONObject> jsonObjects = PageUtil.removeTemplatesReplaceAndReturnTemplates(html);
        //存储用户主题
        record.setTemplate(html.outerHtml());
        String themeId = saveTheme(record);
        //存储用户与主题之间的对应关系表
        saveEngThemeRefComp(themeId, jsonObjects);
    }

    @Transactional(rollbackFor = Exception.class)
    public void insertToFront(EngThemeDTO record, String userId) {
        //存储用户与主题关联关系
        EngThemeRefUser engThemeRefUser = saveThemeRefUser(record.getThemeId(), userId);
        saveThemeRefCompUser(engThemeRefUser.getId(), record);
    }

    private void saveThemeRefCompUser(String themeRefUserId, EngThemeDTO record) {
        //获取所有的组件与parentId的映射关系
        JSONArray innerData = record.getInnerData();
        //循环遍历出所有的数据并存储
        for (int i = 0; i < innerData.size(); i++) {
            JSONObject object = innerData.getJSONObject(i);
            EngThemeRefCompUser engThemeRefCompUser = new EngThemeRefCompUser();
            engThemeRefCompUser.setThemeUserId(themeRefUserId).setCompId(object.getString(MsgConstant.COMP_ID))
                    .setIcon("待定").setUrl("待定").setParentId(object.getString(MsgConstant.PARENT_ID));
            engThemeRefCompUserMapper.insert(engThemeRefCompUser);
        }
    }

    private void saveEngThemeRefComp(String themeId, List<JSONObject> jsonObjects) {
        jsonObjects.forEach(p -> {
            String parentId = p.getString(MsgConstant.LAYOUTID);
            Element element = (Element) p.get("comp");
            EngThemeRefComp engThemeRefComp = new EngThemeRefComp();
            engThemeRefComp.setCompId(element.attr(MsgConstant.COMP_ID));
            engThemeRefComp.setThemeId(themeId);
            engThemeRefComp.setIcon("待定");
            engThemeRefComp.setParentId(parentId);
            engThemeRefComp.setUrl("待定");
            engThemeRefCompMapper.insert(engThemeRefComp);
        });
    }

    private String saveTheme(EngTheme theme) {
        engThemeMapper.insert(theme);
        return theme.getId();
    }

    private EngThemeRefUser saveThemeRefUser(String themeId, String userId) {
        List<EngThemeRefUser> engThemeRefUserList = engThemeRefUserMapper.selectByUserId(userId);
        engThemeRefUserList.forEach(engThemeRefUser -> engThemeRefUser.setActive("N"));
        if (engThemeRefUserList.size() != 0) engThemeRefUserMapper.updateBatch(engThemeRefUserList);
        EngThemeRefUser engThemeRefUser = new EngThemeRefUser();
        engThemeRefUser.setThemeId(themeId).setUserId(userId).setActive("Y");
        engThemeRefUserMapper.insert(engThemeRefUser);
        return engThemeRefUser;
    }

    public int insertSelective(EngTheme record) {
        return engThemeMapper.insertSelective(record);
    }

    public Document selectByPrimaryKeyForFront(String userId) {
        //获取ENG_THEME_REF_USER表中用户启用的主题(查询T的时候数据库必定只有一条)
        List<EngThemeRefUser> engThemeRefUserList = engThemeRefUserMapper.selectByUserIdAndActive(userId, "Y");
        if (engThemeRefUserList.size() == 0) { //TODO 当用户没有存储过主题时进行处理
            EngTheme engTheme = engThemeMapper.selectByDefaultTheme("Y");
            return selectByPrimaryKeyForBackstage(engTheme.getId());
        }
        EngThemeRefUser engThemeRefUser = engThemeRefUserList.get(0);
        //获取当前用户使用的主题
        EngTheme engTheme = engThemeMapper.selectByPrimaryKey(engThemeRefUser.getThemeId());
        Document container = Jsoup.parse(engTheme.getTemplate());
        String engThemeRefUserId = engThemeRefUser.getId();
        //获取当前用户当前主题下所有组件的信息并组装最后的页面
        getCurrentThemeWithCompsAndBuildTheme(engThemeRefUserId, container);
        return container;
    }

    private void getCurrentThemeWithCompsAndBuildTheme(String engThemeRefUserId, Document container) {
        List<Element> result = new ArrayList<>();
        List<EngThemeRefCompUser> engThemeRefCompUsers = engThemeRefCompUserMapper.selectByThemeUserId(engThemeRefUserId);
        List<String> compIds = engThemeRefCompUsers.stream().map(EngThemeRefCompUser::getCompId).collect(Collectors.toList());
        List<EngComp> themeRefCompsFromDb = engCompMapper.getThemeRefCompsFromDb(compIds);
        Elements elementsByTag = container.getElementsByTag(MsgConstant.COMP_ID);
        elementsByTag.forEach(element -> engThemeRefCompUsers.forEach(engThemeRefCompUser -> {
            if (null != element.parent()) {
                if (element.parent().id().equalsIgnoreCase(engThemeRefCompUser.getParentId())) {
                    EngComp engComp = themeRefCompsFromDb.stream().filter(p -> p.getId()
                            .equalsIgnoreCase(engThemeRefCompUser.getCompId())).findFirst().get();
                    element.replaceWith(Jsoup.parse(engComp.getTemplate()).body());
                }
            }
        }));
    }

    public int updateByPrimaryKeySelective(EngTheme record) {
        return engThemeMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKeyWithBLOBs(EngTheme record) {
        return engThemeMapper.updateByPrimaryKeyWithBLOBs(record);
    }

    public int updateByPrimaryKey(EngTheme record) {
        return engThemeMapper.updateByPrimaryKey(record);
    }

    public List<EngTheme> listAll() {
        return engThemeMapper.listAll();
    }

    public Document selectByPrimaryKeyForBackstage(String id) {
        EngTheme engTheme = engThemeMapper.selectByPrimaryKey(id);
        Assert.notNull(engTheme, MsgConstant.DATA_NOT_FOUNT);
        Document container = Jsoup.parse(engTheme.getTemplate());
        //从数据库中取出与之对应的组件并替换相应的组件
        getElementsByIdFromDbAndReplaceForBackstage(id, container);
        return container;
    }

    private void getElementsByIdFromDbAndReplaceForBackstage(String id, Document container) {
        List<EngThemeRefComp> engThemeRefComps = engThemeRefCompMapper.selectByThemeId(id);
        List<String> ids = engThemeRefComps.stream().map(EngThemeRefComp::getCompId).collect(Collectors.toList());
        List<EngComp> engComps = engCompMapper.getThemeRefCompsFromDb(ids);
        engThemeRefComps.forEach(engThemeRefComp -> {
            engComps.forEach(engComp -> {
                if (engThemeRefComp.getCompId().equalsIgnoreCase(engComp.getId())) {
                    Element parent = container.getElementById(engThemeRefComp.getParentId());
                    parent.html(engComp.getTemplate());
                }

            });
        });
    }

    @Transactional
    public void updateToBackstage(String id, EngTheme record) {
        EngTheme engTheme = engThemeMapper.selectByPrimaryKey(id);
        Document html = Jsoup.parse(record.getTemplate());
        //去除所有模板标识，并用占位符替换，返回出所有的模板
        List<JSONObject> jsonObjects = PageUtil.removeTemplatesReplaceAndReturnTemplates(html);
        //存储用户主题
        engTheme.setTemplate(html.outerHtml());
        engThemeMapper.updateByPrimaryKeyWithBLOBs(engTheme);
        //删除掉之前关联的comp
        deleteEngThemeRefComp(id);
        //存储用户与主题之间的对应关系表
        saveEngThemeRefComp(id, jsonObjects);

    }

    private int deleteEngThemeRefComp(String id) {
        return engThemeRefCompMapper.deleteByThemeId(id);
    }
}
