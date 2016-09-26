package com.ofg.loans.configuration;

import com.ofg.loans.controller.LoanController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by damian on 9/26/16.
 */
@Configuration
@Import(SwaggerConfig.class)
public class BeanConfig {

    @Bean
    public LoanController loanController() {
        return new LoanController();
    }
}
