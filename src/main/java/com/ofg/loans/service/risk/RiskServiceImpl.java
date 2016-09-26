package com.ofg.loans.service.risk;

import com.ofg.loans.model.LoanApplication;
import com.ofg.loans.utils.TimeUtils;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by damian on 9/26/16.
 */
public class RiskServiceImpl implements RiskService {

    private static LocalTime MIN_RISK_TIME = LocalTime.of(0,0);
    private static LocalTime MAX_RISK_TIME = LocalTime.of(6,0);

    private LocalDate lastDateIPChecked;
    private Map<String, Integer> requestsByIPAddress;

    @Autowired
    private TimeUtils timeUtils;

    @Autowired
    private HttpServletRequest request;

    public RiskServiceImpl() {
        this.requestsByIPAddress = new ConcurrentHashMap<>();
    }

    @Override
    public Boolean approve(LoanApplication loanApplication) {
        return checkAmount(loanApplication.getAmount())
            && checkRiskTime(loanApplication.getAmount())
            && checkMaxAttempts();
    }

    private Boolean checkAmount(Integer amount) {
        return amount <= RiskService.MAXIMUM_LOAN_AMOUNT;
    }

    private Boolean checkRiskTime(Integer amount) {
        LocalTime time = getTimeFromDateTime(timeUtils.getDateTime());
        Boolean hasRisk = (amount >= RiskService.MAXIMUM_LOAN_AMOUNT)
                && time.isAfter(MIN_RISK_TIME) && time.isBefore(MAX_RISK_TIME);
        return !hasRisk;
    }

    private LocalTime getTimeFromDateTime(LocalDateTime localDateTime) {
        return LocalTime.from(localDateTime);
    }

    private Boolean checkMaxAttempts() {
        if(this.lastDateIPChecked == null || !getLocalDate().isEqual(this.lastDateIPChecked)) {
            this.requestsByIPAddress.clear();
            this.lastDateIPChecked = getLocalDate();
        }
        String ipAddress = this.request.getRemoteAddr();
        this.requestsByIPAddress.merge(ipAddress, 1, (integer, integer2) -> integer+1);
        Integer count = this.requestsByIPAddress.getOrDefault(ipAddress, 0);
        return count<=RiskService.MAXIMUM_ATTEMPTS_PER_IP_ADDRESS;
    }

    private LocalDate getLocalDate() {
        return timeUtils.getDateTime().toLocalDate();
    }

}
