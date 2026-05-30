package com.example.expensetracker.expenceservice.controller;


import com.example.expensetracker.expenceservice.requestDTO.CategoryInsightDto;
import com.example.expensetracker.expenceservice.requestDTO.addDTO;
import com.example.expensetracker.expenceservice.service.expenseService;

import lombok.*;


import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // <-- Add this line to allow frontend testing
@RequestMapping("/expense/v1")
public class expenseController {

    //need sevice for business logic

    private final expenseService expenseService;


    //insight

    // basic crud

    // create
    @PostMapping("/addExpense")
    public ResponseEntity<Boolean> addexpense(@RequestHeader("X-User-Id") String userId, @RequestBody addDTO entrydetail) {
        addDTO userEntry = new addDTO(
                userId,
                entrydetail.merchant(),
                entrydetail.currency(),
                entrydetail.externalId(),
                entrydetail.amount(),
                entrydetail.category(),
                entrydetail.date()
        );
        try {
            return new ResponseEntity<>(expenseService.saveExpense(userEntry), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }

    // read
    @GetMapping("/getExpense")
    public ResponseEntity<List<addDTO>> getentry(@RequestHeader("X-User-Id") @NonNull String userId, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size, @RequestParam(defaultValue = "createdAt") String sortBy, // e.g., "amount" or "date"
                                                 @RequestParam(defaultValue = "desc") String sortDir) {
        try {
            Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                    Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

            Pageable pageable = PageRequest.of(page, size, sort);
            List<addDTO> entries = expenseService.getExpense(userId, pageable);
            return new ResponseEntity<>(entries, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>((HttpHeaders) null, HttpStatus.NOT_FOUND);
        }
    }

    //update
    //update
    @PostMapping("/updateExpense")
    public ResponseEntity<Boolean> updateentry(@RequestHeader("X-User-Id") @NonNull String userId, @RequestBody addDTO entrydetail) {

        // 1. Create a new record instance because Java records are immutable
        addDTO userEntry = new addDTO(
                userId,
                entrydetail.merchant(),
                entrydetail.currency(),
                entrydetail.externalId(),
                entrydetail.amount(),
                entrydetail.category(),
                entrydetail.date()
        );


        try {
            // 2. Pass the arguments to the service and include the HttpStatus
            return new ResponseEntity<>(expenseService.updateexpense(userId, userEntry), HttpStatus.OK);
        } catch (Exception e) {
            // 3. Return false instead of null for the Boolean response
            e.printStackTrace();
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/insight/category")
    public ResponseEntity<List<CategoryInsightDto>> getCategoryInsights(
            @RequestHeader("X-User-Id") String userId) {
        try {
            // Assuming you added a getCategoryInsights method in expenseService to call the repo!
            List<CategoryInsightDto> insights = expenseService.getinsight(userId);
            return new ResponseEntity<>(insights, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>((HttpHeaders) null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
