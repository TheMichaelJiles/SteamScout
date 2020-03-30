package com.steamscout.application.test.connection.serverwatchlistmodificationservice;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.steamscout.application.connection.ServerWatchlistModificationService;
import com.steamscout.application.model.game_data.Game;
import com.steamscout.application.model.game_data.Watchlist;
import com.steamscout.application.model.notification.NotificationCriteria;
import com.steamscout.application.model.user.Credentials;
import com.steamscout.application.model.user.User;

class TestJsonExchange {
	
	private class TestServerWatchlistModificationService extends ServerWatchlistModificationService {
		public Watchlist interpretJsonString(Credentials credentials, String receivedJson) {
			this.setCredentials(credentials);
			return super.interpretJsonString(receivedJson);
		}
		
		public String getJsonString(Credentials credentials, Game game, NotificationCriteria notificationCriteria) {
			this.setCredentials(credentials);
			this.setGame(game);
			this.setCriteria(notificationCriteria);
			return super.getSendingJsonString();
		}
	}
	
	@Test
	void testValidModification() {
		TestServerWatchlistModificationService service = new TestServerWatchlistModificationService();
		Credentials credentials = new Credentials("twhal", "1234");
		User user = new User(credentials);
		String receivedJson = "{\"result\": true, \"games_on_watchlist\": [{\"steamid\": 5, \"title\": \"test-game\", \"actualprice\": 39.99, \"initialprice\": 59.99, \"onsale\": true, \"onsale_selected\": true, \"targetprice_selected\": false, \"targetprice_criteria\": 0.0}]}";
		user.setWatchlist(service.interpretJsonString(credentials, receivedJson));
		Game game = (Game) user.getWatchlist().getMatchingGames("test-game").toArray()[0];
		
		assertAll(() -> assertEquals(5, game.getAppId()),
				() -> assertEquals("test-game", game.getTitle()),
				() -> assertEquals(1, user.getWatchlist().size()));
	}

}
