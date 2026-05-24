package com.example.expensetracker.expenceservice.service;


import com.example.expensetracker.expenceservice.entites.expense;
import com.example.expensetracker.expenceservice.repository.expenseRepo;
import com.example.expensetracker.expenceservice.requestDTO.addDTO;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;


import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Data
public class expenseService {


    @Autowired
    private final expenseRepo expenserepo;
    private final ObjectMapper objectMapper = new ObjectMapper();


    public boolean saveExpense(addDTO entrydetail) {
        try {
            // 1. Create a fresh instance of the entity from the DTO
            expense newExpense = objectMapper.convertValue(entrydetail, expense.class);

            // 2. Set the currency
            newExpense.setCurrency(entrydetail.currency() != null ? entrydetail.currency() : "INR");
            newExpense.setUserId(entrydetail.userID()); // Ensure UserID is mapped
            newExpense.setCategory(entrydetail.category());
            // 3. Save it
            expenserepo.save(newExpense);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<addDTO> getExpense(String userID) {
        // 1. Fetch the data from the database
        List<expense> expenses = expenserepo.findByUserId(userID);

        // 2. Manually and safely map it to the DTO so Jackson doesn't crash!
        return expenses.stream().map(exp -> new addDTO(
                exp.getUserId(),
                exp.getMerchant() != null ? exp.getMerchant() : "Unknown",
                exp.getCurrency() != null ? exp.getCurrency() : "INR",
                exp.getExternalId(),
                exp.getAmount() != null ? exp.getAmount() : java.math.BigDecimal.ZERO,
                exp.getCategory() != null ? exp.getCategory() : "Uncategorized",
                exp.getCreatedAt()
        )).toList();
    }


    public boolean updateexpense(String userID, addDTO entrydetail) {
        Optional<expense> expenseFoundOpt = expenserepo.findByUserIdAndExternalId(userID, entrydetail.externalId());

        if (expenseFoundOpt.isEmpty()) {
            return false;
        }

        // Renamed variable to existingExpense to avoid shadowing the Entity class name
        expense existingExpense = expenseFoundOpt.get();
        existingExpense.setAmount(entrydetail.amount());

        // Replaced Strings.isNotBlank with standard Java String checks
        if (entrydetail.merchant() != null && !entrydetail.merchant().trim().isEmpty()) {
            existingExpense.setMerchant(entrydetail.merchant());
        }

        if (entrydetail.currency() != null && !entrydetail.currency().trim().isEmpty()) {
            existingExpense.setCurrency(entrydetail.currency());
        } else {
            existingExpense.setCurrency("INR");
        }

        expenserepo.save(existingExpense);
        return true;
    }
}
