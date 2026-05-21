package com.example.sms.sms_extract.service.preprocessing.normalizer;

import org.springframework.stereotype.Component;

@Component
public class CurrencyNormalizer {

    public String normalize(String sms) {

        return sms
                .replace("₹", "INR ")
                .replaceAll("(?i)rs\\.?\\s*", "INR ")
                .replaceAll("(?i)inr\\s*", "INR ");
    }
}
