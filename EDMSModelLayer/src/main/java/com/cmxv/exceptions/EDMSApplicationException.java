package com.cmxv.exceptions;

public class EDMSApplicationException extends RuntimeException {

	private static final long serialVersionUID = 8925181898305242227L;
	
	private String message;
	
	public EDMSApplicationException(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return this.message;
	}

}
