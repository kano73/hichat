package com.hichat.mychat.config;

import com.hichat.mychat.service.StringGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyConfigBeans {
    @Bean
    public StringGenerator stringGenerator() {
        return new StringGenerator();
    }
}

