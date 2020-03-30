package com.steamscout.application.connection;

import org.json.JSONObject;

import com.steamscout.application.connection.exceptions.InvalidAccountException;
import com.steamscout.application.connection.interfaces.CreateAccountService;
import com.steamscout.application.model.user.Credentials;

/**
 * Contacts the server and creates a new account on behalf of a user.
 * 
 * @author Luke Whaley
 *
 */
public class ServerCreateAccountService extends ServerService<Object> implements CreateAccountService {
	
	private Credentials credentials;
	private String email;
	
	@Override
	public void createAccount(Credentials credentials, String email) throws InvalidAccountException {
		this.credentials = credentials;
		this.email = email;
		this.send();
	}

	@Override
	protected Object interpretJsonString(String json) {
		JSONObject root = new JSONObject(json);
		boolean isCreated = root.getBoolean("result");
		if (!isCreated) {
			throw new InvalidAccountException(this.credentials);
		}
		return null;
	}

	@Override
	protected String getSendingJsonString() {
		JSONObject user = new JSONObject();
		user.put("username", this.credentials.getUsername());
		user.put("password", this.credentials.getPassword());
		user.put("email", this.email);
		
		JSONObject data = new JSONObject();
		data.put("user", user);
		
		JSONObject root = new JSONObject();
		root.put("type", "create_account");
		root.put("data", data);
		
		return root.toString();
	}

	/**
	 * The credentials for the new account.
	 * 
	 * @precondition none
	 * @postcondition none
	 * 
	 * @param credentials the credentials for the new account.
	 */
	public void setCredentials(Credentials credentials) {
		this.credentials = credentials;
	}

	/**
	 * The email of the new account.
	 * 
	 * @precondition none
	 * @postcondition none
	 * 
	 * @param email the email of the new account.
	 */
	public void setEmail(String email) {
		this.email = email;
	}

}
