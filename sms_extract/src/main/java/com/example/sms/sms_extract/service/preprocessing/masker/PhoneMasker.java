package com.example.sms.sms_extract.service.preprocessing.masker;

import org.springframework.stereotype.Component;

@Component
public class PhoneMasker {

    public String mask(String sms) {

        return sms.replaceAll(
                "\\b\\d{10}\\b",
                "**********"
        );
    }
}
