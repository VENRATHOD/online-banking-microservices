package com.banking.account.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class BalanceResponse {

    private String accountNumber;
    private BigDecimal balance;
}