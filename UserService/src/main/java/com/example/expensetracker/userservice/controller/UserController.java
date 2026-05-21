package com.example.expensetracker.userservice.controller;

import com.example.expensetracker.userservice.entites.userinfoDto;
import com.example.expensetracker.userservice.serviec.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequiredArgsConstructor
public class UserController {
    private UserService userService;

    @PostMapping("/user/v1/createUpdate")
    public ResponseEntity<userinfoDto> createUpdateUser(userinfoDto eventdata){
        try {
            userinfoDto user = userService.createOrUpdateUser(eventdata);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
    @GetMapping("/user/v1/getUser")
    public ResponseEntity<userinfoDto> getUser(@RequestBody userinfoDto userInfoDto){
        try{
            userinfoDto user = userService.getUser(userInfoDto);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
