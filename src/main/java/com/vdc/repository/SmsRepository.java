package com.vdc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vdc.model.sms;

public interface SmsRepository extends JpaRepository<sms, Integer> {

}
