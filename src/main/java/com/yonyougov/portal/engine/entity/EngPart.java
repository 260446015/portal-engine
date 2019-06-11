package com.yonyougov.portal.engine.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author yindwe@yonyu.com
 * @Date 2019/5/5 9:28
 * @Description 基础组件表
 */
@ApiModel(value = "com.ufgov.eng.entity.EngPart")
@Data
public class EngPart implements Serializable {
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键",hidden = true)
    private String id;

    /**
     * 基础组件名称
     */
    @ApiModelProperty(value = "基础组件名称")
    private String name;

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
     * 模板名称
     */
    @ApiModelProperty(value = "模板名称")
    private String templateName;

    /**
     * 显示分类
     */
    @ApiModelProperty(value = "显示分类")
    private String category;

    /**
     * 模板
     */
    @ApiModelProperty(value = "模板")
    private String template;

    private static final long serialVersionUID = 1L;
}