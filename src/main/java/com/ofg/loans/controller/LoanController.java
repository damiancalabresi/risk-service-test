package com.ofg.loans.controller;

import com.ofg.loans.model.LoanApplication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/loan/")
public class LoanController {

    @RequestMapping(method = RequestMethod.POST, value = "approve/")
    public Boolean approveLoan(@RequestBody @Valid LoanApplication loanApplication) {
        return Boolean.TRUE;
    }

}
