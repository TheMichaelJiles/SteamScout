package com.steamscout.application.model.user;

/**
 * Defines behavior for logging in a user.
 * 
 * @author Luke Whaley
 *
 */
public interface LoginService {

	/**
	 * Attempts to login the user with the specified credentials. If the credentials
	 * are valid, then the User object is returned, if the credentials are invalid, then
	 * an InvalidCredentialsException is thrown.
	 * 
	 * @precondition credentials != null
	 * @postcondition none
	 * 
	 * @param credentials the credentials to attempt the login with.
	 * @return the logged in User.
	 * @throws InvalidCredentialsException if the credentials are invalid.
	 */
	User login(Credentials credentials) throws InvalidCredentialsException;
	
}
