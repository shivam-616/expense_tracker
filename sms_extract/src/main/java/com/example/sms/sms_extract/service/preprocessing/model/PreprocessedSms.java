package com.example.sms.sms_extract.service.preprocessing.model;

public class PreprocessedSms {

    private String rawSms;

    private String processedSms;

    private boolean transaction;

    public String getRawSms() {
        return rawSms;
    }

    public void setRawSms(String rawSms) {
        this.rawSms = rawSms;
    }

    public String getProcessedSms() {
        return processedSms;
    }

    public void setProcessedSms(String processedSms) {
        this.processedSms = processedSms;
    }

    public boolean isTransaction() {
        return transaction;
    }

    public void setTransaction(boolean transaction) {
        this.transaction = transaction;
    }
}
