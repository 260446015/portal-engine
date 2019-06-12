package com.yonyougov.portal.engine.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author yindwe@yonyu.com
 * @Date 2019/4/28 17:35
 * @Description 基础组件和组件之间的中间表
 */
@ApiModel(value = "com.ufgov.eng.entity.EngCompRefPart")
@Data
public class EngCompRefPart implements Serializable {
    private static final long serialVersionUID = -7699457941824397882L;
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private String id;

    /**
     * 组件表id
     */
    @ApiModelProperty(value = "组件表id")
    private String compid;

    /**
     * 基础组件表id
     */
    @ApiModelProperty(value = "基础组件表id")
    private String partId;
}