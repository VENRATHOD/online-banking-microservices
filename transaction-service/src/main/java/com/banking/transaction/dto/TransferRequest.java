package com.banking.transaction.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TransferRequest {

    @NotBlank
    private String fromAccount;

    @NotBlank
    private String toAccount;

    @DecimalMin("1.0")
    private BigDecimal amount;
}