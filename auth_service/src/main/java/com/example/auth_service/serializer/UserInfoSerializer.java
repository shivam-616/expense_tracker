package com.example.auth_service.serializer;

import com.example.auth_service.eventProducer.UserInfoEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;


public class UserInfoSerializer implements Serializer<UserInfoEvent> {

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        Serializer.super.configure(configs, isKey);
    }

    @Override
    public byte[] serialize(String args, UserInfoEvent args1) {
        byte [] b = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            b = objectMapper.writeValueAsString(args1).getBytes();
        }catch(Exception e){
            System.out.println(e);
        }
        return b;
    }


    @Override
    public void close() {
        Serializer.super.close();
    }
}
