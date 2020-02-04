package com.steamscout.application.test.model.user.credentials;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.steamscout.application.model.user.Credentials;

public class TestConstructor {

	@Test
	public void testNotAllowNullUsername() {
		assertThrows(IllegalArgumentException.class, () -> new Credentials(null, "yes"));
	}

	@Test
	public void testNotAllowNullPassword() {
		assertThrows(IllegalArgumentException.class, () -> new Credentials("me", null));
	}
	
	@Test
	public void testCorrectPostconditions() {
		Credentials creds = new Credentials("twhal", "123");
		
		assertAll(() -> assertEquals("twhal", creds.getUsername()),
				() -> assertEquals("123", creds.getPassword()));
	}
}
