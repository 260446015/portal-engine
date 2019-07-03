package com.yonyougov.portal.engine.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @Author yindwe@yonyou.com
 * @Date 2019/7/3
 * @Description
 */
@Data
@Accessors(chain = true)
public class Container implements Serializable {
    private static final long serialVersionUID = -1097344082335226850L;
    private String xpoint;
    private String ypoint;
    private String height;
    private String width;
    private String index;
    private boolean draggable;
    private boolean resizable;
    private boolean multiple;
    private List<InnerContainer> lists;
}
