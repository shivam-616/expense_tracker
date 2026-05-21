package com.example.sms.sms_extract.service.preprocessing.normalizer;

import org.springframework.stereotype.Component;

@Component
public class BankTermNormalizer {

    public String normalize(String sms) {

        return sms
                .replaceAll("(?i)debited", "spent")
                .replaceAll("(?i)credited", "received")
                .replaceAll("(?i)txn", "transaction")
                .replaceAll("(?i)deducted", "spent");
    }
}
