package com.yonyougov.portal.engine.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author yindwe@yonyu.com
 * @Date 2019/4/28 14:38
 * @Description
 */
@Component
@ConfigurationProperties(prefix = "com.ufgov.config")
@Data
public class ConfigProperties {
    private Boolean enableSwagger;
}
