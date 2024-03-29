package com.seedcup.backend.global.config;

import com.seedcup.backend.common.dao.TeamMapper;
import com.seedcup.backend.common.dao.UserMapper;
import com.seedcup.backend.common.interceptor.AuthInterceptor;
import com.seedcup.backend.common.interceptor.TeamInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Resource
    private UserMapper userMapper;

    @Resource
    private TeamMapper teamMapper;

    /**
     * 添加拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthInterceptor(userMapper)).addPathPatterns("/**");
        registry.addInterceptor(new TeamInterceptor(teamMapper)).addPathPatterns("/**");
    }

    /**
     * 跨域支持
     *
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "DELETE", "PUT", "PATCH", "OPTIONS", "HEAD")
                .maxAge(3600 * 24);
    }
}
