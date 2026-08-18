package com.banking.account.controller;

import com.banking.account.dto.AccountRequest;
import com.banking.account.dto.AccountUserResponse;
import com.banking.account.dto.BalanceResponse;
import com.banking.account.dto.UpdateBalanceRequest;
import com.banking.account.entity.Account;
import com.banking.account.service.AccountService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

	private final AccountService accountService;

	@PostMapping("/create")
	public Account createAccount(@RequestBody AccountRequest request) {

		return accountService.createAccount(request);
	}

	@PutMapping("/balance")
	public ResponseEntity<Account> updateBalance(@RequestBody UpdateBalanceRequest request) {

		Account account = accountService.updateBalance(request);

		return ResponseEntity.ok(account);
	}
	
	@PutMapping("/withdraw")
	public ResponseEntity<Account> withdrawBalance(@RequestBody UpdateBalanceRequest request) {

	    Account account = accountService.withdrawBalance(request);

	    return ResponseEntity.ok(account);
	}
	
	@DeleteMapping("/{accountNumber}")
	public ResponseEntity<String> deleteAccount(@PathVariable String accountNumber) {

	    return ResponseEntity.ok(accountService.deleteAccount(accountNumber));
	}
	
	@GetMapping("/{accountNumber}")
	public ResponseEntity<Account> getAccountByNumber(
	        @PathVariable String accountNumber) {

	    return ResponseEntity.ok(
	            accountService.getAccountByNumber(accountNumber));
	}
	
	@GetMapping("/balance/{accountNumber}")
	public ResponseEntity<BalanceResponse> getBalance(
	        @PathVariable String accountNumber) {

	    return ResponseEntity.ok(
	            accountService.getBalance(accountNumber));
	}
	@GetMapping("/{accountNumber}/user")
	public AccountUserResponse getAccountUser(
	        @PathVariable String accountNumber) {

	    return accountService.getAccountUser(accountNumber);
	}
}