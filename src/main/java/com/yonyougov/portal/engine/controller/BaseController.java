package com.yonyougov.portal.engine.controller;

import com.alibaba.fastjson.JSONObject;
import com.yonyougov.portal.engine.dto.ApiResult;
import org.springframework.data.domain.Page;

public class BaseController {

    protected ApiResult success(Object obj) {
        return new ApiResult().setData(obj).setCode(0).setMessage("消息返回成功");
    }

    protected ApiResult error(String message) {
        return new ApiResult().setMessage(message).setCode(-1);
    }

    protected ApiResult successPages(Page<?> data) {
        JSONObject result = new JSONObject();
        result.put("dataList", data.getContent());
        result.put("totalNumber", data.getTotalElements());
        result.put("totalPage", data.getTotalPages());
        result.put("pageNumber", data.getNumber());
        return new ApiResult().setData(result).setCode(0).setMessage("消息返回成功");
    }
    protected ApiResult successPages(com.github.pagehelper.Page data) {
        JSONObject result = new JSONObject();
        result.put("dataList", data.getResult());
        result.put("totalNumber", data.getTotal());
        result.put("totalPage", data.getPages());
        result.put("pageNumber", data.getPageNum() - 1);
        return new ApiResult().setData(result).setCode(0).setMessage("消息返回成功");
    }
}
