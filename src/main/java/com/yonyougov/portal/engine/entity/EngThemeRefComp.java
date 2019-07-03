package com.yonyougov.portal.engine.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author yindwe@yonyou.com
 * @Date 2019/7/3
 * @Description
 */
@ApiModel(value = "com.yonyougov.portal.engine.entity.EngThemeRefComp")
@Data
public class EngThemeRefComp implements Serializable {
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
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
    private String compid;

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

    /**
     * x坐标
     */
    @ApiModelProperty(value = "x坐标")
    private String xpoint;

    /**
     * y坐标
     */
    @ApiModelProperty(value = "y坐标")
    private String ypoint;

    /**
     * 高度
     */
    @ApiModelProperty(value = "高度")
    private String height;

    /**
     * 宽度
     */
    @ApiModelProperty(value = "宽度")
    private String width;

    /**
     * 角标
     */
    @ApiModelProperty(value = "角标")
    private String index;

    /**
     * 可拖动标识
     */
    @ApiModelProperty(value = "可拖动标识")
    private String draggable;

    /**
     * 可调整大小标识
     */
    @ApiModelProperty(value = "可调整大小标识")
    private String resizable;

    private static final long serialVersionUID = 1L;
}