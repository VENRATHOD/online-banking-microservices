package com.banking.account.service;

import com.banking.account.dto.AccountRequest;
import com.banking.account.dto.BalanceResponse;
import com.banking.account.dto.UpdateBalanceRequest;
import com.banking.account.entity.Account;
import com.banking.account.exception.AccountInactiveException;
import com.banking.account.exception.AccountNotFoundException;
import com.banking.account.exception.InsufficientBalanceException;
import com.banking.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AccountService {

	private final AccountRepository accountRepository;

	public Account createAccount(AccountRequest request) {

		Account account = new Account();

		account.setUserId(request.getUserId());
		account.setAccountType(request.getAccountType());
		account.setAccountNumber(String.valueOf(new Random().nextInt(900000000) + 100000000));
		account.setBalance(BigDecimal.ZERO);

		 account.setStatus("ACTIVE");
		 
		return accountRepository.save(account);

	}

	public Account updateBalance(UpdateBalanceRequest request) {

	    Account account = accountRepository.findByAccountNumber(request.getAccountNumber())
	            .orElseThrow(() -> new RuntimeException("Account not found"));

	    if (!"ACTIVE".equals(account.getStatus())) {
	        throw new AccountInactiveException("Account is inactive");
//	    	throw new AccountNotFoundException(
//	    	        "Account not found with account number: " + request.getAccountNumber());
	    }
	    
	    account.setBalance(account.getBalance().add(request.getAmount()));

	    return accountRepository.save(account);
	}
	
	public Account withdrawBalance(UpdateBalanceRequest request) {

	    Account account = accountRepository.findByAccountNumber(request.getAccountNumber())
	            .orElseThrow(() -> new RuntimeException("Account not found"));

	 // First check status
	    if (!"ACTIVE".equals(account.getStatus())) {
	        throw new AccountInactiveException("Account is inactive");
	    }

	    
	    if (account.getBalance().compareTo(request.getAmount()) < 0) {
	        //throw new RuntimeException("Insufficient Balance");
	    	throw new InsufficientBalanceException("Insufficient Balance");
	    }

	    account.setBalance(account.getBalance().subtract(request.getAmount()));

	    return accountRepository.save(account);
	}
	
	public String deleteAccount(String accountNumber) {

	    Account account = accountRepository.findByAccountNumber(accountNumber)
	            .orElseThrow(() -> new RuntimeException("Account not found"));

	    if ("INACTIVE".equals(account.getStatus())) {
	        throw new AccountInactiveException("Account already inactive");
	    }

	    account.setStatus("INACTIVE");

	    accountRepository.save(account);

	    return "Account deleted successfully";
	}
	
	public Account getAccountByNumber(String accountNumber) {

	    return accountRepository.findByAccountNumber(accountNumber)
	            //.orElseThrow(() -> new RuntimeException("Account not found"));
	    		.orElseThrow(() ->
	            new AccountNotFoundException("Account not found with account number: " + accountNumber));
	}
	
	public BalanceResponse getBalance(String accountNumber) {

	    Account account = accountRepository.findByAccountNumber(accountNumber)
	            //.orElseThrow(() -> new RuntimeException("Account not found"));
	    		.orElseThrow(() ->
	            new AccountNotFoundException("Account not found with account number: " + accountNumber));

	    return new BalanceResponse(
	            account.getAccountNumber(),
	            account.getBalance());
	}
}