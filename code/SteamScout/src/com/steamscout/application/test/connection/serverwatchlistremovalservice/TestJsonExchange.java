package com.steamscout.application.test.connection.serverwatchlistremovalservice;

import static org.junit.jupiter.api.Assertions.*;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import com.steamscout.application.connection.ServerWatchlistRemovalService;
import com.steamscout.application.model.game_data.Game;
import com.steamscout.application.model.game_data.Watchlist;
import com.steamscout.application.model.user.Credentials;
import com.steamscout.application.model.user.User;

class TestJsonExchange {

	private class TestServerWatchlistRemovalService extends ServerWatchlistRemovalService {
		public Watchlist interpretJsonString(Credentials credentials, String receivedJson) {
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
	void testValidModification() {
		var service = new TestServerWatchlistRemovalService();
		Credentials credentials = new Credentials("twhal", "1234");
		User user = new User(credentials);
		String receivedJson = "{\"result\": true, \"games_on_watchlist\": [{\"steamid\": 5, \"title\": \"test-game\", \"actualprice\": 39.99, \"initialprice\": 59.99, \"onsale\": true, \"onsale_selected\": true, \"targetprice_selected\": false, \"targetprice_criteria\": 0.0}]}";
		user.setWatchlist(service.interpretJsonString(credentials, receivedJson));
		Game game = (Game) user.getWatchlist().getMatchingGames("test-game").toArray()[0];
		
		assertAll(() -> assertEquals(5, game.getAppId()),
				() -> assertEquals("test-game", game.getTitle()),
				() -> assertEquals(1, user.getWatchlist().size()));
	}
	
	@Test
	void testGetSendingJsonString( ) {
		TestServerWatchlistRemovalService service = new TestServerWatchlistRemovalService();
		Credentials credentials = new Credentials("twhal", "1234");
		Game game = new Game(4, "test");
		String json = service.getJsonString(credentials, game);
		
		JSONObject jsonobj = new JSONObject(json);
		
		assertAll(() -> assertEquals("watchlist_removal", jsonobj.getString("type")),
				() -> assertEquals("twhal", jsonobj.getJSONObject("data").getJSONObject("user").getString("username")),
				() -> assertEquals("1234", jsonobj.getJSONObject("data").getJSONObject("user").getString("password")),
				() -> assertEquals(4, jsonobj.getJSONObject("data").get("steamid")));
	}

}
