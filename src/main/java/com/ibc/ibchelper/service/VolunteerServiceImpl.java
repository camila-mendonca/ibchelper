package com.ibc.ibchelper.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ibc.ibchelper.entity.Accommodation;
import com.ibc.ibchelper.entity.Event;
import com.ibc.ibchelper.entity.Role;
import com.ibc.ibchelper.entity.User;
import com.ibc.ibchelper.entity.UserType;
import com.ibc.ibchelper.entity.Vehicle;
import com.ibc.ibchelper.entity.Volunteer;
import com.ibc.ibchelper.entity.VolunteerType;
import com.ibc.ibchelper.error.UserAlreadyExistException;
import com.ibc.ibchelper.form.VolunteerForm;
import com.ibc.ibchelper.repository.AccommodationRepository;
import com.ibc.ibchelper.repository.EventRepository;
import com.ibc.ibchelper.repository.RoleRepository;
import com.ibc.ibchelper.repository.UserRepository;
import com.ibc.ibchelper.repository.VehicleRepository;
import com.ibc.ibchelper.repository.VolunteerRepository;
import com.ibc.ibchelper.repository.VolunteerTypeRepository;
import com.ibc.ibchelper.util.OnRegistrationCompleteEvent;

@Service
public class VolunteerServiceImpl implements VolunteerService{
	
	@Autowired
	UserRepository userRep;
	@Autowired
	RoleRepository roleRep;
	@Autowired
	VolunteerRepository volRep;
	@Autowired
	VolunteerTypeRepository volTypeRep;
	@Autowired
	EventRepository eventRep;
	@Autowired
	AccommodationRepository accRep;
	@Autowired
	VehicleRepository veRep;
	@Autowired
	PasswordEncoder encoder;
	@Autowired
	ApplicationEventPublisher eventPublisher;

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
	public Volunteer saveVolunteer(VolunteerForm vForm) {
		Volunteer volunteer = new Volunteer();
		if(userRep.findUserByUsername(vForm.getEmail())!=null) {
			throw new UserAlreadyExistException("UserAlreadyExist");
		}
		
		Set<VolunteerType> types = listVolTypesByIds(vForm.getVolunteerType());
		
		volunteer.setUsername(vForm.getEmail());
		volunteer.setPassword(encoder.encode(vForm.getPassword()));
		volunteer.setName(vForm.getName());
		volunteer.setType(UserType.volunteer);
		volunteer.setCity(vForm.getCity());
		volunteer.setCountry(vForm.getCountry());
		volunteer.setCountryCode(vForm.getCountryCode());
		volunteer.setPhoneNumber(vForm.getPhoneNumber());
		volunteer.setTypesVolunteer(types);
		volunteer.setDetails(vForm.getVolunteerDetails());
		volunteer.setAvailableWeekdays(vForm.getAvailableWeekdays());
		volunteer.setAvailableWeekends(vForm.getAvailableWeekends());
		volunteer.setAvailabilityExceptions(vForm.getAvailabilityExceptions());
		volunteer.setVerified(false);
		volunteer.setIsAvailable(true);
		
		Set<Role> roles = new HashSet<Role>();
		roles.add(roleRep.findRoleById("ROLE_USER"));
		volunteer.setRoles(roles);
		volunteer.setEnabled(false);
		
		Volunteer vol = volRep.save(volunteer);
		
		if(vForm.getIsHosting()) {
			Accommodation acc = new Accommodation();
			acc.setVolunteer(vol);
			acc.setAddress(vForm.getAccAddress());
			acc.setBedsAdults(vForm.getAccBedsAdults());
			acc.setBedsKids(acc.getBedsKids());
			acc.setPets(vForm.getAccPets());
			acc.setRestrictions(vForm.getAccRestrictions());
			acc.setNotes(vForm.getAccNotes());
			accRep.save(acc);
		}
		
		if(vForm.getIsDriving()) {
			Vehicle ve = new Vehicle();
			ve.setVolunteer(vol);
			ve.setTypeCar(vForm.getVeType());
			String typesHelp = "";
			for(String help : vForm.getVeTypeHelp()) {
				typesHelp.concat(help + ", ");
			}
			ve.setTypeDrivingHelp(typesHelp);
			ve.setSeatsAvailable(vForm.getVeSeats());
			ve.setDrivesToBorder(vForm.getVeDrivesToBorder());
			ve.setRestrictions(vForm.getVeRestrictions());
			ve.setNotes(vForm.getVeNotes());
			veRep.save(ve);
		}
<<<<<<< Updated upstream
		
		eventPublisher.publishEvent(new OnRegistrationCompleteEvent(volunteer, null, "localhost:8081"));
=======
		return vol;
>>>>>>> Stashed changes
	}

	@Override
	public Set<VolunteerType> listVolTypesByIds(Iterable<Long> ids) {
		Iterable<VolunteerType> types = volTypeRep.findAllById(ids);
		Set<VolunteerType> volTypes = new HashSet<VolunteerType>();
		types.forEach(volTypes::add);
		return volTypes;
	}
	
	

}
