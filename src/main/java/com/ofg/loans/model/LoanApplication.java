package com.ofg.loans.model;

/**
 * Loan application entity.
 */
public class LoanApplication {

    private Client client;

    private Integer amount;

    // In days
    private Integer term;

    public LoanApplication() {
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
