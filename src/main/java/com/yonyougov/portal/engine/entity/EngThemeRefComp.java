package com.yonyougov.portal.engine.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author yindwe@yonyu.com
 * @Date 2019/5/7 16:37
 * @Description
 */
@ApiModel(value = "EngThemeRefComp")
@Data
public class EngThemeRefComp implements Serializable {
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键",hidden = true)
    private String id;

    /**
     * 主题id
     */
    @ApiModelProperty(value = "主题id")
    private String themeId;

    /**
     * 组件id
     */
    @ApiModelProperty(value = "组件id")
    private String compId;

    /**
     * 父类id
     */
    @ApiModelProperty(value = "父类id")
    private String parentId;

    /**
     * URL
     */
    @ApiModelProperty(value = "URL")
    private String url;

    /**
     * 组件图标
     */
    @ApiModelProperty(value = "组件图标")
    private String icon;

    private static final long serialVersionUID = 1L;
}