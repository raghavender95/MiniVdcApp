package com.vdc.service;

import java.util.List;

import com.vdc.exception.SmsNotFound;
import com.vdc.model.sms;

public interface SmsService {
	
	public sms create(sms shop);
	public sms delete(int id) throws SmsNotFound;
	public List<sms> findAll();
	public sms update(sms shop) throws SmsNotFound;
	public sms findById(int id);

}
