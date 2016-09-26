package com.ofg.loans.service.risk;

import com.ofg.loans.model.LoanApplication;
import com.ofg.loans.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by damian on 9/26/16.
 */
public class RiskServiceImpl implements RiskService {

    @Autowired
    private TimeUtils timeUtils;

    @Autowired
    private HttpServletRequest request;

    @Override
    public Boolean approve(LoanApplication loanApplication) {
        return Boolean.TRUE;
    }

}
