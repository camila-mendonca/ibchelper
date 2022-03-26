package com.ibc.ibchelper.mail;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailSenderImpl implements EmailSender {

	@Autowired
	public JavaMailSender emailSender;
	
	@Autowired
	private Environment env;
	
	@Override
	public void sendMessage(String to, String subject, String text) {
		System.out.println("Sending email to " + to + ". Subject: " + subject);
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);
		message.setSubject(subject);
		message.setText(text);
		message.setFrom(env.getProperty("support.email"));
		emailSender.send(message);
	}

}
