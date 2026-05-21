package com.example.sms.sms_extract.service.preprocessing;


import com.example.sms.sms_extract.service.preprocessing.classifier.SmsClassifier;
import com.example.sms.sms_extract.service.preprocessing.cleaner.SmsCleaner;
import com.example.sms.sms_extract.service.preprocessing.masker.AccountMasker;
import com.example.sms.sms_extract.service.preprocessing.masker.OtpMasker;
import com.example.sms.sms_extract.service.preprocessing.masker.PhoneMasker;
import com.example.sms.sms_extract.service.preprocessing.normalizer.BankTermNormalizer;
import com.example.sms.sms_extract.service.preprocessing.normalizer.CurrencyNormalizer;
import com.example.sms.sms_extract.service.preprocessing.validator.SmsValidator;
import org.springframework.stereotype.Service;

@Service
public class SmsPreprocessingService {

    private final SmsValidator smsValidator;
    private final SmsCleaner smsCleaner;
    private final OtpMasker otpMasker;
    private final PhoneMasker phoneMasker;
    private final AccountMasker accountMasker;
    private final CurrencyNormalizer currencyNormalizer;
    private final BankTermNormalizer bankTermNormalizer;
    private final SmsClassifier smsClassifier;

    public SmsPreprocessingService(
            SmsValidator smsValidator,
            SmsCleaner smsCleaner,
            OtpMasker otpMasker,
            PhoneMasker phoneMasker,
            AccountMasker accountMasker,
            CurrencyNormalizer currencyNormalizer,
            BankTermNormalizer bankTermNormalizer,
            SmsClassifier smsClassifier
    ) {
        this.smsValidator = smsValidator;
        this.smsCleaner = smsCleaner;
        this.otpMasker = otpMasker;
        this.phoneMasker = phoneMasker;
        this.accountMasker = accountMasker;
        this.currencyNormalizer = currencyNormalizer;
        this.bankTermNormalizer = bankTermNormalizer;
        this.smsClassifier = smsClassifier;
    }

    public String process(String sms) {

        if (!smsValidator.isValid(sms)) {
            return null;
        }

        sms = smsCleaner.clean(sms);

        sms = otpMasker.mask(sms);

        sms = phoneMasker.mask(sms);

        sms = accountMasker.mask(sms);

        sms = currencyNormalizer.normalize(sms);

        sms = bankTermNormalizer.normalize(sms);

        sms = smsClassifier.classify(sms);

        return sms;
    }
}
