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
public class VueThemeInputDTO implements Serializable {
    private static final long serialVersionUID = -5724825015149671004L;
    private String name;
    private Integer count;
    private List<String> roles;
    private List<Container> portletList;
    private List<InnerContainer> lists;
}
