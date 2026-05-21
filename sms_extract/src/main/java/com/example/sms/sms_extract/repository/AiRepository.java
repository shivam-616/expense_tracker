package com.example.sms.sms_extract.repository;

import com.example.sms.sms_extract.model.Sms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AiRepository extends JpaRepository<Sms, Double> {

}
