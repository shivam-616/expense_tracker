package com.example.expensetracker.userservice.consumer;


import com.example.expensetracker.userservice.entites.UserInfo;
import com.example.expensetracker.userservice.entites.userinfoDto;
import com.example.expensetracker.userservice.serviec.UserService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class AuthServiceConsumer {
    private UserService userservice;
    private ObjectMapper objectMapper;

    @KafkaListener(topics = "${spring.kafka.topic-json.name}" , groupId = "${spring.kafka.consumer.group-id}")
    public void listen(userinfoDto eventdata){
        try{
            //
           userservice.createOrUpdateUser(eventdata);
        }catch(Exception e){
            System.out.println(e);
        }
    }

}
