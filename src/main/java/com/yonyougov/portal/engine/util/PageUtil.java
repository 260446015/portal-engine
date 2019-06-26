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
            data.put(MsgConstant.LAYOUTID, c.parent().attr(MsgConstant.LAYOUTID));
            data.put(MsgConstant.COMP,c);
            data.put(MsgConstant.COMP_ID,c.attr(MsgConstant.COMP_ID));
            data.put(MsgConstant.DATA_INTERFACE,c.attr(MsgConstant.DATA_INTERFACE));
            data.put(MsgConstant.ICON,c.attr(MsgConstant.ICON));
            result.add(data);
            c.replaceWith(new Element(MsgConstant.COMP_ID));
        });
        return result;
    }
}
