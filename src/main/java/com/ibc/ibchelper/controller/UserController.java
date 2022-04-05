package com.ibc.ibchelper.controller;

import java.security.Principal;
import java.util.Calendar;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import com.ibc.ibchelper.entity.ContactInfo;
import com.ibc.ibchelper.entity.User;
import com.ibc.ibchelper.entity.VerificationToken;
import com.ibc.ibchelper.service.AdminService;
import com.ibc.ibchelper.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	UserService userService;
	@Autowired
	MessageSource msgSource;
	@Autowired
	AdminService adminService;
	
	@RequestMapping(value="/")
	public String index() {
		return "index";
	}
	
	
	
	@GetMapping("/signup")
	public String LoadSignUpForm(User user) {
		return "add-user";
	}
	
	@RequestMapping(value = "/username", method = RequestMethod.GET)
    @ResponseBody
    public String currentUserName(Principal principal) {
		System.out.println("Logged user: " + principal.getName());
        return principal.getName();
    }
	
	
	
	//This one will change to Information Center later
	@GetMapping("/user/index")
	public String index(ContactInfo contactInfo, Model model) {
		model.addAttribute("languages", adminService.listLanguages());
		return "user/index";
	}
	
	@GetMapping("/admin/index")
	public String adminIndex() {
		return "user/admin/index";
	}
	
	//Registration Operations
	
	@GetMapping("/registrationConfirm")
	public String confirmRegistration
	  (WebRequest request, Model model, @RequestParam("token") String token) {
	 
	    Locale locale = request.getLocale();
	    
	    VerificationToken verificationToken = userService.getVerificationToken(token);
	    if (verificationToken == null) {
	        String message = msgSource.getMessage("auth.message.invalidToken", null, locale);
	        model.addAttribute("message", message);
	        model.addAttribute("invalid",true);
	        return "bad-user";
	        //return "/bad-user?lang=" + locale.getLanguage();
	    }
	    
	    User user = verificationToken.getUser();
	    Calendar cal = Calendar.getInstance();
	    if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
	        String messageValue = msgSource.getMessage("auth.message.expired", null, locale);
	        model.addAttribute("message", messageValue);
	        model.addAttribute("expired", true);
	        model.addAttribute("token", token);
	        return "bad-user";	        
	    } 
	    
	    user.setEnabled(true); 
	    userService.updateUser(user);
	    model.addAttribute("message", msgSource.getMessage("message.accountVerified", null, locale));
	    return "login";
	}
	
	

}
