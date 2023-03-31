package com.liu.blog.config;

import com.liu.blog.handler.PageableHandlerInterceptor;
import com.liu.blog.handler.WebSecurityHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Bean
    public WebSecurityHandler getWebSecurityHandler(){
        return new WebSecurityHandler();
    }
    @Override
    public void addCorsMappings(CorsRegistry corsRegistry){
        corsRegistry.addMapping("/**")
                .allowCredentials(true)
                .allowedHeaders("*")
                .allowedMethods("*")
                .allowedOriginPatterns("*");
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(new PageableHandlerInterceptor());
        registry.addInterceptor(getWebSecurityHandler());
    }
}
