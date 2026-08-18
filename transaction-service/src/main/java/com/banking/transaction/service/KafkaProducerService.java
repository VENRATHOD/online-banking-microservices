package com.banking.transaction.service;

import com.banking.transaction.dto.TransactionEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    private static final String TOPIC = "bank-transactions";

    private final KafkaTemplate<String, TransactionEvent> kafkaTemplate;

    public void sendTransactionEvent(TransactionEvent event) {

        try {

            var result = kafkaTemplate
                    .send(
                            TOPIC,
                            event.getAccountNumber(),
                            event
                    )
                    .get(10, TimeUnit.SECONDS);

            System.out.println("=================================");
            System.out.println("✅ Kafka message sent successfully");
            System.out.println("Account: " + event.getAccountNumber());
            System.out.println("Type: " + event.getTransactionType());
            System.out.println("Amount: " + event.getAmount());
            System.out.println("Topic: " + result.getRecordMetadata().topic());
            System.out.println("Partition: " + result.getRecordMetadata().partition());
            System.out.println("Offset: " + result.getRecordMetadata().offset());
            System.out.println("=================================");

        } catch (Exception e) {

            System.err.println("=================================");
            System.err.println("❌ Kafka message FAILED");
            System.err.println("Account: " + event.getAccountNumber());
            System.err.println("Type: " + event.getTransactionType());
            System.err.println("Amount: " + event.getAmount());
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
            System.err.println("=================================");

            throw new RuntimeException("Failed to send Kafka transaction event", e);
        }
    }
}