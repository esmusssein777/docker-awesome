package com.ligz.docker.log;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogRequestFilterConfiguration {
    @Bean
    @ConditionalOnMissingBean(LogRequestFilter.class)
    public LogRequestFilter initLogRequestFilter() {
        return new LogRequestFilter();
    }
}
