package com.multiple.authentication.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

	@GetMapping("/hello")
	public String sayHi(Authentication authentication){

		Authentication authentication2 = SecurityContextHolder.getContext().getAuthentication();
		System.out.println(authentication2.getName());
		return "hi spring security Authenticate object!!";
	}
}
