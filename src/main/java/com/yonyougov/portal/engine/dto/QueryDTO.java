package com.yonyougov.portal.engine.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author yindwe@yonyu.com
 * @Date 2019/5/9 10:18
 * @Description
 */
@Data
@Accessors(chain = true)
public class QueryDTO implements Serializable {
    private static final long serialVersionUID = -302164908421759104L;
    private int pn;
    private int ps;
}
