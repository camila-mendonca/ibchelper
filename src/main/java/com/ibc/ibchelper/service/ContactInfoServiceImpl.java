package com.ibc.ibchelper.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibc.ibchelper.entity.ContactInfo;
import com.ibc.ibchelper.repository.ContactInfoRepository;

@Service
public class ContactInfoServiceImpl implements ContactInfoService{

	@Autowired
	ContactInfoRepository infoRep;
	
	@Override
	public ContactInfo saveContactInfo(ContactInfo info) {
		return infoRep.save(info);		
	}

	@Override
	public Iterable<ContactInfo> listContacInfo() {
		return infoRep.findAll();
	}

	@Override
	public ContactInfo loadInfo(Long infoId) {
		return infoRep.findById(infoId).get();
	}

	@Override
	public void removeInfo(Long infoId) {
		infoRep.deleteById(infoId);
		
	}

}
