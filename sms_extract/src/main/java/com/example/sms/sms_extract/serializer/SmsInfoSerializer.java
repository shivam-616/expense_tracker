package com.example.sms.sms_extract.serializer;

import com.example.sms.sms_extract.dto.TransactionDetailsDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

import static java.lang.reflect.Array.getByte;


public class SmsInfoSerializer implements Serializer<TransactionDetailsDTO> {

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        Serializer.super.configure(configs, isKey);
    }

    @Override
    public byte[] serialize(String s, TransactionDetailsDTO transactionDetailsDTO) {

        byte[] b = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            b = objectMapper.writeValueAsString(transactionDetailsDTO).getBytes();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return b;
    }

    @Override
    public void close() {
        Serializer.super.close();
    }
}
