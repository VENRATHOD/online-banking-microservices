package com.banking.transaction.controller;

import com.banking.transaction.dto.DepositRequest;
import com.banking.transaction.dto.TransferRequest;
import com.banking.transaction.dto.WithdrawRequest;
import com.banking.transaction.entity.Transaction;
import com.banking.transaction.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/deposit")
    public ResponseEntity<Transaction> deposit(@Valid @RequestBody DepositRequest request) {

        Transaction transaction = transactionService.deposit(request);

        return new ResponseEntity<>(transaction, HttpStatus.CREATED);
    }
    
    @PostMapping("/withdraw")
    public ResponseEntity<Transaction> withdraw(@Valid @RequestBody WithdrawRequest request) {

        Transaction transaction = transactionService.withdraw(request);

        return new ResponseEntity<>(transaction, HttpStatus.CREATED);
    }
    
    @PostMapping("/transfer")
    public ResponseEntity<Transaction> transfer(@Valid @RequestBody TransferRequest request) {

        Transaction transaction = transactionService.transfer(request);

        return new ResponseEntity<>(transaction, HttpStatus.CREATED);
    }
    
    @GetMapping("/history/{accountNumber}")
    public ResponseEntity<List<Transaction>> getTransactionHistory(
            @PathVariable String accountNumber) {

        return ResponseEntity.ok(
                transactionService.getTransactionHistory(accountNumber));
    }
    
}