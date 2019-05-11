package com.yonyougov.portal.engine.util;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.yonyougov.portal.engine.common.MsgConstant;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yindwe@yonyu.com
 * @Date 2019/5/5 15:40
 * @Description
 */
public class PageUtil {

    public static JSONObject successPage(Page data) {
        JSONObject result = new JSONObject();
        result.put("dataList", data.getResult());
        result.put("totalNumber", data.getTotal());
        result.put("totalPage", data.getPages());
        result.put("pageNumber", data.getPageNum());
        return result;
    }

    public static List<JSONObject> removeTemplatesReplaceAndReturnTemplates(Document document) {
        List<JSONObject> result = new ArrayList<>();
        Elements comps = document.getElementsByAttribute(MsgConstant.COMP_ID);
        comps.forEach(c -> {
            JSONObject data = new JSONObject();
            data.put("parentId", c.parent().id());
            data.put("comp",c);
            result.add(data);
            c.replaceWith(new Element(MsgConstant.COMP_ID));
        });
        return result;
    }
}
