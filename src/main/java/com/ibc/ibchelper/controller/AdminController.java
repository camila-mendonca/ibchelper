package com.ibc.ibchelper.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ibc.ibchelper.entity.Languages;
import com.ibc.ibchelper.entity.VolunteerType;
import com.ibc.ibchelper.service.AdminService;
import com.ibc.ibchelper.util.GenericResponse;

@RestController
public class AdminController {
	
	@Autowired
	AdminService adminService;
	
	@GetMapping("/adminpage")
	public String openAdmin(Model model) {
		return "user/adminpage";
	}
	
	@PostMapping("/addlanguage")
	public GenericResponse addLanguage(@Valid Languages language, final HttpServletRequest request) {
		adminService.saveLanguage(language);
		return new GenericResponse("success");
	}
	
	@PostMapping("/addvoltype")
	public GenericResponse addVolType(@Valid VolunteerType volType, final HttpServletRequest request) {
		adminService.saveVolunteerType(volType);
		return new GenericResponse("success");
	}
	
	@GetMapping("/volTypes")
	public Iterable<VolunteerType> listVolunteerTypes(){
		return adminService.listVolunteerTypes();
	}

}
