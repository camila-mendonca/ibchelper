package com.ibc.ibchelper.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ibc.ibchelper.entity.ContactInfo;
import com.ibc.ibchelper.entity.User;
import com.ibc.ibchelper.entity.VerificationToken;
import com.ibc.ibchelper.mail.EmailSender;
import com.ibc.ibchelper.service.ContactInfoService;
import com.ibc.ibchelper.service.UserService;
import com.ibc.ibchelper.util.GenericResponse;
import com.ibc.ibchelper.util.OnRegistrationCompleteEvent;

@RestController
public class UserRestController {
	@Autowired
	UserService userService;
	@Autowired
	ContactInfoService infoService;
	//Framework related classes
	@Autowired
	ApplicationEventPublisher eventPublisher;
	@Autowired
	MessageSource msgSource;
	@Autowired
	EmailSender emailSender;
	
	@ModelAttribute("currentUser")
	public User currentUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.loadUser(auth.getName());
		return user;
	}
	
	//User CRUD	
	
	@PostMapping("/adduser")
	public GenericResponse addUser(@Valid User user, final HttpServletRequest request) {
		//try {
			userService.saveUser(user);
			/*3.1. Using a Spring Event to Create the Token and Send the Verification Email
			These two additional pieces of logic should not be performed by the controller directly because they are “collateral” back-end tasks.
			The controller will publish a Spring ApplicationEvent to trigger the execution of these tasks. This is as simple as injecting the ApplicationEventPublisher and then using it to publish the registration completion.*/
			String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
			eventPublisher.publishEvent(new OnRegistrationCompleteEvent(user, request.getLocale(), appUrl));
		/*} catch (RuntimeException ex) {
			return new GenericResponse("emailError");
		}*/
		return new GenericResponse("success");	
	}
	
	@GetMapping("/resendRegistrationToken")
	public GenericResponse resendRegistrationToken(HttpServletRequest request, @RequestParam("token") String existingToken) {
		VerificationToken newToken = userService.renewRegistrationToken(existingToken);
		User user = newToken.getUser();
		String url = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/registrationConfirm?token=" + newToken.getToken();
		String message = "You requested a new validation email, to activate your account go to the following link: \\r\\n" + url;
		emailSender.sendMessage(user.getUsername(), "New Registration Confirmation", message);
		
		//Not sure how this message thing is working
		//return new GenericResponse(msgSource.getMessage("resentToken", null, request.getLocale()));
		return new GenericResponse("success");
	}
	
	//Contact Info CRUD	
	
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
	
	//For later implementations:
	public void authWithoutPassword(User user) {
/*
        List<Privilege> privileges = user.getRoles()
                .stream()
                .map(Role::getPrivileges)
                .flatMap(Collection::stream)
                .distinct()
                .collect(Collectors.toList());*/

        List<GrantedAuthority> authorities = user.getRoles()
        		.stream()
                .map(p -> new SimpleGrantedAuthority(p.getName()))
                .collect(Collectors.toList());

        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
