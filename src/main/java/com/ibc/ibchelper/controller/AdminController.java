package com.ibc.ibchelper.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ibc.ibchelper.entity.Languages;
import com.ibc.ibchelper.service.LanguageService;
import com.ibc.ibchelper.util.GenericResponse;

@RestController
public class AdminController {
	
	@Autowired
	LanguageService langService;
	
	
	
	@PostMapping("/addlanguage")
	public GenericResponse addLanguage(@Valid Languages language, final HttpServletRequest request) {
		langService.saveLanguage(language);
		return new GenericResponse("success");
	}

}
