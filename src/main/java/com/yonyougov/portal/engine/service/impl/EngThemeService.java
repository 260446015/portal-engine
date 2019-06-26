package com.yonyougov.portal.engine.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yonyougov.portal.engine.common.MsgConstant;
import com.yonyougov.portal.engine.dto.EngThemeDTO;
import com.yonyougov.portal.engine.dto.EngThemeVO;
import com.yonyougov.portal.engine.entity.*;
import com.yonyougov.portal.engine.mapper.*;
import com.yonyougov.portal.engine.service.EngThemeAbstractService;
import com.yonyougov.portal.engine.service.IEngThemeService;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yindwe@yonyu.com
 * @Date 2019/5/5 9:29
 * @Description
 */
@Service
@Slf4j
public class EngThemeService extends EngThemeAbstractService implements IEngThemeService {

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
    @Override
    public int deleteByPrimaryKey(String id) {
        engThemeRefCompMapper.deleteByThemeId(id);
        return engThemeMapper.deleteByPrimaryKey(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveOrUpdateToBackstage(EngTheme record) {
        //判断传入的数据是否有id，如果有id则进行数据更新；如果没有，则是新增
        saveOrUpdateTheme(record);
    }

    protected void saveToBackstage(EngTheme record, List<JSONObject> jsonObjects) {
        log.info("执行新增开始------>{}", record);
        record.setDefaultTheme("N");
        String themeId = saveTheme(record);
        record.setId(themeId);
        //存储用户与主题之间的对应关系表
        saveEngThemeRefComp(record.getId(), jsonObjects);
    }

    private void saveEngThemeRefComp(String themeId, List<JSONObject> jsonObjects) {
        jsonObjects.forEach(p -> {
            String parentId = p.getString(MsgConstant.LAYOUTID);
            Element element = (Element) p.get(MsgConstant.COMP);
            EngThemeRefComp engThemeRefComp = new EngThemeRefComp();
            engThemeRefComp.setCompid(element.attr(MsgConstant.COMP_ID));
            engThemeRefComp.setThemeId(themeId);
            engThemeRefComp.setIcon("待定");
            engThemeRefComp.setParentId(parentId);
            engThemeRefComp.setUrl(element.attr(MsgConstant.DATA_INTERFACE));
            engThemeRefCompMapper.insert(engThemeRefComp);
        });
    }

    public void updateToBackstage(EngTheme record, List<JSONObject> jsonObjects) {
        log.info("执行更新开始------>{}", record);
        updateByPrimaryKeyWithBLOBs(record);
        //删除掉之前关联的comp
        updateEngThemeRefComp(record.getId(), jsonObjects);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void insertToFront(EngThemeDTO record, String userId) {
        //存储用户与主题关联关系
        EngThemeRefUser engThemeRefUser = saveThemeRefUser(record.getThemeId(), userId);
        //清除之前用户的主题
        removeOldThemeRefUser(engThemeRefUser.getId());
        saveThemeRefCompUser(engThemeRefUser, record);
    }

    private void removeOldThemeRefUser(String themeUserId) {
        log.info("执行清除用户之前的布局，布局id为：{}", themeUserId);
        engThemeRefCompUserMapper.deleteByThemeUserId(themeUserId);
    }

    private void saveThemeRefCompUser(EngThemeRefUser engThemeRefUser, EngThemeDTO record) {
        //获取所有的组件与parentId的映射关系
        JSONArray innerData = record.getInnerData();
        //循环遍历出所有的数据并存储
        for (int i = 0; i < innerData.size(); i++) {
            JSONObject object = innerData.getJSONObject(i);
            String parentId = object.getString(MsgConstant.LAYOUTID);
            String themeId = engThemeRefUser.getThemeId();
            String compid = object.getString(MsgConstant.COMP_ID);
            EngThemeRefComp engThemeRefComp = engThemeRefCompMapper.selectByThemeIdAndCompid(themeId, compid);
//            EngComp engComp = engCompMapper.selectByPrimaryKey(compid);
            EngThemeRefCompUser engThemeRefCompUser = new EngThemeRefCompUser();
            engThemeRefCompUser.setId(engThemeRefComp.getId()).setThemeUserId(engThemeRefUser.getId()).setCompid(compid).setParentId(parentId);
//                    .setIcon("待定").setUrl(StringUtils.isEmpty(engThemeRefComp) ? engComp.getUrl() : engThemeRefComp.getUrl()).setParentId(parentId);
            engThemeRefCompUserMapper.insert(engThemeRefCompUser);
        }
    }


    private String saveTheme(EngTheme theme) {
        engThemeMapper.insert(theme);
        return theme.getId();
    }

    private EngThemeRefUser saveThemeRefUser(String themeId, String userId) {
        EngThemeRefUser refUser = null;
        List<EngThemeRefUser> engThemeRefUserList = engThemeRefUserMapper.selectByUserId(userId);
        //先判断有没有保存过的关联信息
        if (engThemeRefUserList.size() != 0) {
            for (EngThemeRefUser engThemeRefUser : engThemeRefUserList) {
                if (!engThemeRefUser.getThemeId().equals(themeId)) {
                    engThemeRefUser.setActive(MsgConstant.ACTIVE_FALSE);
                } else {
                    refUser = engThemeRefUser;
                }
            }
            engThemeRefUserMapper.updateBatch(engThemeRefUserList);
        }
        if (refUser == null) {
            refUser = new EngThemeRefUser();
            refUser.setThemeId(themeId).setUserId(userId).setActive(MsgConstant.ACTIVE_TRUE);
            engThemeRefUserMapper.insert(refUser);
        }
        return refUser;
    }

    @Override
    public EngThemeVO selectByUserIdForFront(String userId) {
        EngThemeVO vo = new EngThemeVO();
        //获取ENG_THEME_REF_USER表中用户启用的主题(查询T的时候数据库必定只有一条)
        List<EngThemeRefUser> engThemeRefUserList = engThemeRefUserMapper.selectByUserIdAndActive(userId, MsgConstant.ACTIVE_TRUE);
        EngTheme engTheme;
        if (engThemeRefUserList.size() == 0) {
            engTheme = engThemeMapper.selectByDefaultTheme(MsgConstant.ACTIVE_TRUE);
            Elements elements = selectByPrimaryKeyForFront(engTheme.getId());
            vo.setSuccessAssemblyHtml(elements.outerHtml()).setThemeId(engTheme.getId());
            return vo;
        }
        EngThemeRefUser engThemeRefUser = engThemeRefUserList.get(0);
        //获取当前用户使用的主题
        engTheme = engThemeMapper.selectByPrimaryKey(engThemeRefUser.getThemeId());
        Document html = Jsoup.parse(engTheme.getTemplate());
        Elements containers = html.getElementsByClass(MsgConstant.CONTAINER);
        String engThemeRefUserId = engThemeRefUser.getId();
        //获取当前用户当前主题下所有组件的信息并组装最后的页面
        getCurrentThemeWithCompsAndBuildTheme(engThemeRefUserId, containers);
        vo.setThemeId(engTheme.getId()).setSuccessAssemblyHtml(containers.outerHtml());
        return vo;
    }

    private Elements selectByPrimaryKeyForFront(String id) {
        EngTheme engTheme = engThemeMapper.selectByPrimaryKey(id);
        Assert.notNull(engTheme, MsgConstant.DATA_NOT_FOUNT);
        Document document = Jsoup.parse(engTheme.getTemplate());
        Elements containers = document.getElementsByClass(MsgConstant.CONTAINER);
        //从数据库中取出与之对应的组件并替换相应的组件
        getElementsByIdFromDbAndReplaceForBackstage(id, containers);
        return containers;
    }

    private void getCurrentThemeWithCompsAndBuildTheme(String engThemeRefUserId, Elements containers) {
        List<EngThemeRefCompUser> engThemeRefCompUsers = engThemeRefCompUserMapper.selectByThemeUserId(engThemeRefUserId);
        List<String> compIds = engThemeRefCompUsers.stream().map(EngThemeRefCompUser::getCompid).collect(Collectors.toList());
        List<EngComp> themeRefCompsFromDb = engCompMapper.getThemeRefCompsFromDb(compIds);
        Elements elementsByTag = containers.get(0).getElementsByTag(MsgConstant.COMP_ID);
        elementsByTag.forEach(element -> engThemeRefCompUsers.forEach(engThemeRefCompUser -> {
            if (null != element.parent()) {
                if (element.parent().id().equalsIgnoreCase(engThemeRefCompUser.getParentId())) {
                    EngComp engComp = themeRefCompsFromDb.stream().filter(p -> p.getId()
                            .equalsIgnoreCase(engThemeRefCompUser.getCompid())).findFirst().get();
                    Document portlet = Jsoup.parse(engComp.getTemplate());
                    Element portletElement = portlet.getElementsByClass(MsgConstant.PORTLET).get(0);
                    portletElement.attr(MsgConstant.DATA_INTERFACE, StringUtils.isEmpty(engThemeRefCompUser.getUrl()) ?
                            engComp.getUrl() : engThemeRefCompUser.getUrl());
                    portletElement.attr(MsgConstant.ID, engThemeRefCompUser.getId());
                    element.replaceWith(portletElement);
                    log.info("portletElement:--------->" + portletElement);
                }
            }
        }));
        log.info("containers:--------->" + containers);
    }

    public int updateByPrimaryKeySelective(EngTheme record) {
        return engThemeMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKeyWithBLOBs(EngTheme record) {
        return engThemeMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(EngTheme record) {
        return engThemeMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<EngTheme> listAll() {
        return engThemeMapper.listAll();
    }

    @Override
    public Elements selectByPrimaryKeyForBackstage(String id) {
        EngTheme engTheme = engThemeMapper.selectByPrimaryKey(id);
        Assert.notNull(engTheme, MsgConstant.DATA_NOT_FOUNT);
        Document document = Jsoup.parse(engTheme.getTemplateFull());
        Elements lyrows = document.getElementsByClass(MsgConstant.LYROW);
        //从数据库中取出与之对应的组件并替换相应的组件
        getElementsByIdFromDbAndReplaceForBackstage(id, lyrows);
        return lyrows;
    }

    private void getElementsByIdFromDbAndReplaceForBackstage(String id, Elements lyrows) {
        List<EngThemeRefComp> engThemeRefComps = engThemeRefCompMapper.selectByThemeId(id);
        List<String> ids = engThemeRefComps.stream().map(EngThemeRefComp::getCompid).collect(Collectors.toList());
        List<EngComp> engComps = engCompMapper.getThemeRefCompsFromDb(ids);
        engThemeRefComps.forEach(engThemeRefComp -> engComps.forEach(engComp -> {
            if (engThemeRefComp.getCompid().equalsIgnoreCase(engComp.getId())) {
                lyrows.forEach(lyrow -> {
                    Element parent = lyrow.getElementsByAttributeValue(MsgConstant.LAYOUTID, engThemeRefComp.getParentId()).first();
                    if (parent != null) {
                        Elements elements = parent.getElementsByTag(MsgConstant.COMP_ID);
                        if(elements.size() != 0){
                            Element element = elements.first();
                            Element portletFull = Jsoup.parse(engComp.getTemplate()).getElementsByClass(MsgConstant.PORTLET).get(0);
                            portletFull.attr(MsgConstant.DATA_INTERFACE, StringUtils.isEmpty(engThemeRefComp.getUrl())
                                    ? engComp.getUrl() : engThemeRefComp.getUrl());
                            portletFull.attr(MsgConstant.ID, engThemeRefComp.getId());
                            element.replaceWith(portletFull);
                        }
                    }
                });
            }
        }));
    }


    private void updateEngThemeRefComp(String id, List<JSONObject> jsonObjects) {
        List<EngThemeRefComp> engThemeRefComps = engThemeRefCompMapper.selectByThemeId(id);
        engThemeRefComps.forEach(engThemeRefComp -> jsonObjects.forEach(jsonObject -> {
            if(engThemeRefComp.getParentId().equalsIgnoreCase(jsonObject.getString(MsgConstant.LAYOUTID))){
                engThemeRefComp.setCompid(jsonObject.getString(MsgConstant.COMP_ID))
                        .setIcon(jsonObject.getString(MsgConstant.ICON))
                        .setUrl(jsonObject.getString(MsgConstant.DATA_INTERFACE));
            }
        }));
        engThemeRefCompMapper.updateBatch(engThemeRefComps);
    }

    public int insert(EngTheme record) {
        return engThemeMapper.insert(record);
    }

    public EngTheme selectByPrimaryKey(String id) {
        return engThemeMapper.selectByPrimaryKey(id);
    }

    public int updateBatch(List<EngTheme> list) {
        return engThemeMapper.updateBatch(list);
    }

    public int batchInsert(List<EngTheme> list) {
        return engThemeMapper.batchInsert(list);
    }
}

