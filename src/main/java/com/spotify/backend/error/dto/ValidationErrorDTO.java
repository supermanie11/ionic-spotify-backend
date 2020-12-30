package com.spotify.backend.error.dto;

public class ValidationErrorDTO {

	private String field;
	private String error;
	
	public ValidationErrorDTO() {}
	
	public ValidationErrorDTO(String field, String error) {
		super();
		this.field = field;
		this.error = error;
	}
	
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	
	
	
	
}
