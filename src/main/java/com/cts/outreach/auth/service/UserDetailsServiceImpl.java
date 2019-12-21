package com.cts.outreach.auth.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cts.outreach.auth.entity.UserEntity;
import com.cts.outreach.auth.repo.UserInterface;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	private Logger LOGGER = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
	
	@Autowired
	private UserInterface userInterface;
	
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
		
		LOGGER.info("Authentication check for " + username);
		
		UserEntity user = userInterface.getUserByName(username);
		
		if (user != null) {
			LOGGER.info(username + " is authenticated");
			List<GrantedAuthority> grantedAuthority = new ArrayList<GrantedAuthority>();
			
			UserDetails userDetails = (UserDetails) new User(user.getUsername(), user.getPassword(), grantedAuthority);
			return userDetails;
		}
		
		LOGGER.info(username + " is not found");
		throw new UsernameNotFoundException(username + " is not found");
	}

}
