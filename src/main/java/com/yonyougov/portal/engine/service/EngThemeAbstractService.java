package com.yonyougov.portal.engine.service;

import com.alibaba.fastjson.JSONObject;
import com.yonyougov.portal.engine.common.MsgConstant;
import com.yonyougov.portal.engine.entity.EngTheme;
import com.yonyougov.portal.engine.entity.EngThemeRefComp;
import com.yonyougov.portal.engine.mapper.EngThemeRefCompMapper;
import com.yonyougov.portal.engine.util.PageUtil;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author yindwe@yonyou.com
 * @Date 2019/6/24
 * @Description
 */
@Slf4j
public abstract class EngThemeAbstractService {

    @Resource
    EngThemeRefCompMapper engThemeRefCompMapper;

    protected void saveOrUpdateTheme(EngTheme record) {
        log.info("开始进行数据处理，数据为：{}", record.toString());
        List<JSONObject> jsonObjects = genDataBaseTemplateHtml(record);
        if (StringUtils.isEmpty(record.getId())) {
            saveToBackstage(record);
        } else {
            updateToBackstage(record);
        }
        //存储用户与主题之间的对应关系表
        saveEngThemeRefComp(record.getId(), jsonObjects);
    }

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

    protected abstract void saveToBackstage(EngTheme record);

    protected abstract void updateToBackstage(EngTheme record);


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
}
