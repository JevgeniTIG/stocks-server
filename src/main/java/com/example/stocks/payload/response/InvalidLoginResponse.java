package com.example.stocks.payload.response;

import lombok.Getter;

@Getter
public class InvalidLoginResponse {
	private String userName;
	private String password;

	public InvalidLoginResponse() {
		this.userName = "Invalid Username";
		this.password = "Invalid Password";
	}
}
