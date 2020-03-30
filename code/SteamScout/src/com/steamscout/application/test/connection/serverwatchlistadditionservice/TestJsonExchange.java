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
		public Watchlist interpretJsonString(Credentials credentials, String receivedJson) throws InvalidAdditionException {
			this.setCredentials(credentials);
			return super.interpretJsonString(receivedJson);
		}
		
		public String getJsonString(Credentials credentials, Game game) {
			this.setCredentials(credentials);
			this.setGame(game);
			return super.getSendingJsonString();
		}
	}
	
	@Test
	public void testValidAddition() {
		TestServerWatchlistAdditionService service = new TestServerWatchlistAdditionService();
		Credentials credentials = new Credentials("twhal", "1234");
		User user = new User(credentials);
		String receivedJson = "{\"result\": true, \"games_on_watchlist\": [{\"steamid\": 5, \"title\": \"test-game\", \"actualprice\": 39.99, \"initialprice\": 59.99, \"onsale\": true, \"onsale_selected\": true, \"targetprice_selected\": false, \"targetprice_criteria\": 0.0}]}";
		try {
			user.setWatchlist(service.interpretJsonString(credentials, receivedJson));
		} catch (InvalidAdditionException e) {
			e.printStackTrace();
		}
		Game game = (Game) user.getWatchlist().getMatchingGames("test-game").toArray()[0];
		assertAll(() -> assertEquals(5, game.getAppId()),
				() -> assertEquals("test-game", game.getTitle()),
				() -> assertEquals(1, user.getWatchlist().size()));
	}

}
