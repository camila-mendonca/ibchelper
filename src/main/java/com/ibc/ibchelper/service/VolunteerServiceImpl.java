package com.ibc.ibchelper.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibc.ibchelper.entity.Event;
import com.ibc.ibchelper.entity.Volunteer;
import com.ibc.ibchelper.entity.VolunteerType;
import com.ibc.ibchelper.repository.EventRepository;
import com.ibc.ibchelper.repository.VolunteerRepository;
import com.ibc.ibchelper.repository.VolunteerTypeRepository;

@Service
public class VolunteerServiceImpl implements VolunteerService{
	
	@Autowired
	VolunteerRepository volRep;
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

	@Override
	public VolunteerType loadVolType(Long id) {
		return volTypeRep.findById(id).get();
	}

	@Override
	public Volunteer saveVolunteer(Volunteer volunteer) {
		return volRep.save(volunteer);
	}
	
	

}
