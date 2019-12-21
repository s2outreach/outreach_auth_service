package com.cts.outreach.auth.model;

public class AuthenticateResp {
	
	private String status;
	private Long userid;

	public AuthenticateResp(String status, Long userid) {
		this.status = status;
		this.userid = userid;
	}
	public String getStatus() {
		return this.status;
	}
	public Long getUserid() {
		return this.userid;
	}

}
