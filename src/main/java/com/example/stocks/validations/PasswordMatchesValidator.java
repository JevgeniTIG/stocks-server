package com.example.stocks.validations;


import com.example.stocks.annotations.PasswordMatches;
import com.example.stocks.payload.request.SignUpRequest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

	@Override
	public void initialize(PasswordMatches constraintAnnotation) {

	}

	@Override
	public boolean isValid(Object obj, ConstraintValidatorContext constraintValidatorContext) {
		SignUpRequest userSignUpRequest = (SignUpRequest) obj;
		return userSignUpRequest.getPassword().equals(userSignUpRequest.getConfirmPassword());
	}
}
