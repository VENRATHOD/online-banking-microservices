package com.banking.account.dto;


import lombok.Data;


@Data
public class AccountRequest {

    private Long userId;

    private String accountType;
}