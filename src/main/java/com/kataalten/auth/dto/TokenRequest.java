package com.kataalten.auth.dto;

public class TokenRequest {
	private String token;

	public String getToken() {
		return token;
	}

	public TokenRequest(String token) {
		super();
		this.token = token;
	}

}
