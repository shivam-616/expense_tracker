package com.example.sms.sms_extract.service.preprocessing.validator;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SmsValidator {

    private static final List<String> KEYWORDS = List.of(
            "debited",
            "credited",
            "spent",
            "received",
            "upi",
            "payment",
            "transaction",
            "withdrawn"
    );

    public boolean isValid(String sms) {

        if (sms == null || sms.isBlank()) {
            return false;
        }

        String lower = sms.toLowerCase();

        return KEYWORDS.stream()
                .anyMatch(lower::contains);
    }
}
