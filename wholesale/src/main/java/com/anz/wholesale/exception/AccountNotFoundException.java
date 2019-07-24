package com.anz.wholesale.exception;

public class AccountNotFoundException extends RuntimeException{

	/**
	 * Account Not Found Exceptiom
	 */
	private static final long serialVersionUID = 1L;

	public AccountNotFoundException(String message) {
		super(message);
	}
	
}
