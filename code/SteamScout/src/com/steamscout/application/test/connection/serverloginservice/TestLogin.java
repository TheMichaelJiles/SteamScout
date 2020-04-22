package com.steamscout.application.test.connection.serverloginservice;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.steamscout.application.connection.ServerLoginService;
import com.steamscout.application.model.user.Credentials;
import com.steamscout.application.model.user.User;

public class TestLogin {

	private class TestServerLoginService extends ServerLoginService {

		@Override
		protected User send() {
			return new User(new Credentials("test", "123"));
		}
		
	}
	
	@Test
	public void testLogin() {
		TestServerLoginService service = new TestServerLoginService();
		User result = service.login(new Credentials("test", "123"));
		assertEquals("test", result.getCredentials().getUsername());
	}

}
