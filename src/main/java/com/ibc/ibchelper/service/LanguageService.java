package com.ibc.ibchelper.service;

import com.ibc.ibchelper.entity.Languages;

public interface LanguageService {
	
	public Languages saveLanguage(Languages lang);
	public Iterable<Languages> listLanguages();

}
