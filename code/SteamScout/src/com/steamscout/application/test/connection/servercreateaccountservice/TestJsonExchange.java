package com.steamscout.application.test.connection.servercreateaccountservice;

import static org.junit.jupiter.api.Assertions.*;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import com.steamscout.application.connection.ServerCreateAccountService;
import com.steamscout.application.connection.exceptions.InvalidAccountException;
import com.steamscout.application.model.user.Credentials;

public class TestJsonExchange {

	private class TestServerCreateAccountService extends ServerCreateAccountService {
		public String getJsonString(Credentials credentials, String email) {
			return super.getJsonString(credentials, email);
		}
		
		public void interpretJsonString(Credentials credentials, String receivingJson) throws InvalidAccountException {
			super.interpretJsonString(credentials, receivingJson);
		}
	}
	
	@Test
	public void testCreatesCorrectJsonToSendToServer() {
		Credentials credentials = new Credentials("test-username", "test-password");
		String email = "test@example.com";
		
		TestServerCreateAccountService service = new TestServerCreateAccountService();
		
		String jsonString = service.getJsonString(credentials, email);
		JSONObject jsonobj = new JSONObject(jsonString);
		
		assertAll(() -> assertEquals("create_account", jsonobj.getString("type")),
				() -> assertEquals("test-username", jsonobj.getJSONObject("data").getJSONObject("user").getString("username")),
				() -> assertEquals("test-password", jsonobj.getJSONObject("data").getJSONObject("user").getString("password")),
				() -> assertEquals("test@example.com", jsonobj.getJSONObject("data").getJSONObject("user").getString("email")));
	}
	
	@Test
	public void testThrowsAppropriately() {
		Credentials credentials = new Credentials("test-username", "test-password");
		TestServerCreateAccountService service = new TestServerCreateAccountService();
		
		String incomingJson = "{\"result\": false, \"details\": \"Creation Unsuccessful: Username Already Taken.\"}";
		assertThrows(InvalidAccountException.class, () -> service.interpretJsonString(credentials, incomingJson));
	}

	@Test
	public void testDoesntThrow() throws InvalidAccountException {
		Credentials credentials = new Credentials("test-username", "test-password");
		TestServerCreateAccountService service = new TestServerCreateAccountService();
		
		String incomingJson = "{\"result\": true, \"details\": \"Creation Successful.\"}";
		service.interpretJsonString(credentials, incomingJson);
	}
}
