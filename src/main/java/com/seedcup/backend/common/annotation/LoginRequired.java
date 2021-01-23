package com.seedcup.backend.common.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LoginRequired {

    /**
     * 是否需要管理员权限，默认不需要
     */
    boolean needAdmin() default false;
}
