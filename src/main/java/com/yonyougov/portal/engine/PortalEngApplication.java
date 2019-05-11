package com.yonyougov.portal.engine;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan(basePackages = "com.yonyougov.portal.engine.mapper")
@EnableTransactionManagement
@EnableAspectJAutoProxy
public class PortalEngApplication {

    public static void main(String[] args) {
        SpringApplication.run(PortalEngApplication.class, args);
    }

}
