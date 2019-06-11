package com.yonyougov.portal.engine.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author yindwe@yonyu.com
 * @Date 2019/5/7 16:25
 * @Description
 */
@ApiModel(value = "EngTheme")
@Data
public class EngTheme implements Serializable {
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键",hidden = true)
    private String id;

    /**
     * 主题名称
     */
    @ApiModelProperty(value = "主题名称")
    private String name;

    /**
     * 时间戳
     */
    @ApiModelProperty(value = "时间戳",hidden = true)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date ts;

    /**
     * 删除标记
     */
    @ApiModelProperty(value = "删除标记",hidden = true)
    private String dr;

    /**
     * 模板名称
     */
    @ApiModelProperty(value = "模板名称")
    private String templateName;

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
     * 模板
     */
    @ApiModelProperty(value = "模板")
    private String template;

    private static final long serialVersionUID = 1L;
}