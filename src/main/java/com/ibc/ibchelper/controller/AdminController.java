package com.ibc.ibchelper.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.ibc.ibchelper.entity.Languages;
import com.ibc.ibchelper.entity.VolunteerType;
import com.ibc.ibchelper.service.AdminService;
import com.ibc.ibchelper.util.GenericResponse;

@RestController
public class AdminController {
	
	@Autowired
	AdminService adminService;
	
	@GetMapping("/admin/adminpage")
	public ModelAndView openAdminPage(ModelAndView mv) {
		mv.setViewName("user/admin/adminpage");
		return mv;
	}
	
	@PostMapping("/admin/addlanguage")
	public GenericResponse addLanguage(@Valid Languages language, final HttpServletRequest request) {
		adminService.saveLanguage(language);
		return new GenericResponse("success");
	}
	
	@PostMapping("/admin/addvoltype")
	public GenericResponse addVolType(@Valid VolunteerType volType, final HttpServletRequest request) {
		adminService.saveVolunteerType(volType);
		return new GenericResponse("success");
	}
	
	@GetMapping("/volTypes")
	public Iterable<VolunteerType> listVolunteerTypes(){
		return adminService.listVolunteerTypes();
	}

}
