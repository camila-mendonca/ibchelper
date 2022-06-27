package com.ibc.ibchelper.service;

import com.ibc.ibchelper.entity.ContactInfo;

public interface ContactInfoService {

	public ContactInfo saveContactInfo(ContactInfo info);
	public Iterable<ContactInfo> listContacInfo(boolean isPublic);
	public ContactInfo loadInfo(Long infoId);
	public void removeInfo(Long infoId);
		
}
