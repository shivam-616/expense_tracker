package com.example.expensetracker.expenceservice.requestDTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NonNull;

import java.math.BigDecimal;
import java.sql.Timestamp;
@JsonIgnoreProperties(ignoreUnknown = true)
public record addDTO(
        // for manual entry from the user
       String userID,
        @NonNull String merchant,
        @NonNull String currency,
        @JsonProperty("external_id") String externalId,
        @NonNull BigDecimal amount,
        @NonNull String category,
        @NonNull @JsonProperty("timestamp") Timestamp date
        ) {}
