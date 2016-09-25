package com.ofg.loans.model;

import com.ofg.loans.configuration.SwaggerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;

/**
 * Loan application entity.
 */
@SpringBootApplication
@Import(SwaggerConfig.class)
public class LoanApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(LoanApplication.class, args);
    }

}
