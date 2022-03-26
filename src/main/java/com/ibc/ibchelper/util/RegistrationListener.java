package com.ibc.ibchelper.util;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.ibc.ibchelper.entity.User;
import com.ibc.ibchelper.mail.EmailSender;
import com.ibc.ibchelper.service.UserService;

@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent>{

	@Autowired
	private UserService service;

	@Autowired
	private EmailSender emailSender;
		
	@Override
	public void onApplicationEvent(OnRegistrationCompleteEvent event) {
		this.confirmRegistration(event);
	}
	
	private void confirmRegistration(final OnRegistrationCompleteEvent event) {
		final User user = event.getUser();
        final String token = UUID.randomUUID().toString();
        service.createVerificationTokenForUser(user, token);
        final String confirmationUrl = event.getAppUrl() + "/registrationConfirm?token=" + token;
        //change message to the localized option if everything works ok
        //final String message = msgSource.getMessage("message.regSuccLink", null, "You registered successfully. To confirm your registration, please click on the below link.", event.getLocale());
        //email.setText(message + " \r\n" + confirmationUrl);
		String message = "You registered successfully. To confirm your registration, please click on the below link. \r\n" + confirmationUrl;
        emailSender.sendMessage(user.getUsername(), "Registration Confirmation", message);
        
        //final SimpleMailMessage email = constructRegistrationEmail(event, user, token);
        //mailSender.send(email);
	}
}
