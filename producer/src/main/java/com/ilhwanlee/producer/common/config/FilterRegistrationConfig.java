package com.ilhwanlee.producer.common.config;

import com.ilhwanlee.producer.common.web.filter.gtid.GlobalTransactionIdFilter;
import com.ilhwanlee.producer.common.web.filter.logging.HttpLoggingFilter;
import jakarta.servlet.Filter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterRegistrationConfig {

    @Bean
    public FilterRegistrationBean<Filter> registerGlobalTransactionIdFilter() {
        FilterRegistrationBean<Filter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new GlobalTransactionIdFilter());
        bean.setOrder(1);
        bean.addUrlPatterns("/v1/*");
        return bean;
    }

    @Bean
    public FilterRegistrationBean<Filter> registerLoggingFilter() {
        FilterRegistrationBean<Filter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new HttpLoggingFilter());
        bean.setOrder(2);
        bean.addUrlPatterns("/v1/*");
        return bean;
    }
}
