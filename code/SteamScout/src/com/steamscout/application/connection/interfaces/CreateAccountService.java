package com.steamscout.application.connection.interfaces;

import com.steamscout.application.connection.exceptions.InvalidAccountException;
import com.steamscout.application.model.user.Credentials;

/**
 * Defines behavior for the system's create account feature.
 * 
 * @author Luke Whaley
 *
 */
public interface CreateAccountService {

	/**
	 * Attempts to create an account with the specified credentials. If there
	 * are credentials stored in the system that have the same username, then 
	 * the account will not be created.
	 * 
	 * @precondition credentials != null && email != null
	 * @postcondition none
	 * 
	 * @param credentials the credentials of the new account to create.
	 * @param email the email of the new account.
	 * 
	 * @throws InvalidAccountException if the account could not be created.
	 */
	void createAccount(Credentials credentials, String email) throws InvalidAccountException;
}
