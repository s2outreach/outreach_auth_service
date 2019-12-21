package com.cts.outreach.auth.config;

import java.io.IOException;
import java.util.Date;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtUsernamePasswordAuthFilter extends OncePerRequestFilter {
	
	@Autowired
	private JwtConfig jwtConfig;
	
	public JwtUsernamePasswordAuthFilter(JwtConfig jwtConfig) {
		this.jwtConfig = jwtConfig;
	}
	
	private Logger LOGGER = LoggerFactory.getLogger(JwtUsernamePasswordAuthFilter.class);

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		Long now = System.currentTimeMillis();
		LOGGER.info("Subject for jwt " + SecurityContextHolder.getContext());
		LOGGER.info("Authorities "+ SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream());
		String token = Jwts.builder()
				.setSubject(SecurityContextHolder.getContext().getAuthentication().getName())
				.claim("authorities", SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
					.map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.setIssuedAt(new Date(now))
				.setExpiration(new Date(now + jwtConfig.getExpiration() * 1000))  // in milliseconds
				.signWith(SignatureAlgorithm.HS512, jwtConfig.getSecret().getBytes())
				.compact();
		String bearerToken = "Bearer " + token; 
		LOGGER.info(jwtConfig.getHeader() + bearerToken);
		LOGGER.info("user from security " + SecurityContextHolder.getContext().getAuthentication().getName());
			
		response.addHeader(jwtConfig.getHeader(), bearerToken);
		
		chain.doFilter(request, response);
	}
	
}
