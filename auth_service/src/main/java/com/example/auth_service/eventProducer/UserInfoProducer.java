package com.example.auth_service.eventProducer;

import com.example.auth_service.model.UserInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class UserInfoProducer {
    private final KafkaTemplate<String, UserInfoDto> kafkaTemplate;

    @Value("${spring.kafka-json.name}")
    private String topicJsonName;


    @Autowired
    public UserInfoProducer(KafkaTemplate<String, UserInfoDto> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEventTokafka(UserInfoEvent userInfoEvent){
        Message<UserInfoEvent> message = MessageBuilder.withPayload(userInfoEvent).setHeader(KafkaHeaders.TOPIC , topicJsonName).build();
        kafkaTemplate.send(message);
    }

}
