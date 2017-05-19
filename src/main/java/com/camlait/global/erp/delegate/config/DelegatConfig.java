package com.camlait.global.erp.delegate.config;

import org.jasypt.util.password.StrongPasswordEncryptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DelegatConfig {

    @Bean
    public StrongPasswordEncryptor passwordEncriptor(){
        return new StrongPasswordEncryptor();
    }
}
