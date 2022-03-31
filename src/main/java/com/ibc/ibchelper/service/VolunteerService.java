package com.ibc.ibchelper.service;

import com.ibc.ibchelper.entity.Event;
import com.ibc.ibchelper.entity.VolunteerType;

public interface VolunteerService {
	public Iterable<VolunteerType> listVolunteerTypes();
	public void saveEvent(Event event);
	public Iterable<Event> listEvents();
}
