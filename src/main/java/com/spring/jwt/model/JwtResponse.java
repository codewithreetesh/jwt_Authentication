package com.spring.jwt.model;

public class JwtResponse {
	
	public String token;

	public JwtResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public JwtResponse(String token) {
		super();
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return "jwtResponse [token=" + token + "]";
	}
	
	
}
