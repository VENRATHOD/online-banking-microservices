package com.banking.transaction.service;

import com.banking.transaction.client.AccountClient;
import com.banking.transaction.dto.DepositRequest;
import com.banking.transaction.dto.TransferRequest;
import com.banking.transaction.dto.UpdateBalanceRequest;
import com.banking.transaction.dto.WithdrawRequest;
import com.banking.transaction.entity.Transaction;
import com.banking.transaction.repository.TransactionRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountClient accountClient;

    // ===========================
    // Transaction History
    // ===========================
    public List<Transaction> getTransactionHistory(String accountNumber) {

        return transactionRepository
                .findByAccountNumberOrderByTransactionDateDesc(accountNumber);
    }

    // ===========================
    // Deposit
    // ===========================
    public Transaction deposit(DepositRequest request) {

        UpdateBalanceRequest balanceRequest = new UpdateBalanceRequest();

        balanceRequest.setAccountNumber(request.getAccountNumber());
        balanceRequest.setAmount(request.getAmount());

        accountClient.updateBalance(balanceRequest);

        Transaction transaction = new Transaction();

        transaction.setAccountNumber(request.getAccountNumber());
        transaction.setTransactionType("DEPOSIT");
        transaction.setAmount(request.getAmount());
        transaction.setTransactionDate(LocalDateTime.now());

        return transactionRepository.save(transaction);
    }

    // ===========================
    // Withdraw
    // ===========================
    public Transaction withdraw(WithdrawRequest request) {

        UpdateBalanceRequest balanceRequest = new UpdateBalanceRequest();

        balanceRequest.setAccountNumber(request.getAccountNumber());
        balanceRequest.setAmount(request.getAmount());

        accountClient.withdrawBalance(balanceRequest);

        Transaction transaction = new Transaction();

        transaction.setAccountNumber(request.getAccountNumber());
        transaction.setTransactionType("WITHDRAW");
        transaction.setAmount(request.getAmount());
        transaction.setTransactionDate(LocalDateTime.now());

        return transactionRepository.save(transaction);
    }

    // ===========================
    // Transfer
    // ===========================
    public Transaction transfer(TransferRequest request) {

        UpdateBalanceRequest withdrawRequest = new UpdateBalanceRequest();
        withdrawRequest.setAccountNumber(request.getFromAccount());
        withdrawRequest.setAmount(request.getAmount());

        accountClient.withdrawBalance(withdrawRequest);

        UpdateBalanceRequest depositRequest = new UpdateBalanceRequest();
        depositRequest.setAccountNumber(request.getToAccount());
        depositRequest.setAmount(request.getAmount());

        accountClient.updateBalance(depositRequest);

        Transaction transaction = new Transaction();

        transaction.setAccountNumber(request.getFromAccount());
        transaction.setTransactionType("TRANSFER");
        transaction.setAmount(request.getAmount());
        transaction.setTransactionDate(LocalDateTime.now());

        return transactionRepository.save(transaction);
    }
}