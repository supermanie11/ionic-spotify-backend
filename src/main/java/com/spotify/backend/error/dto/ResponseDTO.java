package com.spotify.backend.error.dto;

import java.util.Date;

public class ResponseDTO{

	private String timestamp = new Date().toString();
	private String message = "";
	private int status;
	private boolean success = false;
	
	
	public ResponseDTO() {
	}

	public ResponseDTO(String message, int status, boolean success) {
		super();
		this.message = message;
		this.status = status;
		this.success = success;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	@Override
	public String toString() {
		return "ResponseDTO [timestamp=" + timestamp + ", message=" + message + ", status=" + status + ", success="
				+ success + "]";
	}
	
	
}
