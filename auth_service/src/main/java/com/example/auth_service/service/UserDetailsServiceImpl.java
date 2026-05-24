package com.example.auth_service.service;

import com.example.auth_service.entities.UserInfo;
import com.example.auth_service.eventProducer.UserInfoEvent;
import com.example.auth_service.eventProducer.UserInfoProducer;
import com.example.auth_service.model.UserInfoDto;
import com.example.auth_service.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Objects;
import java.util.UUID;

@Component
@AllArgsConstructor
@Data
public class UserDetailsServiceImpl implements UserDetailsService
{

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private final UserInfoProducer userInfoProducer;
    @Autowired


    private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {

        log.debug("Entering in loadUserByUsername Method...");
        UserInfo user = userRepository.findByUsername(username);
        if(user == null){
            log.error("Username not found: " + username);
            throw new UsernameNotFoundException("could not found user..!!");
        }
        log.info("User Authenticated Successfully..!!!");
        return new CustomUserDetails(user);
    }

    public UserInfo checkIfUserAlreadyExist(UserInfoDto userInfoDto){
        return userRepository.findByUsername(userInfoDto.getUsername());
    }
    public Boolean signupUser(UserInfoDto userInfoDto){
        // ValidationUtil.validateUserAttributes(userInfoDto);
        userInfoDto.setPassword(passwordEncoder.encode(userInfoDto.getPassword()));

        if(Objects.nonNull(checkIfUserAlreadyExist(userInfoDto))){
            return false;
        }

        // 1. Generate the UUID
        String userId = UUID.randomUUID().toString();

        // 2. Save to the Auth Database
        userRepository.save(new UserInfo(userId, userInfoDto.getUsername(), userInfoDto.getPassword(), new HashSet<>()));

        // 3. Construct the Kafka Event
        UserInfoEvent event =new UserInfoEvent();
        event.setUserId(userId); // <-- CHANGED: Simply use the userId string we generated above
        event.setFirstName(userInfoDto.getFirstName());
        event.setLastName(userInfoDto.getLastName());
        event.setEmail(userInfoDto.getEmail());
        event.setPhoneNumber(userInfoDto.getPhoneNumber());

        // 4. Send the event to Kafka!
        userInfoProducer.sendEventTokafka(event);

        return true;
    }
}