package com.ibc.ibchelper.service;

import com.ibc.ibchelper.entity.Languages;
import com.ibc.ibchelper.entity.VolunteerType;

public interface AdminService {
	
	public Languages saveLanguage(Languages lang);
	public Iterable<Languages> listLanguages();
	public void saveVolunteerType(VolunteerType volType);
	public Iterable<VolunteerType> listVolunteerTypes();

}
