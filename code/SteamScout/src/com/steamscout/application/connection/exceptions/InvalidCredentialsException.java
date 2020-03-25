package com.steamscout.application.connection.exceptions;

import com.steamscout.application.model.user.Credentials;

/**
 * Exception that is thrown when attempting to login with invalid
 * credentials.
 * 
 * @author Luke Whaley
 *
 */
public class InvalidCredentialsException extends Exception {

	private static final long serialVersionUID = 3524344855409279186L;

	/**
	 * Creates a new InvalidCredentialsException object for the
	 * specified credentials.
	 * 
	 * @precondition credentials != null
	 * @postcondition none
	 * 
	 * @param credentials the invalid credentials.
	 */
	public InvalidCredentialsException(Credentials credentials) {
		super("Credentials for entered username " + credentials.getUsername() + " are invalid.");
	}
}
