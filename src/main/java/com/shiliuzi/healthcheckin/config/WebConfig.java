package com.shiliuzi.healthcheckin.config;

import com.shiliuzi.healthcheckin.common.interceptor.JwtInterceptor;
import com.shiliuzi.healthcheckin.common.interceptor.LogInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final JwtInterceptor jwtInterceptor;
    private final LogInterceptor logInterceptor;

    @Autowired
    public WebConfig(
            JwtInterceptor jwtInterceptor,
            LogInterceptor logInterceptor) {
        this.jwtInterceptor = jwtInterceptor;
        this.logInterceptor = logInterceptor;

    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 日志拦截器应该最先注册，以便记录所有请求
        registry.addInterceptor(logInterceptor).addPathPatterns("/**");

        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/user/login")
                .excludePathPatterns("/user/register")
                .excludePathPatterns("/health");
    }
}
