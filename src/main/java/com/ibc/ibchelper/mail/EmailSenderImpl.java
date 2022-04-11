package com.ibc.ibchelper.mail;


import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Component
public class EmailSenderImpl implements EmailSender {

	@Autowired
	private JavaMailSender emailSender;

	@Autowired
	private TemplateEngine tmpEngine;
	
	@Autowired
	private Environment env;
	
	@Override
	public void sendMessage(String to, String subject, String text) {		
		
		try {
			MimeMessage mimeMessage = emailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
			helper.setFrom(env.getProperty("SPRING.MAIL.USERNAME"));
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText("<html><body>"+text+"</html></body>",true);
			emailSender.send(mimeMessage);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		/*
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);
		message.setSubject(subject);
		
		message.setText(text);
		message.setFrom(env.getProperty("support.email"));
		emailSender.send(message);*/
		
	}

}
