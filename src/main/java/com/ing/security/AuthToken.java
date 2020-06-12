package com.ing.security;

public class AuthToken {

	private String token;
	private String userName;

	public AuthToken() {

	}

	public AuthToken(String token, String userName) {
		this.token = token;
		this.userName = userName;
	}

	public AuthToken(String token) {
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
