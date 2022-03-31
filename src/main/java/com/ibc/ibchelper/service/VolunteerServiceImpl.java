package com.ibc.ibchelper.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibc.ibchelper.entity.Event;
import com.ibc.ibchelper.entity.VolunteerType;
import com.ibc.ibchelper.repository.EventRepository;
import com.ibc.ibchelper.repository.VolunteerTypeRepository;

@Service
public class VolunteerServiceImpl implements VolunteerService{
	
	@Autowired
	VolunteerTypeRepository volTypeRep;
	@Autowired
	EventRepository eventRep;

	@Override
	public Iterable<VolunteerType> listVolunteerTypes() {
		return volTypeRep.findAll();
	}

	@Override
	public void saveEvent(Event event) {
		eventRep.save(event);
	}

	@Override
	public Iterable<Event> listEvents() {
		return eventRep.findAll();
	}
	
	

}
