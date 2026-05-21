package com.example.sms.sms_extract.service.preprocessing.masker;

import org.springframework.stereotype.Component;

@Component
public class AccountMasker {

    public String mask(String sms) {

        return sms.replaceAll(
                "(?i)(a/c|account)\\s*[xX*]*\\d{4,}",
                "A/C ****"
        );
    }
}
