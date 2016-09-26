package com.ofg.loans.model;

import javax.validation.constraints.Min;

/**
 * Loan application entity.
 */
public class LoanApplication {

    private Client client;

    @Min(value = 1, message = "The amount should be greater than zero")
    private Integer amount;

    // In days
    @Min(value = 1, message = "The term should be greater than zero")
    private Integer term;

    public LoanApplication() {
    }

    public LoanApplication(Client client, Integer amount, Integer term) {
        this.client = client;
        this.amount = amount;
        this.term = term;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getTerm() {
        return term;
    }

    public void setTerm(Integer term) {
        this.term = term;
    }

}
