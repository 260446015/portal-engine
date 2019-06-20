package com.yonyougov.portal.engine.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Author yindwe@yonyou.com
 * @Date 2019/6/20
 * @Description
 */
@Data
@Accessors(chain = true)
@ApiModel
public class EngThemeVO implements Serializable {

    private static final long serialVersionUID = -4661188143393530605L;
    @ApiModelProperty(value = "主题id，用于保存当前用户拖拽页面后调用的参数使用")
    private String themeId;
    @ApiModelProperty(value = "组装成功后前台显示的页面")
    private String successAssemblyHtml;
}
