package com.example.expensetracker.expenceservice.repository;

import com.example.expensetracker.expenceservice.entites.expense;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface expenseRepo extends JpaRepository<expense, String > {
    List<expense> findByUserId(String userID);

    Optional<expense> findByUserIdAndExternalId(@NonNull String s, String s1);
}
