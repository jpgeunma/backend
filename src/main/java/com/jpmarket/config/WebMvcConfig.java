package com.jpmarket.config;

import com.jpmarket.web.Interceptor.AuthInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        System.out.println("AuthInterceptor accepted");
        registry.addInterceptor(new AuthInterceptor()).addPathPatterns("/auth/signup");
    }

}