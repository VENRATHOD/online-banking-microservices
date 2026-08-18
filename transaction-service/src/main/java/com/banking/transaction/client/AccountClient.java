//package com.banking.transaction.client;
//
//import com.banking.transaction.dto.AccountUserResponse;
//import com.banking.transaction.dto.UpdateBalanceRequest;
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//
//@FeignClient(name = "account-service")
//public interface AccountClient {
//
//    @PutMapping("/api/accounts/balance")
//    void updateBalance(@RequestBody UpdateBalanceRequest request);
//
//    @PutMapping("/api/accounts/withdraw")
//    void withdrawBalance(@RequestBody UpdateBalanceRequest request);
//    @GetMapping("/api/accounts/{accountNumber}/user")
//    AccountUserResponse getAccountUser(@PathVariable("accountNumber") String accountNumber);
//}

package com.banking.transaction.client;

import com.banking.transaction.dto.AccountUserResponse;
import com.banking.transaction.dto.UpdateBalanceRequest;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "account-service")
public interface AccountClient {

    @PutMapping("/api/accounts/balance")
    void updateBalance(
            @RequestBody UpdateBalanceRequest request
    );

    @PutMapping("/api/accounts/withdraw")
    void withdrawBalance(
            @RequestBody UpdateBalanceRequest request
    );

    @GetMapping("/api/accounts/{accountNumber}/user")
    AccountUserResponse getAccountUser(
            @PathVariable("accountNumber") String accountNumber
    );
}