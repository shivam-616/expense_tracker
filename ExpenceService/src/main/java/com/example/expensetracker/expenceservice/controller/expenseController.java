package com.example.expensetracker.expenceservice.controller;


import com.example.expensetracker.expenceservice.entites.expense;
import com.example.expensetracker.expenceservice.requestDTO.addDTO;
import com.example.expensetracker.expenceservice.service.expenseService;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@Controller
@RestController
@Data
@AllArgsConstructor
@NoArgsConstructor
@RequestMapping("/expense/v1")
public class expenseController {

    //need sevice for business logic

    private expenseService expenseService;
    private expense expense;

    public addDTO addDTO;


    //insight

    // basic crud

    // create
    @PostMapping("/addExpense")
    public ResponseEntity<Boolean> addexpense(@RequestHeader("X-User-Id") String userId, @RequestBody addDTO entrydetail) {
        try {
            return new ResponseEntity<>(expenseService.saveExpense(entrydetail), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }

    // read
    @GetMapping("/getExpense")
    public ResponseEntity<List<addDTO>> getentry(@RequestHeader("X-User-Id")@NonNull String userId) {
        try{
            List<addDTO> entries = expenseService.getExpense(userId);
            return new ResponseEntity<>(entries , HttpStatus.OK );
        }catch (Exception e){
            return new ResponseEntity<>((HttpHeaders) null, HttpStatus.NOT_FOUND);
        }
    }

    //update
    @PostMapping("/updateExpense")
    public ResponseEntity<Boolean>updateentry(@RequestHeader("X-User-Id")@NonNull String userId , @RequestBody addDTO entrydetail) {
        entrydetail.userID(userId);
        try{
            return new ResponseEntity<>(expenseService.updateexpense());
        }catch (Exception e){
            return new ResponseEntity<>(null , HttpStatus.NOT_FOUND);
        }
    }

}
