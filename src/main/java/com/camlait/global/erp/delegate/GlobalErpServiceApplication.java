package com.camlait.global.erp.delegate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Global application entry point.
 */
@SpringBootApplication
@EnableTransactionManagement
public  class GlobalErpServiceApplication {
    
    /**
     * Global application entry point.
     * @param args
     */
    public static void main(final String... args) {
        SpringApplication.run(GlobalErpServiceApplication.class, args);
    }
}
