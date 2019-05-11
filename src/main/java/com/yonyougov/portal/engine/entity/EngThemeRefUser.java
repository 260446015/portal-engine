package com.yonyougov.portal.engine.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author yindwe@yonyu.com
 * @Date 2019/5/8 11:22
 * @Description
 */
@ApiModel(value = "EngThemeRefUser")
@Accessors(chain = true)
@Data
public class EngThemeRefUser implements Serializable {
    @ApiModelProperty(value = "null")
    private String id;

    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id")
    private String userId;

    /**
     * 主题id
     */
    @ApiModelProperty(value = "主题id")
    private String themeId;

    @ApiModelProperty(value = "null")
    private String active;

    private static final long serialVersionUID = 1L;
}