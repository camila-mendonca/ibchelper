package com.ibc.ibchelper.controller;

import java.util.Calendar;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.ibc.ibchelper.entity.ContactInfo;
import com.ibc.ibchelper.entity.User;
import com.ibc.ibchelper.entity.VerificationToken;
import com.ibc.ibchelper.entity.Volunteer;
import com.ibc.ibchelper.form.PasswordChangeForm;
import com.ibc.ibchelper.form.VolunteerForm;
import com.ibc.ibchelper.mail.EmailSender;
import com.ibc.ibchelper.security.UserSecurityService;
import com.ibc.ibchelper.service.ContactInfoService;
import com.ibc.ibchelper.service.UserService;
import com.ibc.ibchelper.service.VolunteerService;
import com.ibc.ibchelper.util.GenericResponse;
import com.ibc.ibchelper.util.OnRegistrationCompleteEvent;

@RestController
public class PublicController {

	@Autowired
	UserService userService;
	@Autowired
	ContactInfoService infoService;
	@Autowired
	VolunteerService volService;
	@Autowired
	UserSecurityService userSecService;

	// Framework related classes
	@Autowired
	ApplicationEventPublisher eventPublisher;
	@Autowired
	MessageSource msgSource;
	@Autowired
	EmailSender emailSender;

	@RequestMapping(value="/")
	public ModelAndView index(ModelAndView mv) {
		mv.addObject("volunteerTypes", volService.listVolunteerTypes());
		mv.setViewName("index");
		return mv;
	}
	
	@GetMapping("/contactinfos")
	public Iterable<ContactInfo> listPublicContacInfo(){
		return infoService.listContacInfo(true);
	}
	
	// -----------------------------------------------
	// USER REGISTRATION METHODS
	
	// In case we need to add more than one external account login:
	// https://www.baeldung.com/spring-security-5-oauth2-login#customization
	@RequestMapping(value = "/login")
	public ModelAndView login(@RequestParam(value = "message", required = false) String message, ModelAndView mv) {
		if (message != null) mv.addObject("message", message);
		mv.setViewName("login");
		return mv;
	}
	
	@GetMapping("/signup")
	public ModelAndView loadSignupForm(User user, ModelAndView mv) {
		mv.setViewName("add-user");
		return mv;
	}	

	@PostMapping("/adduser")
	public GenericResponse addUser(@Valid User user, final HttpServletRequest request) {
		// try {
		userService.saveUser(user);
		/*
		 * 3.1. Using a Spring Event to Create the Token and Send the Verification Email
		 * These two additional pieces of logic should not be performed by the
		 * controller directly because they are “collateral” back-end tasks. The
		 * controller will publish a Spring ApplicationEvent to trigger the execution of
		 * these tasks. This is as simple as injecting the ApplicationEventPublisher and
		 * then using it to publish the registration completion.
		 */
		String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
		eventPublisher.publishEvent(new OnRegistrationCompleteEvent(user, request.getLocale(), appUrl));
		/*
		 * } catch (RuntimeException ex) { return new GenericResponse("emailError"); }
		 */
		return new GenericResponse("success");
	}
	
	@GetMapping("/registrationConfirm")
	public ModelAndView confirmRegistration(WebRequest request, @RequestParam("token") String token, ModelAndView mv) {
		Locale locale = request.getLocale();
		
		VerificationToken verificationToken = userService.getVerificationToken(token);
		Calendar cal = Calendar.getInstance();
		
	    if (verificationToken == null) {
	        String msgInvalid = msgSource.getMessage("auth.message.invalidToken", null, locale);
	        mv.addObject("message", msgInvalid);
	        mv.addObject("invalid", true);
	        mv.setViewName("bad-user");
	    } else if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0){
	    	String msgExpired = msgSource.getMessage("auth.message.expired", null, locale);
		    mv.addObject("message", msgExpired);
		    mv.addObject("expired", true);
		    mv.addObject("token", token);
		    mv.setViewName("bad-user");
	    } else {
	    	User user = verificationToken.getUser();
	    	user.setEnabled(true);
	    	userService.updateUser(user);
	    	mv.addObject("message",msgSource.getMessage("message.accountVerified", null, locale));
	    	mv.setViewName("login");
	    }	    
		return mv;
	}
	
	@GetMapping("/resendRegistrationToken")
	public GenericResponse resendRegistrationToken(HttpServletRequest request, @RequestParam("token") String existingToken) {
		VerificationToken newToken = userService.renewRegistrationToken(existingToken);
		User user = newToken.getUser();
		String url = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/registrationConfirm?token=" + newToken.getToken();
		String message = "You requested a new validation email, to activate your account go to the following link:<br>" + url;
		emailSender.sendMessage(user.getUsername(), "New Registration Confirmation", message);
		
		//Not sure how this message thing is working
		//return new GenericResponse(msgSource.getMessage("resentToken", null, request.getLocale()));
		return new GenericResponse("success");
	}
	
	@GetMapping("/forgotpassword")
	public ModelAndView showForgotPasswordPage(ModelAndView mv) {
		mv.setViewName("recoverpassword");
		return mv;
	}
	
	@PostMapping("/resetPassword")
	public GenericResponse resetPassword(HttpServletRequest request, @RequestParam("username") String username) {
		System.out.println("Sending reset password email to: " + username);
		User user = userService.loadUser(username);
		if(user!=null) {
			final String token = UUID.randomUUID().toString();
			userService.createPasswordResetTokenForUser(user, token);
			String url = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/changePassword?token=" + token;
			String message = "A password reset was requested for your account. To reset your password, click on the link:<br>" + url +"<br>If it wasn't you, please ignore this email.";
			emailSender.sendMessage(user.getUsername(), "Password reset", message);
		}
		return new GenericResponse("");
	}
	
	@GetMapping("/changePassword")
	public ModelAndView showChangePasswordPage(Locale locale, ModelAndView mv, @RequestParam("token") String token) {
		String result = userSecService.validatePasswordResetToken(token);
		if (result!=null) {
			String message = msgSource.getMessage("auth.message."+result,null,locale);
			mv.setViewName("redirect:/login?message="+message);
			//mv.setViewName("redirect:/login?lang="+locale.getLanguage()+"&message="+message);
			
		} else {
			mv.addObject("token",token);
			mv.setViewName("updatepassword");
			//mv.setViewName("redirect:/updatePassword?lang="+locale.getLanguage());
		}
		return mv;
	}
	
	@PostMapping("/savepassword")
	public GenericResponse savePassword(final Locale locale, @Valid PasswordChangeForm password) {
		String result = userSecService.validatePasswordResetToken(password.getToken());
		if(result!=null) {
			return new GenericResponse(msgSource.getMessage("auth.message."+result,null, locale));
		}
		Optional<User> user = userService.getUserByPasswordResetToken(password.getToken());
		if(user.isPresent()) {
			userService.changeUserPassword(user.get(),password.getNewPassword());
			return new GenericResponse("message.passwordChanged");
		} else {
			return new GenericResponse(msgSource.getMessage("auth.message.invalid",null, locale));
		}
	}
	
	// -------------------------------------------------------------
	// VOLUNTEER'S FORM
	
	@PostMapping("/addvolunteer")
<<<<<<< Updated upstream
	public GenericResponse addVolunteer(@RequestBody VolunteerForm vForm) {
		volService.saveVolunteer(vForm);
		//String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
			
		return new GenericResponse("success");
=======
		public GenericResponse addVolunteer(@RequestBody VolunteerForm vForm, final HttpServletRequest request) {
			Volunteer vol = volService.saveVolunteer(vForm);
			
			String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
			eventPublisher.publishEvent(new OnRegistrationCompleteEvent(vol, request.getLocale(), appUrl));
			
			return new GenericResponse("success");
>>>>>>> Stashed changes
	}
}
