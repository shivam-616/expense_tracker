package com.example.sms.sms_extract.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NonNull;


@Entity
@Data
public class Sms {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int sms_id;

    boolean is_transaction;
    Double amount;

    String currency;
    String merchant;
    String category;
    String payment_method;
    String bank;
    String account_last4;
    String reference_id;
    String timestamp;
    int balance;
    String status;
    String raw_sms;
}
