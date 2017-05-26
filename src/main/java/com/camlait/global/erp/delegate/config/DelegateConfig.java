package com.camlait.global.erp.delegate.config;

import org.jasypt.util.password.StrongPasswordEncryptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class DelegateConfig {

    @Bean
    public StrongPasswordEncryptor passwordEncriptor(){
        return new StrongPasswordEncryptor();
    }
    
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
