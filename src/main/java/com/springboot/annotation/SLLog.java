package com.springboot.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: chuan.bai
 * @Description
 * @Date: Created on 13:57 30/11/2017
 * @Modified By: 开启日志注解
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})//作用于方法
@Retention(RetentionPolicy.RUNTIME)//运行时有效
public @interface SLLog {

    boolean show() default true;

    String content() default "";
}
