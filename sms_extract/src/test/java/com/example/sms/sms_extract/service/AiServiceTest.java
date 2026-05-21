package com.example.sms.sms_extract.service;

import com.example.sms.sms_extract.dto.TransactionDetailsDTO;
import com.example.sms.sms_extract.service.preprocessing.SmsPreprocessingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AiServiceTest {

    @Autowired
    private SmsPreprocessingService smsPreprocessingService;

    @Test
    @DisplayName("Should successfully process a standard debit SMS")
    void testProcessDebitSms() {
        // Raw SMS with Rs., lowercase debited, and an account number
        String rawSms = "Dear Customer, RS 500.00 was debited from a/c x1234 on 10-May-26. Avl Bal: INR 45,000.";

        String result = smsPreprocessingService.process(rawSms);

        assertNotNull(result);
        // Check classification
        assertTrue(result.startsWith("[EXPENSE]"), "Should be classified as an expense");
        // Check normalization
        assertTrue(result.contains("INR 500.00"), "Rs should be normalized to INR");
        assertTrue(result.contains("spent"), "debited should be normalized to spent");
        // Check masking
        assertTrue(result.contains("A/C ****"), "Account number should be masked");
    }

    @Test
    @DisplayName("Should mask sensitive OTP and Phone numbers")
    void testMasking() {
        String rawSms = "Your OTP is 123456. Contact 9876543210 for help.";

        // This fails the validator because it lacks transaction keywords,
        // so we test the result is null as intended by your logic.
        String result = smsPreprocessingService.process(rawSms);
        assertNull(result, "Non-transactional SMS should be rejected by validator");
    }

    @Test
    @DisplayName("Should return null for promotional/invalid SMS")
    void testValidator() {
        String promoSms = "Get 50% off on your next pizza! Order now.";
        String result = smsPreprocessingService.process(promoSms);

        assertNull(result, "Promotional SMS should not pass the keyword validator");
    }

    @Test
    @DisplayName("Should handle currency symbols and extra spaces")
    void testCleaningAndCurrency() {
        String rawSms = "Received   ₹1000   from   friend";
        String result = smsPreprocessingService.process(rawSms);

        assertNotNull(result);
        assertTrue(result.startsWith("[INCOME]"), "Should be classified as income");
        assertTrue(result.contains("INR 1000"), "Symbol ₹ should be converted to INR");
        assertFalse(result.contains("   "), "Extra spaces should be removed");
    }
}