package com.cts.outreach.auth.controller;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cts.outreach.auth.entity.UserEntity;
import com.cts.outreach.auth.model.AuthenticateResp;
import com.cts.outreach.auth.model.NewUserReq;
import com.cts.outreach.auth.model.NewUserResp;
import com.cts.outreach.auth.service.UserService;

@RestController
public class AuthController {
	
	private Logger LOGGER = LoggerFactory.getLogger(Controller.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@PostMapping("/register")
	public NewUserResp register(@RequestBody NewUserReq newuser){
		LOGGER.info("New user requested for " + newuser.getUsername() + " for email" + newuser.getEmail());
		LOGGER.info("Check for username availability");
		UserEntity checkUser = userService.getUserByName(newuser.getUsername());
		if (checkUser != null) {
			LOGGER.info("Username " + checkUser.getUsername() + " already exists");
			return new NewUserResp("username exists");
		}
		
		LOGGER.info("Username is available");
		
		UserEntity checkMail = userService.getUserByEmail(newuser.getEmail());
		if (checkMail != null) {
			LOGGER.info("Email " + checkMail.getEmail() + " already exists");
			return new NewUserResp("email exists");
		}
		LOGGER.info("Email is available");
		
		UserEntity userEntity = new UserEntity(
				newuser.getUsername(),
				bCryptPasswordEncoder.encode(newuser.getPassword()),
				newuser.getEmail());
		Long userid = userService.addNewUser(userEntity);

		return new NewUserResp("user added");
	}
	
	@PostMapping("/authenticate")
	public AuthenticateResp basicGet(HttpServletResponse response) {
		LOGGER.info(SecurityContextHolder.getContext().getAuthentication().getName());
		UserEntity user = userService.getUserByName(SecurityContextHolder.getContext().getAuthentication().getName());
		return new AuthenticateResp("success", user.getId());
	}
	

}

