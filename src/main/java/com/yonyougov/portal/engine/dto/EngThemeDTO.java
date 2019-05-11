package com.yonyougov.portal.engine.dto;

import com.alibaba.fastjson.JSONArray;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author yindwe@yonyu.com
 * @Date 2019/5/7 19:47
 * @Description
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "前台插入页面数据传输对象")
public class EngThemeDTO implements Serializable {
    private static final long serialVersionUID = -8094985843428102778L;
    @ApiModelProperty(value = "主题id")
    private String themeId;
    @ApiModelProperty(value = "模板名称")
    private String name;
    @ApiModelProperty(value = "包含组件id和parentId的jsonArray对象",example = "[parentId:123,compId:abc]")
    private JSONArray innerData;
}
