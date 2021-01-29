package com.seedcup.backend.common.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TeamRequired {

    /**
     * 是否需要队长身份
     */
    boolean needLeader() default false;
}
