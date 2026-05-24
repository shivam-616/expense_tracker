package com.example.expensetracker.expenceservice.deserialization;

import com.example.expensetracker.expenceservice.requestDTO.addDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;
import java.util.Map;

public class expenseDeserialization implements Deserializer<addDTO> {
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        Deserializer.super.configure(configs, isKey);
    }

    @Override
    public addDTO deserialize(String args0, byte[] args1) {
        ObjectMapper mapper = new ObjectMapper();
        addDTO  temp = null;

        try{
            temp  = mapper.readValue(args1 , addDTO.class);
        }catch(Exception e){
            e.printStackTrace();
        }
        return temp;
    }


    @Override
    public void close() {
        Deserializer.super.close();
    }
}
