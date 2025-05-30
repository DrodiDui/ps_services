package com.kapitonau.authserver.config;

import com.kapitonau.authserver.common.AuthApplicationLogger;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

@Configuration
public class ApplicationLoggerConfig {

    @Bean
    public FilterRegistrationBean<AuthApplicationLogger> containerLoggingFilterRegBean() {
        FilterRegistrationBean<AuthApplicationLogger> bean = new FilterRegistrationBean<>();

        bean.setFilter(new AuthApplicationLogger());
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);

        return bean;
    }

}
