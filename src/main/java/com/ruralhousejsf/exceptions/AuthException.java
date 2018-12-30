package com.ruralhousejsf.exceptions;

/**
 * Thrown when an authentication fails.
 */
public class AuthException extends Exception {

	/**
	 * Constructs a {@code AuthException} with a default message.
	 */
	public AuthException() {
		super("Authentification failed.");
	}

	/**
	 * Constructs a {@code AuthException} with a default message.
	 * @param s the message
	 */
	public AuthException(String s) {
		super(s);
	}

	private static final long serialVersionUID = -7963009307719576712L;

}
