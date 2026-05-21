package com.example.sms.sms_extract.service.preprocessing.classifier;

import org.springframework.stereotype.Component;

@Component
public class SmsClassifier {

    public String classify(String sms) {

        String lower = sms.toLowerCase();

        if (lower.contains("spent")) {
            return "[EXPENSE] " + sms;
        }

        if (lower.contains("received")) {
            return "[INCOME] " + sms;
        }

        return "[UNKNOWN] " + sms;
    }
}
