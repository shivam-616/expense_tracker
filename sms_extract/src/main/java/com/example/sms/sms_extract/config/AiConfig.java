package com.example.sms.sms_extract.config;

import org.checkerframework.checker.units.qual.C;
import org.checkerframework.common.value.qual.BottomVal;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AiConfig {


    public ChatClient chatClient;

    @Bean
    public ChatClient chatClient(ChatClient.Builder builder) {
        return builder.build();
    }
}
