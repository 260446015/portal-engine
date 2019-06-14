package com.yonyougov.portal.engine.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author yindwe@yonyou.com
 * @Date 2019/6/14
 * @Description
 */
@ApiModel(value = "com.yonyougov.portal.engine.entity.EngComp")
@Data
public class EngComp implements Serializable {
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private String id;

    /**
     * 时间戳
     */
    @ApiModelProperty(value = "时间戳")
    private Date ts;

    /**
     * 删除标志
     */
    @ApiModelProperty(value = "删除标志")
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

    @ApiModelProperty(value = "模板全部")
    private String templateFull;

    /**
     * 模板
     */
    @ApiModelProperty(value = "模板")
    private String template;

    /**
     * 接口调用地址
     */
    @ApiModelProperty(value = "接口调用地址")
    private String url;

    /**
     * 组件类型0bootstrap1vue
     */
    @ApiModelProperty(value = "组件类型0bootstrap1vue")
    private String compType;

    /**
     * 组件图标
     */
    @ApiModelProperty(value = "组件图标")
    private String icon;

    private static final long serialVersionUID = 1L;
}