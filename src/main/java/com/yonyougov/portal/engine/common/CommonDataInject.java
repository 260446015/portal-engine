package com.yonyougov.portal.engine.common;

import io.swagger.annotations.ApiModel;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Calendar;

/**
 * @author yindwe@yonyu.com
 * @Date 2019/5/5 14:42
 * @Description
 */
@Component
@Aspect
@Slf4j
public class CommonDataInject {

    @Pointcut("execution(* com.yonyougov.portal.engine.mapper.*Mapper.insert*(..))")
    private void insertCutMethod() {
    }

    @Pointcut("execution(* com.yonyougov.portal.engine.mapper.*Mapper.update*(..))")
    private void updateCutMethod() {
    }

    @Around("insertCutMethod()")
    public Object doInsertAround(ProceedingJoinPoint pjp) throws Throwable {
        Object[] args = pjp.getArgs();
        for (Object arg : args) {
            log.debug("[insert]" + arg);
            if (arg.getClass().getAnnotation(ApiModel.class) != null) {
                Field[] declaredFields = arg.getClass().getDeclaredFields();
                for (Field declaredField : declaredFields) {
                    if (declaredField.getName().equalsIgnoreCase("ts")) {
                        declaredField.setAccessible(true);
                        declaredField.set(arg, Calendar.getInstance().getTime());
                    } else if (declaredField.getName().equalsIgnoreCase("dr")) {
                        declaredField.setAccessible(true);
                        declaredField.set(arg, "N");
                    }
                }
//                Field id = arg.getClass().getDeclaredField("id");
//                id.setAccessible(true);
//                id.set(arg, UUID.randomUUID());
            }
        }
        return pjp.proceed();
    }

    @Around("updateCutMethod()")
    public Object doupdateAround(ProceedingJoinPoint pjp) throws Throwable {
        Object[] args = pjp.getArgs();
        for (Object arg : args) {
            log.debug("[update]" + arg);
        }
        return pjp.proceed();
    }
}
