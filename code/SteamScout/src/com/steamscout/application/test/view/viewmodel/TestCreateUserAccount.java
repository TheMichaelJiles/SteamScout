package com.steamscout.application.test.view.viewmodel;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.steamscout.application.connection.exceptions.InvalidAccountException;
import com.steamscout.application.connection.interfaces.CreateAccountService;
import com.steamscout.application.model.user.Credentials;
import com.steamscout.application.view.ViewModel;

public class TestCreateUserAccount {

	private class FailingCreateAccountService implements CreateAccountService {
		@Override
		public void createAccount(Credentials credentials, String email) throws InvalidAccountException {
			throw new InvalidAccountException(credentials);
		}
	}
	
	private class PassingCreateAccountService implements CreateAccountService {
		@Override
		public void createAccount(Credentials credentials, String email) throws InvalidAccountException {
			return;
		}
	}
	
	@BeforeEach
	public void setUp() {
		ViewModel.get().createAccountPageUsernameProperty().setValue("test-username");
		ViewModel.get().createAccountPagePasswordProperty().setValue("test-password");
		ViewModel.get().createAccountPageEmailProperty().setValue("test-email");
	}
	
	@AfterEach
	public void tearDown() {
		ViewModel.get().createAccountPageUsernameProperty().setValue("");
		ViewModel.get().createAccountPagePasswordProperty().setValue("");
		ViewModel.get().createAccountPageEmailProperty().setValue("");
		ViewModel.get().createAccountPageErrorProperty().setValue(null);
	}
	
	@Test
	public void testNotAllowNullService() {
		assertThrows(IllegalArgumentException.class, () -> ViewModel.get().createUserAccount(null));
	}
	
	@Test
	public void testSuccessfulCreation() {
		ViewModel.get().createUserAccount(new PassingCreateAccountService());
		
		assertEquals(null, ViewModel.get().createAccountPageErrorProperty().getValue());
	}

	@Test
	public void testFailingCreation() {
		ViewModel.get().createUserAccount(new FailingCreateAccountService());
		
		assertEquals("Invalid Account", ViewModel.get().createAccountPageErrorProperty().getValue());
	}
}
