package com.cts.outreach.auth.model;

public class AuthenticateResp {
	
	private String status;
	private Long userid;
	private String role;
	private String email;

	public AuthenticateResp(String status, Long userid, String role, String email) {
		this.status = status;
		this.userid = userid;
		this.role =role;
		this.email = email;
	}
	public String getStatus() {
		return this.status;
	}
	public Long getUserid() {
		return this.userid;
	}
	public String getRole() {
		return role;
	}
	public String getEmail() {
		return email;
	}

}
