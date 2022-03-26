package com.ibc.ibchelper.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibc.ibchelper.entity.Languages;
import com.ibc.ibchelper.repository.LanguageRepository;

@Service
public class LanguageServiceImpl implements LanguageService {
	
	@Autowired
	LanguageRepository langRep;

	@Override
	public Languages saveLanguage(Languages lang) {
		return langRep.save(lang);
	}

	@Override
	public Iterable<Languages> listLanguages() {
		return langRep.findAll();
	}

}
