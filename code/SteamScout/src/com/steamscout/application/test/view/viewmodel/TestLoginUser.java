package com.steamscout.application.test.view.viewmodel;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.steamscout.application.connection.exceptions.InvalidCredentialsException;
import com.steamscout.application.connection.interfaces.LoginService;
import com.steamscout.application.model.game_data.Game;
import com.steamscout.application.model.game_data.Watchlist;
import com.steamscout.application.model.user.Credentials;
import com.steamscout.application.model.user.User;
import com.steamscout.application.view.ViewModel;

import javafx.collections.ObservableList;

public class TestLoginUser {

	private class SuccessfulLoginService implements LoginService {
		@Override
		public User login(Credentials credentials) throws InvalidCredentialsException {
			Watchlist games = new Watchlist();
			games.add(new Game(1, "test-game-1"));
			games.add(new Game(2, "test-game-2"));
			return new User(credentials, games);
		}
	}
	
	private class FailingLoginService implements LoginService {
		@Override
		public User login(Credentials credentials) throws InvalidCredentialsException {
			throw new InvalidCredentialsException(credentials);
		}
	}
	
	@BeforeEach
	public void setUp() {
		ViewModel.get().loginPageUsernameProperty().setValue("test-username");
		ViewModel.get().loginPagePasswordProperty().setValue("test-password");
	}
	
	@AfterEach
	public void tearDown() {
		ViewModel.get().loginPageUsernameProperty().setValue("");
		ViewModel.get().loginPagePasswordProperty().setValue("");
		ViewModel.get().loginPageErrorProperty().setValue(null);
		ViewModel.get().userProperty().setValue(null);
		ViewModel.get().watchlistProperty().clear();
	}
	
	@Test
	public void testNotAllowNullService() {
		assertThrows(IllegalArgumentException.class, () -> ViewModel.get().loginUser(null));
	}
	
	@Test
	public void testSuccessfulLogin() {
		ViewModel.get().loginUser(new SuccessfulLoginService());
		
		User currentUser = ViewModel.get().userProperty().getValue();
		ObservableList<Game> currentUserWatchlist = ViewModel.get().watchlistProperty().getValue();
		
		assertAll(() -> assertEquals("test-username", currentUser.getCredentials().getUsername()),
				() -> assertEquals("test-password", currentUser.getCredentials().getPassword()),
				() -> assertEquals(2, currentUserWatchlist.size()),
				() -> assertEquals(true, currentUserWatchlist.contains(new Game(1, "test-game-1"))),
				() -> assertEquals(true, currentUserWatchlist.contains(new Game(2, "test-game-2"))));
	}
	
	@Test
	public void testFailingLogin() {
		ViewModel.get().loginUser(new FailingLoginService());
		
		assertAll(() -> assertEquals("Invalid Credentials", ViewModel.get().loginPageErrorProperty().getValue()),
				() -> assertEquals(null, ViewModel.get().userProperty().getValue()),
				() -> assertEquals(0, ViewModel.get().watchlistProperty().size()));
	}

}
