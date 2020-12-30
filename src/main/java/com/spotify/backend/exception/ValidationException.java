package com.spotify.backend.exception;

import java.util.Set;

import javax.validation.ConstraintViolation;

public class ValidationException extends BusinessException {

	private Set<ConstraintViolation<?>> errors;

	public ValidationException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ValidationException(int statusCode, int errorCode, String details, Set<ConstraintViolation<?>> errors) {
		super(statusCode, errorCode, details);
		this.errors= errors;
	}

	public Set<ConstraintViolation<?>> getErrors() {
		return errors;
	}

	public void setErrors(Set<ConstraintViolation<?>> errors) {
		this.errors = errors;
	}
	
	
}
