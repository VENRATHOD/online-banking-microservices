package com.banking.transaction.client;

import com.banking.transaction.dto.UpdateBalanceRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "account-service")
public interface AccountClient {

    @PutMapping("/api/accounts/balance")
    void updateBalance(@RequestBody UpdateBalanceRequest request);

    @PutMapping("/api/accounts/withdraw")
    void withdrawBalance(@RequestBody UpdateBalanceRequest request);
}