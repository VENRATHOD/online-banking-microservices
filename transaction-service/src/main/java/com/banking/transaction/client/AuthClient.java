package com.banking.transaction.client;

import com.banking.transaction.dto.UserEmailResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "auth-service")
public interface AuthClient {

    @GetMapping("/api/auth/email/{userId}")
    UserEmailResponse getUserEmail(
            @PathVariable("userId") Long userId
    );
}