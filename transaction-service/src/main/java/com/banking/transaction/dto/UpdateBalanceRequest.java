package com.banking.transaction.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class UpdateBalanceRequest {

    private String accountNumber;
    private BigDecimal amount;
}