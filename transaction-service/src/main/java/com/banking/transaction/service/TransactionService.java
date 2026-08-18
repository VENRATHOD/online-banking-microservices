package com.banking.transaction.service;

import com.banking.transaction.client.AccountClient;
import com.banking.transaction.client.AuthClient;
import com.banking.transaction.dto.AccountUserResponse;
import com.banking.transaction.dto.DepositRequest;
import com.banking.transaction.dto.TransactionEvent;
import com.banking.transaction.dto.TransferRequest;
import com.banking.transaction.dto.UpdateBalanceRequest;
import com.banking.transaction.dto.UserEmailResponse;
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
    private final AuthClient authClient;
    private final KafkaProducerService kafkaProducerService;


    // =====================================================
    // Transaction History
    // =====================================================

    public List<Transaction> getTransactionHistory(String accountNumber) {

        return transactionRepository
                .findByAccountNumberOrderByTransactionDateDesc(accountNumber);
    }


    // =====================================================
    // Get User Email
    // =====================================================

    private String getUserEmail(String accountNumber) {

        // 1. Get user ID from Account Service
        AccountUserResponse accountUser =
                accountClient.getAccountUser(accountNumber);

        // 2. Get email from Auth Service
        UserEmailResponse userEmail =
                authClient.getUserEmail(accountUser.getUserId());

        return userEmail.getEmail();
    }


    // =====================================================
    // Deposit
    // =====================================================

    public Transaction deposit(DepositRequest request) {

        // 1. Update account balance
        UpdateBalanceRequest balanceRequest =
                new UpdateBalanceRequest();

        balanceRequest.setAccountNumber(
                request.getAccountNumber()
        );

        balanceRequest.setAmount(
                request.getAmount()
        );

        accountClient.updateBalance(balanceRequest);


        // 2. Create transaction
        Transaction transaction =
                new Transaction();

        transaction.setAccountNumber(
                request.getAccountNumber()
        );

        transaction.setTransactionType(
                "DEPOSIT"
        );

        transaction.setAmount(
                request.getAmount()
        );

        transaction.setTransactionDate(
                LocalDateTime.now()
        );


        // 3. Save transaction
        Transaction savedTransaction =
                transactionRepository.save(transaction);


        // 4. Get user email
        String email =
                getUserEmail(
                        savedTransaction.getAccountNumber()
                );


        // 5. Create Kafka event
        TransactionEvent event =
                new TransactionEvent(
                        savedTransaction.getAccountNumber(),
                        savedTransaction.getTransactionType(),
                        savedTransaction.getAmount(),
                        email
                );


        // 6. Send Kafka event
        kafkaProducerService.sendTransactionEvent(event);


        // 7. Return transaction
        return savedTransaction;
    }


    // =====================================================
    // Withdraw
    // =====================================================

    public Transaction withdraw(WithdrawRequest request) {

        // 1. Prepare balance request
        UpdateBalanceRequest balanceRequest =
                new UpdateBalanceRequest();

        balanceRequest.setAccountNumber(
                request.getAccountNumber()
        );

        balanceRequest.setAmount(
                request.getAmount()
        );


        // 2. Withdraw money
        accountClient.withdrawBalance(
                balanceRequest
        );


        // 3. Create transaction
        Transaction transaction =
                new Transaction();

        transaction.setAccountNumber(
                request.getAccountNumber()
        );

        transaction.setTransactionType(
                "WITHDRAW"
        );

        transaction.setAmount(
                request.getAmount()
        );

        transaction.setTransactionDate(
                LocalDateTime.now()
        );


        // 4. Save transaction
        Transaction savedTransaction =
                transactionRepository.save(transaction);


        // 5. Get user email
        String email =
                getUserEmail(
                        savedTransaction.getAccountNumber()
                );


        // 6. Create Kafka event
        TransactionEvent event =
                new TransactionEvent(
                        savedTransaction.getAccountNumber(),
                        savedTransaction.getTransactionType(),
                        savedTransaction.getAmount(),
                        email
                );


        // 7. Send Kafka event
        kafkaProducerService.sendTransactionEvent(event);


        // 8. Return transaction
        return savedTransaction;
    }


    // =====================================================
    // Transfer
    // =====================================================

    public Transaction transfer(TransferRequest request) {

        // 1. Withdraw from sender
        UpdateBalanceRequest withdrawRequest =
                new UpdateBalanceRequest();

        withdrawRequest.setAccountNumber(
                request.getFromAccount()
        );

        withdrawRequest.setAmount(
                request.getAmount()
        );

        accountClient.withdrawBalance(
                withdrawRequest
        );


        // 2. Deposit into receiver
        UpdateBalanceRequest depositRequest =
                new UpdateBalanceRequest();

        depositRequest.setAccountNumber(
                request.getToAccount()
        );

        depositRequest.setAmount(
                request.getAmount()
        );

        accountClient.updateBalance(
                depositRequest
        );


        // 3. Create transaction
        Transaction transaction =
                new Transaction();

        transaction.setAccountNumber(
                request.getFromAccount()
        );

        transaction.setTransactionType(
                "TRANSFER"
        );

        transaction.setAmount(
                request.getAmount()
        );

        transaction.setTransactionDate(
                LocalDateTime.now()
        );


        // 4. Save transaction
        Transaction savedTransaction =
                transactionRepository.save(transaction);


        // 5. Get sender email
        String email =
                getUserEmail(
                        savedTransaction.getAccountNumber()
                );


        // 6. Create Kafka event
        TransactionEvent event =
                new TransactionEvent(
                        savedTransaction.getAccountNumber(),
                        savedTransaction.getTransactionType(),
                        savedTransaction.getAmount(),
                        email
                );


        // 7. Send Kafka event
        kafkaProducerService.sendTransactionEvent(event);


        // 8. Return transaction
        return savedTransaction;
    }
}