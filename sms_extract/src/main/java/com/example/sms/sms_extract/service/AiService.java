package com.example.sms.sms_extract.service;


import com.example.sms.sms_extract.dto.TransactionDetailsDTO;
import com.example.sms.sms_extract.model.Sms;
import com.example.sms.sms_extract.producer.smsdataProducer;
import com.example.sms.sms_extract.repository.AiRepository;
import com.example.sms.sms_extract.service.preprocessing.SmsPreprocessingService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.ResponseEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AiService {
    private AiRepository aiRepository;
    private ChatClient chatClient;
    private SmsPreprocessingService smsPreprocessingService;
    private smsdataProducer smsdataProducer;
    // Standard constructor injection (Better than using both Lombok @AllArgs and @NoArgs)
    public AiService(AiRepository aiRepository, ChatClient chatClient, SmsPreprocessingService smsPreprocessingService) {
        this.aiRepository = aiRepository;
        this.chatClient = chatClient;
        this.smsPreprocessingService = smsPreprocessingService;
    }


    @Value("classpath:/templates/sms-extraction.st")
    private Resource resource;

    public void extract(String userId , String sms) {
        String cleanedSms = smsPreprocessingService.process(sms);
        if (cleanedSms == null) {
            throw new RuntimeException("Invalid or non-transaction SMS");
        }

        TransactionDetailsDTO result = chatClient.prompt()
                .user(u -> u.text(resource).param("sms_text", cleanedSms))
                .call().entity(TransactionDetailsDTO.class);

        smsdataProducer.sendEventToKafka( userId , result);
    }


}
