package com.steamscout.application.model.user;

/**
 * Encapsulates a username/password pair.
 * 
 * @author Thomas Whaley
 *
 */
public class Credentials {

	private String username;
	private String password;
	
	/**
	 * Creates a new Credentials object.
	 * 
	 * @precondition username != null && password != null
	 * @postcondition getUsername().equals(username) && getPassword().equals(password)
	 * 
	 * @param username the user name portion of the credential.
	 * @param password the password portion of the credential.
	 */
	public Credentials(String username, String password) {
		if (username == null) {
			throw new IllegalArgumentException("username should not be null.");
		}
		if (password == null) {
			throw new IllegalArgumentException("password should not be null.");
		}
		
		this.username = username;
		this.password = password;
	}
	
	/**
	 * Gets the username portion of this credential.
	 * 
	 * @precondition none
	 * @postcondition none
	 * 
	 * @return the username.
	 */
	public String getUsername() {
		return this.username;
	}
	
	/**
	 * Gets the password portion of this credential.
	 * 
	 * @precondition none
	 * @postcondition none
	 * 
	 * @return the password.
	 */
	public String getPassword() {
		return this.password;
	}
}
