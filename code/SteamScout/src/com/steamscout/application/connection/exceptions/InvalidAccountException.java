package com.steamscout.application.connection.exceptions;

import com.steamscout.application.model.user.Credentials;

/**
 * This is the exception that gets thrown when a user tries to create
 * an invalid account, i.e. username already taken.
 * 
 * @author Luke Whaley
 *
 */
public class InvalidAccountException extends Exception {

	private static final long serialVersionUID = 7623736374817257290L;

	/**
	 * Constructs an InvalidAccountException for the specified credentials. This
	 * exception signifies that there is already a record for an account with
	 * matching a credential username.
	 * 
	 * @precondition none
	 * @postcondition none
	 * 
	 * @param credentials the credentials of the invalid account.
	 */
	public InvalidAccountException(Credentials credentials) {
		super("Account could not be created for username: " + credentials.getUsername());
	}
}
