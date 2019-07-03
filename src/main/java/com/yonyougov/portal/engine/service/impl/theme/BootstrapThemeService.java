package com.yonyougov.portal.engine.service.impl.theme;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yonyougov.portal.engine.ThemeOccupiedException;
import com.yonyougov.portal.engine.common.MsgConstant;
import com.yonyougov.portal.engine.dto.EngThemeDTO;
import com.yonyougov.portal.engine.entity.EngTheme;
import com.yonyougov.portal.engine.entity.EngThemeRefComp;
import com.yonyougov.portal.engine.entity.EngThemeRefCompUser;
import com.yonyougov.portal.engine.entity.EngThemeRefUser;
import com.yonyougov.portal.engine.service.EngThemeAbstractService;
import com.yonyougov.portal.engine.util.PageUtil;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author yindwe@yonyou.com
 * @Date 2019/7/2
 * @Description
 */
@Service
@Slf4j
public class BootstrapThemeService extends EngThemeAbstractService {
    /**
     * 将页面传过来的组件用占位符替换，并将替换的数据返回出来
     *
     * @param record
     * @return
     */
    private List<JSONObject> genDataBaseTemplateHtml(EngTheme record) {
        Document html = Jsoup.parse(record.getTemplate());
        Document editHtml = Jsoup.parse(record.getTemplateFull());
        //去除所有模板标识，并用占位符替换，返回出所有的模板
        List<JSONObject> jsonObjects = PageUtil.removeTemplatesReplaceAndReturnTemplates(html);
        PageUtil.removeTemplatesReplaceAndReturnTemplates(editHtml);
        //存储主题
        Element container = html.getElementsByClass(MsgConstant.CONTAINER).get(0);
        Elements lyrows = editHtml.getElementsByClass(MsgConstant.LYROW);
        record.setTemplate(container.outerHtml());
        record.setTemplateFull(lyrows.outerHtml());
        log.info("组装完后的数据为:{}", record);
        return jsonObjects;
    }

    protected void updateToBackstage(EngTheme record, List<JSONObject> jsonObjects) {
        log.info("执行更新开始------>{}", record);
        updateByPrimaryKeyWithBLOBs(record);
        //检查主题是否被占用，抛出异常
        checkIfThemeUsed(record.getId());
        //修改之前关联的comp
        updateEngThemeRefComp(record.getId(), jsonObjects);
    }

    private void saveToBackstage(EngTheme record, List<JSONObject> jsonObjects) {
        log.info("执行新增开始------>{}", record);
        record.setDefaultTheme("N");
        String themeId = saveTheme(record);
        record.setId(themeId);
        //存储用户与主题之间的对应关系表
        saveEngThemeRefComp(record.getId(), jsonObjects);
    }

    private String saveTheme(EngTheme theme) {
        engThemeMapper.insert(theme);
        return theme.getId();
    }

    private void saveEngThemeRefComp(String themeId, List<JSONObject> jsonObjects) {
        jsonObjects.forEach(p -> {
            String parentId = p.getString(MsgConstant.LAYOUTID);
            Element element = (Element) p.get(MsgConstant.COMP);
            EngThemeRefComp engThemeRefComp = new EngThemeRefComp();
            engThemeRefComp.setCompid(element.attr(MsgConstant.COMP_ID));
            engThemeRefComp.setThemeId(themeId);
            engThemeRefComp.setParentId(parentId);
            //icon和url不做处理，交给组件设计器进行
//            engThemeRefComp.setIcon("待定");
//            engThemeRefComp.setUrl(element.attr(MsgConstant.DATA_INTERFACE));
            engThemeRefCompMapper.insert(engThemeRefComp);
        });
    }



    private void checkIfThemeUsed(String themeId) {
        List<EngThemeRefUser> engThemeRefUserList = engThemeRefUserMapper.selectByThemeId(themeId);
        if(engThemeRefUserList.size() != 0){
            throw new ThemeOccupiedException("主题已被使用");
        }
    }

    private void updateEngThemeRefComp(String id, List<JSONObject> jsonObjects) {
        List<EngThemeRefComp> engThemeRefComps = engThemeRefCompMapper.selectByThemeId(id);
        jsonObjects.forEach(jsonObject -> engThemeRefComps.forEach(engThemeRefComp -> {
            if(engThemeRefComp.getParentId().equalsIgnoreCase(jsonObject.getString(MsgConstant.LAYOUTID))){
                engThemeRefComp.setCompid(jsonObject.getString(MsgConstant.COMP_ID));
            }
        }));
        engThemeRefCompMapper.updateBatch(engThemeRefComps);
    }

    @Override
    public void insertToFront(EngThemeDTO record, String userId) {
        //存储用户与主题关联关系
        EngThemeRefUser engThemeRefUser = saveThemeRefUser(record.getThemeId(), userId);
        //清除之前用户的主题
        removeOldThemeRefUser(engThemeRefUser.getId());
        saveThemeRefCompUser(engThemeRefUser, record);
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

    @Override
    protected void saveToBackstage(EngTheme record) {
        List<JSONObject> jsonObjects = genDataBaseTemplateHtml(record);
        saveToBackstage(record,jsonObjects);
    }

    @Override
    protected void updateToBackstage(EngTheme record) {

    }
}
