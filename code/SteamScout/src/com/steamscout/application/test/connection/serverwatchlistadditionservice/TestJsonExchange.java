package com.steamscout.application.test.connection.serverwatchlistadditionservice;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.steamscout.application.connection.ServerWatchlistAdditionService;
import com.steamscout.application.connection.exceptions.InvalidAdditionException;
import com.steamscout.application.model.game_data.Game;
import com.steamscout.application.model.game_data.Watchlist;
import com.steamscout.application.model.user.Credentials;
import com.steamscout.application.model.user.User;

class TestJsonExchange {

	private class TestServerWatchlistAdditionService extends ServerWatchlistAdditionService {
		public Watchlist interpretJsonString(Credentials credentials, String receivingJson) throws InvalidAdditionException {
			return super.interpretJsonString(credentials, receivingJson);
		}
		
		public String getJsonString(Credentials credentials, Game game) {
			return super.getJsonString(credentials, game);
		}
	}
	
	@Test
	public void testValidAddition() {
		TestServerWatchlistAdditionService service = new TestServerWatchlistAdditionService();
		Credentials credentials = new Credentials("twhal", "1234");
		User user = new User(credentials);
		try {
			user.setWatchlist(service.addGameToWatchlist(credentials, new Game(0, "title")));
		} catch (InvalidAdditionException e) {
			e.printStackTrace();
		}
		
		assertAll(() -> assertEquals("twhal", resultingUser.getCredentials().getUsername()),
				() -> assertEquals("1234", resultingUser.getCredentials().getPassword()),
				() -> assertEquals(1, resultingUser.getWatchlist().size());
	}

}
