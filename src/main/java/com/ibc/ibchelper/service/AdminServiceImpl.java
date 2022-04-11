package com.ibc.ibchelper.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibc.ibchelper.entity.Languages;
import com.ibc.ibchelper.entity.VolunteerType;
import com.ibc.ibchelper.repository.LanguageRepository;
import com.ibc.ibchelper.repository.VolunteerTypeRepository;

@Service
public class AdminServiceImpl implements AdminService {
	
	@Autowired
	LanguageRepository langRep;
	@Autowired
	VolunteerTypeRepository volTypeRepo;

	@Override
	public Languages saveLanguage(Languages lang) {
		return langRep.save(lang);
	}

	@Override
	public Iterable<Languages> listLanguages() {
		return langRep.findAll();
	}

	@Override
	public void saveVolunteerType(VolunteerType volType) {
		volTypeRepo.save(volType);	
	}

	@Override
	public Iterable<VolunteerType> listVolunteerTypes() {
		return volTypeRepo.findAll();
	}

}
