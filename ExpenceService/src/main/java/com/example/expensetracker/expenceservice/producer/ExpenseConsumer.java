package com.example.expensetracker.expenceservice.producer;

import com.example.expensetracker.expenceservice.requestDTO.addDTO;
import com.example.expensetracker.expenceservice.service.expenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ExpenseConsumer {
    private expenseService expenseService;

    @Autowired
    ExpenseConsumer(expenseService expenseService) {
        this.expenseService = expenseService;
    }

    @KafkaListener(topics = "${spring.kafka.topic-json.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void listen(@Payload addDTO eventData ,@Header(KafkaHeaders.RECEIVED_KEY) String userId) {
        try {addDTO enrichedData = new addDTO(
                userId, // <-- This ensures it is no longer NULL!
                eventData.merchant(),
                eventData.currency(),
                eventData.externalId(),
                eventData.amount(),
                eventData.category(),
                eventData.date()
        );

            // Todo: Make it transactional, and check if duplicate event (Handle idempotency)
            expenseService.saveExpense(enrichedData);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("AuthServiceConsumer: Exception is thrown while consuming kafka event");
        }
    }
}
