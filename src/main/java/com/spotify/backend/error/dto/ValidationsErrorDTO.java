package com.spotify.backend.error.dto;

import java.util.ArrayList;
import java.util.List;

public class ValidationsErrorDTO extends ResponseDTO {

	private List<ValidationErrorDTO> errors = new ArrayList<ValidationErrorDTO> ();

	public ValidationsErrorDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ValidationsErrorDTO(String message, int status, boolean success) {
		super(message, status, success);
	}

	public List<ValidationErrorDTO> getErrors() {
		return errors;
	}

	public void setErrors(List<ValidationErrorDTO> errors) {
		this.errors = errors;
	}

}
