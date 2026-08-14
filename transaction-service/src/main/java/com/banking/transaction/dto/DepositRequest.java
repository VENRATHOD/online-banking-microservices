package com.banking.transaction.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class DepositRequest {

    @NotBlank(message = "Account Number is required")
    private String accountNumber;

    @DecimalMin(value = "0.01", message = "Amount must be greater than zero")
    private BigDecimal amount;
}