package com.example.expensetracker.userservice.deserialization;

import com.example.expensetracker.userservice.entites.userinfoDto;
import org.apache.kafka.common.serialization.Deserializer;
import tools.jackson.databind.ObjectMapper;

import java.util.Map;

public class UserInfoDeserializer implements Deserializer<userinfoDto> {
    @Override
    public void close() {
    }

    @Override
    public void configure(Map<String, ?> arg0, boolean arg1) {
    }

    @Override
    public userinfoDto deserialize(String arg0, byte[] arg1) {
        ObjectMapper mapper = new ObjectMapper();
        userinfoDto user = null;
        try {
            user = mapper.readValue(arg1, userinfoDto.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }
}
