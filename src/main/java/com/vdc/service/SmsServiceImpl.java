package com.vdc.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vdc.exception.SmsNotFound;
import com.vdc.model.sms;
import com.vdc.repository.SmsRepository;

@Service
public class SmsServiceImpl implements SmsService {
	
	@Resource
	private SmsRepository smsRepository;

	@Override
	@Transactional
	public sms create(sms shop) {
		sms createdSms = shop;
		return smsRepository.save(createdSms);
	}
	
	@Override
	@Transactional
	public sms findById(int id) {
		return smsRepository.findOne(id);
	}

	@Override
	@Transactional(rollbackFor=SmsNotFound.class)
	public sms delete(int id) throws SmsNotFound {
		sms deletedSms = smsRepository.findOne(id);
		
		if (deletedSms == null)
			throw new SmsNotFound();
		
		smsRepository.delete(deletedSms);
		return deletedSms;
	}

	@Override
	@Transactional
	public List<sms> findAll() {
		return smsRepository.findAll();
	}

	@Override
	public sms update(sms shop) throws SmsNotFound {
		// TODO Auto-generated method stub
		return null;
	}

	/*@Override
	@Transactional(rollbackFor=SmsNotFound.class)
	public sms update(sms shop) throws SmsNotFound {
		sms updatedShop = shopRepository.findOne(shop.getId());
		
		if (updatedShop == null)
			throw new SmsNotFound();
		
		updatedShop.setName(shop.getName());
		updatedShop.setEmplNumber(shop.getEmplNumber());
		return updatedShop;
	}*/

}
