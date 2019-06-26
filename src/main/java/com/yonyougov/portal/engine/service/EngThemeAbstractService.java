package com.yonyougov.portal.engine.service;

import com.alibaba.fastjson.JSONObject;
import com.yonyougov.portal.engine.common.MsgConstant;
import com.yonyougov.portal.engine.entity.EngTheme;
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
            saveToBackstage(record, jsonObjects);
        } else {
            updateToBackstage(record, jsonObjects);
        }

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

    protected abstract void saveToBackstage(EngTheme record, List<JSONObject> jsonObjects);

    protected abstract void updateToBackstage(EngTheme record, List<JSONObject> jsonObjects);


}
