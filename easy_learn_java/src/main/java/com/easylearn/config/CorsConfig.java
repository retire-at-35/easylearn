package com.easylearn.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    //使用CTRL+O 打开需要实现的接口方法

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        WebMvcConfigurer.super.addCorsMappings(registry);
        registry.addMapping("/**")
//                .allowedOrigins("*")
                .allowedOrigins("http://localhost:5173")
                .allowCredentials(true);
    }
}
