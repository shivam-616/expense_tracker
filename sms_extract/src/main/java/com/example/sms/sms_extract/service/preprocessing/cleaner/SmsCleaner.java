package com.example.sms.sms_extract.service.preprocessing.cleaner;

import org.springframework.stereotype.Component;

@Component
public class SmsCleaner {

    public String clean(String sms) {

        sms = removeUrls(sms);

        sms = removeExtraSpaces(sms);

        sms = sms.replace("\n", " ")
                 .replace("\r", " ");

        return sms.trim();
    }

    private String removeUrls(String sms) {

        return sms.replaceAll("https?://\\S+", "");
    }

    private String removeExtraSpaces(String sms) {

        return sms.replaceAll("\\s+", " ");
    }
}
