package com.example.stocks.payload.request;

import com.example.stocks.annotations.PasswordMatches;
import com.example.stocks.annotations.ValidEmail;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@PasswordMatches
public class SignUpRequest {

	@Email(message = "This field must have email format")
	@NotBlank(message = "Email is required")
	@ValidEmail
	private String email;

	@NotEmpty(message = "Please enter your Name")
	private String firstName;

	@NotEmpty(message = "Please enter your Lastname")
	private String lastName;

	@NotEmpty(message = "Please enter your username")
	private String userName;

	@NotEmpty(message = "Password is required")
	@Size(min = 6)
	private String password;
	private String confirmPassword;

}
