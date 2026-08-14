package com.banking.account.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class UpdateBalanceRequest {

    private String accountNumber;
    private BigDecimal amount;
}