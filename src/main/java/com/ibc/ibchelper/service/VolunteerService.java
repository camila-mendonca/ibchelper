package com.ibc.ibchelper.service;

import java.util.Set;

import com.ibc.ibchelper.entity.Event;
import com.ibc.ibchelper.entity.User;
import com.ibc.ibchelper.entity.Volunteer;
import com.ibc.ibchelper.entity.VolunteerType;
import com.ibc.ibchelper.form.VolunteerForm;

public interface VolunteerService {
	public Iterable<VolunteerType> listVolunteerTypes();
	public VolunteerType loadVolType(Long id);
	public void saveEvent(Event event);
	public Iterable<Event> listEvents();
	public Volunteer saveVolunteer(VolunteerForm vForm);
	public Set<VolunteerType> listVolTypesByIds(Iterable<Long> ids);
}
