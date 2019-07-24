package com.anz.wholesale.exception;

public class ForbiddenException extends RuntimeException{

	/**
	 * Forbidden exception.
	 * Raised when user tries to perform an operation for which he does not have the permission.
	 */
	private static final long serialVersionUID = 1L;

	public ForbiddenException(String message) {
		super(message);
	}
	
}
