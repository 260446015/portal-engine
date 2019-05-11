package com.yonyougov.portal.engine.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

/**
 * @author yindwe@yonyu.com
 * @Date 2019/5/7 16:38
 * @Description
 */
@ApiModel(value = "EngPageRefComp")
@Data
public class EngPageRefComp implements Serializable {
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private String id;

    /**
     * 关联page表主键
     */
    @ApiModelProperty(value = "关联page表主键")
    private String pageId;

    /**
     * 关联component表主键
     */
    @ApiModelProperty(value = "关联component表主键")
    private String compId;

    /**
     * 接口调用地址（优先级高于组件）
     */
    @ApiModelProperty(value = "接口调用地址（优先级高于组件）")
    private String url;

    private static final long serialVersionUID = 1L;
}