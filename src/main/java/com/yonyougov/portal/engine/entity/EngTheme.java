package com.yonyougov.portal.engine.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author yindwe@yonyou.com
 * @Date 2019/6/14
 * @Description
 */
@ApiModel(value = "com.yonyougov.portal.engine.entity.EngTheme")
@Data
@Accessors(chain = true)
public class EngTheme implements Serializable {
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private String id;

    /**
     * 主题名称
     */
    @ApiModelProperty(value = "主题名称")
    private String name;

    /**
     * 时间戳
     */
    @ApiModelProperty(value = "时间戳")
    private Date ts;

    /**
     * 删除标记
     */
    @ApiModelProperty(value = "删除标记")
    private String dr;

    /**
     * 模板名称
     */
    @ApiModelProperty(value = "模板名称")
    private String templateName;

    /**
     * 模板
     */
    @ApiModelProperty(value = "模板")
    private String template;

    /**
     * URL
     */
    @ApiModelProperty(value = "URL")
    private String url;

    /**
     * 主题图标
     */
    @ApiModelProperty(value = "主题图标")
    private String icon;

    /**
     * 默认主题(N|Y)
     */
    @ApiModelProperty(value = "默认主题(N|Y)")
    private String defaultTheme;

    /**
     * 模板全部
     */
    @ApiModelProperty(value = "模板全部")
    private String templateFull;

    private static final long serialVersionUID = 1L;
}