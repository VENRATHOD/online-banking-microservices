//package com.banking.account.dto;
//
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import java.math.BigDecimal;
//
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//public class BalanceResponse {
//
//    private String accountNumber;
//    private BigDecimal balance;
//}
package com.banking.account.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class BalanceResponse {

    private String accountNumber;
    private BigDecimal balance;

    public BalanceResponse() {
    }

    @JsonCreator
    public BalanceResponse(
            @JsonProperty("accountNumber") String accountNumber,
            @JsonProperty("balance") BigDecimal balance) {

        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}