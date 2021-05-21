package com.mekaku.ppic.config;

import com.mekaku.ppic.interceptor.LoginIntercepter;
import com.mekaku.ppic.interceptor.TokenIntercepter;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class IntercepterConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginIntercepter())
                .addPathPatterns("/**")
                .excludePathPatterns("/User/**")
                .excludePathPatterns("/Picture/PictureStream")
                .excludePathPatterns("/Picture/UpLoadPicture*");
        registry.addInterceptor(new TokenIntercepter()).addPathPatterns("/Picture/PictureStream");
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}