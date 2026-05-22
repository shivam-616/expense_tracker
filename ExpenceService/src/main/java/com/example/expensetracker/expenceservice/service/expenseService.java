package com.example.expensetracker.expenceservice.service;


import com.example.expensetracker.expenceservice.entites.expense;
import com.example.expensetracker.expenceservice.repository.expenseRepo;
import com.example.expensetracker.expenceservice.requestDTO.addDTO;
import org.springframework.stereotype.Service;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Objects;

@Service
public class expenseService {

    private addDTO addDTO;
    private expense expense;
    private expenseRepo expenserepo;
    private final ObjectMapper objectMapper = new ObjectMapper();


    public  boolean saveExpense( addDTO entrydetail) {
        expense.setUserId(addDTO.userID(userId));
        expense.setCurrency(((Objects.isNull(entrydetail.curreny()))) ? entrydetail.curreny() : "INR");
        try {
            expenserepo.save(objectMapper.convertValue(addDTO, expense.class));
            return true;
        } catch (Exception e) {
            return false;


        }
    }

    public List<addDTO> getExpense(String userID) {
        List<expense> ls = expenserepo.findByUserId(userID);
        return objectMapper.convertValue(ls,
                new TypeReference<List<addDTO>>() {
                });
    }
//    public boolean updateexpense(String userID , addDTO entrydetail){
//        expense.setCurrency(((Objects.isNull(entrydetail.curreny())))? entrydetail.curreny(): "INR");
//        Optional<expense> expenseFoundOpt = expenserepo.findByUserIdAndExternalId(entrydetail.userID(), entrydetail.externalId());
//        if(expenseFoundOpt.isEmpty()){
//            return false;
//        }
//        expense expense = expenseFoundOpt.get();
//        expense.setAmount(entrydetail.amount());
//        expense.setMerchant(Strings.isNotBlank(entrydetail.merchant())?entrydetail.merchant():expense.getMerchant());
//        expense.setCurrency(Strings.isNotBlank(entrydetail.curreny())?entrydetail.merchant():expense.getCurrency());
//        expenserepo.save(expense);
//        return true;
//    }
}
