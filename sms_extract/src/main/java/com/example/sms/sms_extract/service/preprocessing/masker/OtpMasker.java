package com.example.sms.sms_extract.service.preprocessing.masker;

import org.springframework.stereotype.Component;

@Component
public class OtpMasker {

    public String mask(String sms) {

        return sms.replaceAll(
                "(?i)otp\\s*[:is-]*\\s*\\d{4,8}",
                "OTP [REMOVED]"
        );
    }
}
