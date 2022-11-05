package com.banking.three_di_testing.utils;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

public class AccountInput {

    @NotBlank(message = "Sort code is mandatory")
    private String sortCode;

    @NotBlank(message = "Account number is mandatory")
    private String accountNumber;

    @NotBlank(message = "Start Date is mandatory")
    private String startDate;

    @NotBlank(message = "End Date is mandatory")
    private String endDate;

    public AccountInput() {}

    public String getSortCode() {
        return sortCode;
    }
    public void setSortCode(String sortCode) {
        this.sortCode = sortCode;
    }
    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "AccountInput{" +
                "sortCode='" + sortCode + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountInput that = (AccountInput) o;
        return Objects.equals(sortCode, that.sortCode) &&
                Objects.equals(accountNumber, that.accountNumber)&&
                Objects.equals(startDate, that.startDate)&&
                Objects.equals(endDate, that.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sortCode, accountNumber);
    }
}
