package com.example.expensetracker.expenceservice.repository;

import com.example.expensetracker.expenceservice.entites.expense;
import com.example.expensetracker.expenceservice.requestDTO.CategoryInsightDto;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface expenseRepo extends JpaRepository<expense, String > {

    Page<expense> findByUserId(String usesID , Pageable pageable);


    @Query("SELECT new com.example.expensetracker.expenceservice.requestDTO.CategoryInsightDto(e.category, SUM(e.amount)) " +
            "FROM expense e WHERE e.userId = :userId GROUP BY e.category ORDER BY SUM(e.amount) DESC")
    List<CategoryInsightDto> getCategoryInsights(@Param("userId") String userId);
    Optional<expense> findByUserIdAndExternalId(@NonNull String s, String s1);
}
