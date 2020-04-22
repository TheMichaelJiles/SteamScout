package com.steamscout.application.test.connection.servercreateaccountservice;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.steamscout.application.connection.ServerCreateAccountService;

public class TestCreateAccount {

	private class TestServerCreateAccountService extends ServerCreateAccountService {
		@Override
		protected Object send() {
			return null;
		}
	}
	
	@Test
	public void testCreate() {
		TestServerCreateAccountService service = new TestServerCreateAccountService();
		service.createAccount(null, null);
		assertTrue(true);
	}

}
