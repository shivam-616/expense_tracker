package com.example.expensetracker.expenceservice.requestDTO;

import java.math.BigDecimal;

public record CategoryInsightDto(
        String category,
        BigDecimal totalSpent
) {
}
