package com.banking.notification.kafka;

import com.banking.notification.service.EmailService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationKafkaConsumer {

    private final EmailService emailService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(
            topics = "bank-transactions",
            groupId = "notification-group"
    )
    public void consume(String message) {

        try {

            System.out.println("=================================");
            System.out.println("📩 Notification Service Received");
            System.out.println("Kafka Message: " + message);

            JsonNode json = objectMapper.readTree(message);

            String accountNumber =
                    json.get("accountNumber").asText();

            String transactionType =
                    json.get("transactionType").asText();

            String amount =
                    json.get("amount").asText();

            String email =
                    json.get("email").asText();

            System.out.println("Email: " + email);

            emailService.sendTransactionEmail(
                    email,
                    accountNumber,
                    transactionType,
                    amount
            );

            System.out.println("=================================");

        } catch (Exception e) {

            System.err.println("=================================");
            System.err.println("❌ Failed to process notification");
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
            System.err.println("=================================");
        }
    }
}