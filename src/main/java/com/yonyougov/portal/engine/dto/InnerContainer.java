package com.yonyougov.portal.engine.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @Author yindwe@yonyou.com
 * @Date 2019/7/3
 * @Description  处理多频道框架的内部消息体lists
 */
@Data
@Accessors(chain = true)
public class InnerContainer implements Serializable {
    private static final long serialVersionUID = 6771799551308405130L;
    private String type;
    private List<String> options;
    private String name;
}
