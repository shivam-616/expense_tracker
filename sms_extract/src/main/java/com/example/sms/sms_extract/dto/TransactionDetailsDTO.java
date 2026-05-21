package com.example.sms.sms_extract.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TransactionDetailsDTO(
        @JsonProperty("is_transaction" ) boolean is_transaction,
        @JsonProperty("amount" ) Double amount,
        @JsonProperty("currency" ) String currency,
        @JsonProperty("merchant" ) String merchant,
        @JsonProperty("category" ) String category,
        @JsonProperty("payment_method" ) String payment_method,
        @JsonProperty("bank" ) String bank,
        @JsonProperty("account_last4" ) String account_last4,
        @JsonProperty("reference_id" ) String reference_id,
        @JsonProperty("timestamp" ) String timestamp,
        @JsonProperty("balance" ) int balance,
        @JsonProperty("status" ) String status,
        @JsonProperty("raw_sms" ) String raw_sms
) {
}
