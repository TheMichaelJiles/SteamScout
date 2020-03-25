package com.steamscout.application.test.model.user.user;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.steamscout.application.model.game_data.Game;
import com.steamscout.application.model.game_data.Watchlist;
import com.steamscout.application.model.user.Credentials;
import com.steamscout.application.model.user.User;

public class TestConstructor {

	@Test
	public void testCredentialConstructorNotAllowNullCredentials() {
		assertThrows(IllegalArgumentException.class, () -> new User(null));
	}

	@Test
	public void testGameConstructorNotAllowNullCredentials() {
		assertThrows(IllegalArgumentException.class, () -> new User(null, new Watchlist()));
	}
	
	@Test
	public void testGameConstructorNotAllowNullGames() {
		assertThrows(IllegalArgumentException.class, () -> new User(new Credentials("a", "a"), null));
	}
	
	@Test
	public void testValidCredentialConstruction() {
		Credentials creds = new Credentials("twhal", "123");
		User me = new User(creds);
		
		assertAll(() -> assertEquals(creds, me.getCredentials()),
				() -> assertEquals(0, me.getWatchlist().size()));
	}
	
	@Test
	public void testValidGameConstruction() {
		Credentials creds = new Credentials("twhal", "123");
		
		Game game0 = new Game(0, "a");
		Game game1 = new Game(1, "aa");
		Watchlist games = new Watchlist();
		games.add(game0);
		games.add(game1);
		
		User me = new User(creds, games);
		
		assertAll(() -> assertEquals(creds, me.getCredentials()),
				() -> assertEquals(2, me.getWatchlist().size()));
	}
}
