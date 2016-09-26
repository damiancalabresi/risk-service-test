package com.ofg.loans.configuration;

import com.ofg.loans.controller.LoanController;
import com.ofg.loans.error.ErrorCustomController;
import com.ofg.loans.service.risk.RiskService;
import com.ofg.loans.service.risk.RiskServiceImpl;
import com.ofg.loans.utils.TimeUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by damian on 9/26/16.
 */
@Configuration
public class BeanConfig {

    @Bean
    public LoanController loanController() {
        return new LoanController();
    }

    @Bean
    public ErrorCustomController errorCustomController() {
        return new ErrorCustomController();
    }

    @Bean
    public RiskService riskService() {
        return new RiskServiceImpl();
    }

    @Bean
    public TimeUtils timeUtils() {
        return new TimeUtils();
    }

}
