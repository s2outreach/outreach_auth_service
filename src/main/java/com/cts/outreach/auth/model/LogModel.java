package com.cts.outreach.auth.model;

public class LogModel {
	
	private String eventname;
	private String username;
	private String action;
	public String getEventname() {
		return eventname;
	}
	public void setEventname(String eventname) {
		this.eventname = eventname;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public LogModel(String eventname, String username, String action) {
		super();
		this.eventname = eventname;
		this.username = username;
		this.action = action;
	}

}
