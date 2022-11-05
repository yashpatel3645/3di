package com.banking.three_di_testing.utils;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

public class TransactionInput {

    private AccountInput sourceAccount;

    private AccountInput targetAccount;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    @Positive(message = "Transfer amount must be positive")
    // Prevent fraudulent transfers attempting to abuse currency conversion errors
    @Min(value = 1, message = "Amount must be larger than 1")
    private double amount;

    private String reference;

    public TransactionInput() {}

    public AccountInput getSourceAccount() {
        return sourceAccount;
    }
    public void setSourceAccount(AccountInput sourceAccount) {
        this.sourceAccount = sourceAccount;
    }
    public AccountInput getTargetAccount() {
        return targetAccount;
    }
    public void setTargetAccount(AccountInput targetAccount) {
        this.targetAccount = targetAccount;
    }
    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }
    public String getReference() {
        return reference;
    }
    public void setReference(String reference) {
        this.reference = reference;
    }

    public LocalDateTime getStartDate(){
        return startDate;
    }

    public LocalDateTime getEndDate(){
        return endDate;
    }

    @Override
    public String toString() {
        return "TransactionInput{" +
                "sourceAccount=" + sourceAccount +
                ", targetAccount=" + targetAccount +
                ", amount=" + amount +
                ", reference='" + reference + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
