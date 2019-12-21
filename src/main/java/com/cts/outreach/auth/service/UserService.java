package com.cts.outreach.auth.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.outreach.auth.entity.UserEntity;
import com.cts.outreach.auth.repo.UserInterface;

@Service
public class UserService {
	
	private Logger LOGGER = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private UserInterface userInterface;
	
	public Long addNewUser(UserEntity newuser) {
		LOGGER.info("New user entity requested for " + newuser.getUsername());
		userInterface.save(newuser);
		return newuser.getId();
	}
	
	public UserEntity getUserByName(String username) {
		LOGGER.info("User details requested for the username: " + username);
		return userInterface.getUserByName(username);
	}
	
	public UserEntity getUserByEmail(String email) {
		LOGGER.info("User details requested for the email: " + email);
		return userInterface.getUserByEmail(email);
	}

}
