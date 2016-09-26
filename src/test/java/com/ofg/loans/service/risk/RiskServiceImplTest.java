package com.ofg.loans.service.risk;

import com.ofg.loans.configuration.BeanConfig;
import com.ofg.loans.model.Client;
import com.ofg.loans.model.LoanApplication;
import com.ofg.loans.utils.TimeUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Please add test cases for your risk service implementation.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = BeanConfig.class)
public class RiskServiceImplTest {

    @MockBean
    private TimeUtils timeUtils;

    @Autowired
    private RiskService service;

    @Before
    public void setUp() throws Exception {
        Mockito.when(timeUtils.getDateTime()).thenReturn(getLocalDateTimeAfternoon());
    }

    @Test
    public void testApproveOk() throws Exception {
        Boolean isApproved = service.approve(getLoanApplicationCorrect());
        Assert.assertEquals(Boolean.TRUE, isApproved);
    }

    @Test
    public void testApproveAmountExceeded() throws Exception {
        Boolean isApproved = service.approve(getLoanApplicationWithAmountExceeded());
        Assert.assertEquals(Boolean.FALSE, isApproved);
    }

    @Test
    public void testApproveBetweenZeroAndSix() throws Exception {
        Mockito.when(timeUtils.getDateTime()).thenReturn(getLocalDateTime3oClock());
        LoanApplication loanApplication = getLoanApplicationCorrect();
        Boolean isApproved = service.approve(loanApplication);
        Assert.assertEquals(Boolean.FALSE, isApproved);
    }

    private LocalDateTime getLocalDateTime3oClock() {
        return LocalDateTime.of(LocalDate.now(), LocalTime.of(3, 0));
    }

    private LocalDateTime getLocalDateTimeAfternoon() {
        return LocalDateTime.of(LocalDate.now(), LocalTime.of(15, 0));
    }

    private LoanApplication getLoanApplicationCorrect() {
        return new LoanApplication(new Client("James", "Norwe"), RiskService.MAXIMUM_LOAN_AMOUNT/2, 5);
    }

    private LoanApplication getLoanApplicationWithAmountExceeded() {
        LoanApplication loanApplication = getLoanApplicationCorrect();
        loanApplication.setAmount(RiskService.MAXIMUM_LOAN_AMOUNT+1);
        return loanApplication;
    }
}