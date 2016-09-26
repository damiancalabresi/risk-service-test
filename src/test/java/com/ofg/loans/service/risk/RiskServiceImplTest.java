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

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Please add test cases for your risk service implementation.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = BeanConfig.class)
public class RiskServiceImplTest {

    private static String IP_ADDRESS_ONE = "192.168.1.1";
    private static String IP_ADDRESS_TWO = "192.168.1.2";
    private static String IP_ADDRESS_THREE = "192.168.1.3";
    private static String IP_ADDRESS_FOUR = "192.168.1.4";
    private static String IP_ADDRESS_FIVE = "192.168.1.5";

    @MockBean
    private TimeUtils timeUtils;

    @MockBean
    private HttpServletRequest httpServletRequest;

    @Autowired
    private RiskService service;

    @Before
    public void setUp() throws Exception {
        Mockito.when(timeUtils.getDateTime()).thenReturn(getLocalDateTimeFixedAfternoon());
        Mockito.when(httpServletRequest.getRemoteAddr()).thenReturn(IP_ADDRESS_ONE);
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
    public void testApproveBetweenZeroAndSixWithLessAmount() throws Exception {
        Mockito.when(timeUtils.getDateTime()).thenReturn(getLocalDateTimeFixed3oClock());
        LoanApplication loanApplication = getLoanApplicationCorrect();
        loanApplication.setAmount(RiskService.MAXIMUM_LOAN_AMOUNT-1);
        Boolean isApproved = service.approve(loanApplication);
        Assert.assertEquals(Boolean.TRUE, isApproved);
    }

    @Test
    public void testApproveBetweenZeroAndSix() throws Exception {
        Mockito.when(timeUtils.getDateTime()).thenReturn(getLocalDateTimeFixed3oClock());
        LoanApplication loanApplication = getLoanApplicationCorrect();
        Boolean isApproved = service.approve(loanApplication);
        Assert.assertEquals(Boolean.FALSE, isApproved);
    }

    @Test
    public void testApproveMaxAttemptADayExceeded() throws Exception {
        Mockito.when(httpServletRequest.getRemoteAddr()).thenReturn(IP_ADDRESS_TWO);
        LoanApplication loanApplication = getLoanApplicationCorrect();
        for (int i = 0; i < RiskService.MAXIMUM_ATTEMPTS_PER_IP_ADDRESS; i++) {
            Assert.assertEquals(Boolean.TRUE, service.approve(loanApplication));
        }
        Boolean isApproved = service.approve(loanApplication);
        Assert.assertEquals(Boolean.FALSE, isApproved);
    }

    @Test
    public void testApproveMaxAttemptADayExceededDifferentIP() throws Exception {
        Mockito.when(httpServletRequest.getRemoteAddr()).thenReturn(IP_ADDRESS_THREE);
        LoanApplication loanApplication = getLoanApplicationCorrect();
        for (int i = 0; i < RiskService.MAXIMUM_ATTEMPTS_PER_IP_ADDRESS; i++) {
            Assert.assertEquals(Boolean.TRUE, service.approve(loanApplication));
        }
        Mockito.when(httpServletRequest.getRemoteAddr()).thenReturn(IP_ADDRESS_FOUR);
        Boolean isApproved = service.approve(loanApplication);
        Assert.assertEquals(Boolean.TRUE, isApproved);
    }

    @Test
    public void testApproveMaxAttemptADayExceededDifferentDays() throws Exception {
        Mockito.when(httpServletRequest.getRemoteAddr()).thenReturn(IP_ADDRESS_FIVE);
        LoanApplication loanApplication = getLoanApplicationCorrect();
        for (int i = 0; i < RiskService.MAXIMUM_ATTEMPTS_PER_IP_ADDRESS; i++) {
            Assert.assertEquals(Boolean.TRUE, service.approve(loanApplication));
        }
        Mockito.when(timeUtils.getDateTime()).thenReturn(getLocalDateTimeFixedAfternoonOtherDay());
        Assert.assertEquals(Boolean.TRUE, service.approve(loanApplication));
    }

    private LocalDateTime getLocalDateTimeFixed3oClock() {
        return LocalDateTime.of(LocalDate.of(2016, 9, 15), LocalTime.of(3, 0));
    }

    private LocalDateTime getLocalDateTimeFixedAfternoon() {
        return LocalDateTime.of(LocalDate.of(2016, 9, 15), LocalTime.of(15, 0));
    }

    private LocalDateTime getLocalDateTimeFixedAfternoonOtherDay() {
        return getLocalDateTimeFixedAfternoon().withDayOfMonth(16);
    }

    private LoanApplication getLoanApplicationCorrect() {
        return new LoanApplication(new Client("James", "Norwe"), RiskService.MAXIMUM_LOAN_AMOUNT, 5);
    }

    private LoanApplication getLoanApplicationWithAmountExceeded() {
        LoanApplication loanApplication = getLoanApplicationCorrect();
        loanApplication.setAmount(RiskService.MAXIMUM_LOAN_AMOUNT+1);
        return loanApplication;
    }
}