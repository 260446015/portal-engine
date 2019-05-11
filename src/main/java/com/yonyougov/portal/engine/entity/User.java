package com.yonyougov.portal.engine.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author yindwe@yonyu.com
 * @Date 2019/5/6 18:48
 * @Description
 */
@Data
@Accessors(chain = true)
public class User implements Serializable {
    private static final long serialVersionUID = -971557400012775274L;
    private String id;
}
