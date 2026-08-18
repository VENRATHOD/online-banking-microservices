package com.banking.notification.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendTransactionEmail(
            String email,
            String accountNumber,
            String transactionType,
            String amount) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(email);
        message.setSubject("BankPro Transaction Notification");

        message.setText(
                "Dear Customer,\n\n" +
                "Your BankPro transaction was completed successfully.\n\n" +
                "Account Number: " + accountNumber + "\n" +
                "Transaction Type: " + transactionType + "\n" +
                "Amount: ₹" + amount + "\n\n" +
                "Thank you for using BankPro.\n\n" +
                "Regards,\n" +
                "BankPro Team"
        );

        mailSender.send(message);

        System.out.println("=================================");
        System.out.println("📧 Email sent successfully");
        System.out.println("To: " + email);
        System.out.println("Transaction: " + transactionType);
        System.out.println("Amount: " + amount);
        System.out.println("=================================");
    }
}