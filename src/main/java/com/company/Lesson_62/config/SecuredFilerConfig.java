package com.company.Lesson_62.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecuredFilerConfig {

    @Autowired
    private JwtTokenFilter jwtTokenFilter;

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {

        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(jwtTokenFilter);
        bean.addUrlPatterns("/article/*");
        bean.addUrlPatterns("/profile/*");
        bean.addUrlPatterns("/region/*");
        bean.addUrlPatterns("/comment/*");
        bean.addUrlPatterns("/like/*");

        return bean;
    }

}
