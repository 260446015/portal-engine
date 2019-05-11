package com.yonyougov.portal.engine.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author yindwe@yonyu.com
 * @Date 2019/4/28 17:36
 * @Description 页面实体类
 */
@ApiModel(value = "com.ufgov.eng.entity.EngPage")
@Data
public class EngPage implements Serializable {
    private static final long serialVersionUID = 6738151562622641436L;
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键", hidden = true)
    private String id;

    /**
     * 时间戳
     */
    @ApiModelProperty(value = "时间戳", hidden = true)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date ts;

    /**
     * 删除标志
     */
    @ApiModelProperty(value = "删除标志", hidden = true)
    private String dr;

    /**
     * 名称
     */
    @ApiModelProperty(value = "名称")
    private String name;

    /**
     * 类别
     */
    @ApiModelProperty(value = "类别")
    private String category;
}