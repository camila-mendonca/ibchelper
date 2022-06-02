package com.ibc.ibchelper.controller;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.ibc.ibchelper.entity.ContactInfo;
import com.ibc.ibchelper.entity.Event;
import com.ibc.ibchelper.entity.User;
import com.ibc.ibchelper.entity.UserType;
import com.ibc.ibchelper.entity.Volunteer;
import com.ibc.ibchelper.entity.VolunteerType;
import com.ibc.ibchelper.form.VolunteerForm;
import com.ibc.ibchelper.mail.EmailSender;
import com.ibc.ibchelper.security.UserSecurityService;
import com.ibc.ibchelper.service.AdminService;
import com.ibc.ibchelper.service.ContactInfoService;
import com.ibc.ibchelper.service.UserService;
import com.ibc.ibchelper.service.VolunteerService;
import com.ibc.ibchelper.util.GenericResponse;
import com.ibc.ibchelper.util.OnRegistrationCompleteEvent;

@RestController
public class UserRestController {
	@Autowired
	UserService userService;
	@Autowired
	ContactInfoService infoService;
	@Autowired
	VolunteerService volService;
	@Autowired
	UserSecurityService userSecService;
	@Autowired
	AdminService adminService;
	
	//Framework related classes
	@Autowired
	ApplicationEventPublisher eventPublisher;
	@Autowired
	MessageSource msgSource;
	@Autowired
	EmailSender emailSender;
	
	@GetMapping("/addvolunteer")
	public void addVolunteer(final HttpServletRequest request) {
		VolunteerForm vForm = new VolunteerForm();
		vForm.setName("Host One");
		vForm.setEmail("mila.louise.m@gmail.com");
		vForm.setPassword("123456");
		vForm.setCountryCode("40");
		vForm.setPhoneNumber("123654789");
		vForm.setCountry("Romania");
		vForm.setCity("Bucharest");
		
		Set<Long> types = new HashSet<>();
		types.add((long) 81);
		types.add((long) 82);
		vForm.setVolunteerType(types);
		
		vForm.setVolunteerDetails("Here are some details about this volunteer");
		vForm.setAvailableWeekdays(false);
		vForm.setAvailableWeekends(true);
		vForm.setAvailabilityExceptions("Not available in the morning");
		
		vForm.setIsHosting(true);
		vForm.setAccAddress("Strada Liviu Rebreanu nr 16");
		vForm.setAccBedsAdults(2);
		vForm.setAccBedsKids(1);
		vForm.setAccPets(true);
		vForm.setAccNotes("Beds available starting on 01-05-2022");
		vForm.setAccRestrictions("Can't accept dogs");
		
		vForm.setIsDriving(false);
		
		volService.saveVolunteer(vForm);
		//String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
		//eventPublisher.publishEvent(new OnRegistrationCompleteEvent(user, request.getLocale(), appUrl));
		
		
	}
	
	@ModelAttribute("currentUser")
	public User currentUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.loadUser(auth.getName());
		return user;
	}
	
	@RequestMapping(value = "/username", method = RequestMethod.GET)
    public String currentUserName(Principal principal) {
		System.out.println("Logged user: " + principal.getName());
        return principal.getName();
    }
	
	//This one will change to Information Center later
	@GetMapping("/user/index")
	public ModelAndView index(ContactInfo contactInfo, ModelAndView mv) {
		mv.addObject("languages", adminService.listLanguages());
		mv.setViewName("user/index");
		return mv;
	}
	
	//--------------------------------------------
	// Contact Info CRUD	
	
	@PostMapping("/user/addinfo")
	public GenericResponse addInfo(@Valid ContactInfo info, final HttpServletRequest request) {
		infoService.saveContactInfo(info);
		return new GenericResponse("success");
	}

	@GetMapping("/user/listcontactinfo")
	public Iterable<ContactInfo> listContacInfo(){
		return infoService.listContacInfo();
	}
	
	@GetMapping("/user/loadeditinfo/info={infoId}")
	public ContactInfo loadEditInfo(@PathVariable ("infoId") Long infoId) {
		return infoService.loadInfo(infoId);
	}
	
	@GetMapping("/user/removeinfo/info={infoId}")
	public ResponseEntity<String> removeInfo(@PathVariable ("infoId") Long infoId) {
		infoService.removeInfo(infoId);
		return ResponseEntity.ok().build();
	}
	//--------------------------------------------
	// Events CRUD
	
	@PostMapping("/user/addevent")
	public GenericResponse addEvent(@Valid Event event, final HttpServletRequest request) {
		volService.saveEvent(event);
		return new GenericResponse("success");
	}
	
	//--------------------------------------------
	// Volunteers CRUD
	
	@RequestMapping("/user/volunteers")
	public ModelAndView openVolunteers(ModelAndView mv) {
		mv.addObject("volunteerTypes", volService.listVolunteerTypes());
		mv.setViewName("user/volunteers");
	    return mv;
	}
	
	@GetMapping("/listevents")
	public Iterable<Event> listEvent(){
		return volService.listEvents();
	}

}
