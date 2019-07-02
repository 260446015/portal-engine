package com.yonyougov.portal.engine.common;

import com.yonyougov.portal.engine.service.EngThemeAbstractService;
import com.yonyougov.portal.engine.service.impl.theme.BootstrapThemeService;
import com.yonyougov.portal.engine.service.impl.theme.VueThemeService;
import com.yonyougov.portal.engine.util.BeanNameContext;

/**
 * @Author yindwe@yonyou.com
 * @Date 2019/7/2
 * @Description
 */
public enum ThemeEnum {
    VUE(VueThemeService.class), BOOTSTRAP(BootstrapThemeService.class);
    private Class clazz;

    ThemeEnum(Class clazz) {
        this.clazz = clazz;
    }

    public static EngThemeAbstractService getTargetService(ThemeEnum themeEnum) {
        return (EngThemeAbstractService) BeanNameContext.getBean(themeEnum.clazz);
    }
}
