package com.cts.outreach.auth.model;

public class AuthenticateResp {
	
	private String status;
	private Long userid;
	private String role;

	public AuthenticateResp(String status, Long userid, String role) {
		this.status = status;
		this.userid = userid;
		this.role =role;
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
	public void setRole(String role) {
		this.role = role;
	}

}
