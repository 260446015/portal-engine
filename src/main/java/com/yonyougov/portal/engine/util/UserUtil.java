package com.yonyougov.portal.engine.util;

import com.yonyougov.portal.engine.entity.User;

/**
 * @author yindwe@yonyu.com
 * @Date 2019/5/6 18:49
 * @Description
 */
public class UserUtil {
    public static User getCurrentUser(){
        User user = new User();
        user.setId("aaaaaaaa");
        return user;
    }
}
