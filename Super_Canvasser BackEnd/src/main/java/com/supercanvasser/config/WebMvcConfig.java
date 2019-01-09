package com.supercanvasser.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedHeaders("*")
                .exposedHeaders("Origin","X-Requested-With","Accept","Accept-Encoding","Accept-Language",
                        "Host","Referer","Connection","User-Agent","Authorization")
                .allowedMethods("*")
                .allowedOrigins("*");
    }
}
