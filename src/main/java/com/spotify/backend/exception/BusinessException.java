package com.spotify.backend.exception;


public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	
	private int statusCode;
	private int errorCode;
	private String details;

	public BusinessException() {
		super();
	}

	public BusinessException(int statusCode, int errorCode, String details) {
		super();
		this.errorCode = errorCode;
		this.statusCode = statusCode;
		this.details = details;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	
	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	

	@Override
	public String toString() {
		return "BusinessException [errorCode=" + errorCode + ", statusCode=" + statusCode + ", details=" + details
				+ "]";
	}

	
}
