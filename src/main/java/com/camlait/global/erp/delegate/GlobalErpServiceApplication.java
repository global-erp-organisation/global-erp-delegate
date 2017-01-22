package com.camlait.global.erp.delegate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class GlobalErpServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(GlobalErpServiceApplication.class, args);
    }
}
