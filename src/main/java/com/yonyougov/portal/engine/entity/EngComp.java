package com.yonyougov.portal.engine.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author yindwe@yonyu.com
 * @Date 2019/5/7 16:39
 * @Description
 */
@ApiModel(value = "EngComp")
@Data
public class EngComp implements Serializable {
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键",hidden = true)
    private String id;

    /**
     * 时间戳
     */
    @ApiModelProperty(value = "时间戳",hidden = true)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date ts;

    /**
     * 删除标志
     */
    @ApiModelProperty(value = "删除标志",hidden = true)
    private String dr;

    /**
     * 组件名称
     */
    @ApiModelProperty(value = "组件名称")
    private String name;

    /**
     * 模板名称
     */
    @ApiModelProperty(value = "模板名称")
    private String templateName;

    /**
     * 接口调用地址
     */
    @ApiModelProperty(value = "接口调用地址")
    private String url;

    /**
     * 组件类型0bootstrap1vue
     */
    @ApiModelProperty(value = "组件类型0bootstrap 1vue")
    private String compType;

    /**
     * 组件图标
     */
    @ApiModelProperty(value = "组件图标")
    private String icon;

    /**
     * 模板
     */
    @ApiModelProperty(value = "模板")
    private String template;

    private static final long serialVersionUID = 1L;
}