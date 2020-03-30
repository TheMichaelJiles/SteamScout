package com.steamscout.application.test.connection.serverwatchlistfetchservice;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.steamscout.application.connection.ServerWatchlistFetchService;
import com.steamscout.application.model.game_data.Game;
import com.steamscout.application.model.game_data.Watchlist;

public class TestInterpretJsonString {

	private class TestServerWatchlistFetchService extends ServerWatchlistFetchService {
	
		public Watchlist interpretJsonString(String receivingJson) {
			return super.interpretJsonString(receivingJson);
		}
	}
	
	@Test
	public void testInterpretsCorrectly() {
		String json = "{\"games_on_watchlist\": [{\"steamid\": 1, \"title\": \"test-title\", \"initialprice\": 5.00, \"actualprice\": 3.00, \"onsale\": true, \"targetprice_criteria\": 15.00, \"targetprice_selected\": true, \"onsale_selected\": true},"
				+ "{\"steamid\": 2, \"title\": \"test-title-1\", \"initialprice\": 25.00, \"actualprice\": 25.00, \"onsale\": false, \"targetprice_criteria\": 5.00, \"targetprice_selected\": true, \"onsale_selected\": false}]}";
		
		TestServerWatchlistFetchService service = new TestServerWatchlistFetchService();
		Watchlist result = service.interpretJsonString(json);
		List<Game> games = new ArrayList<Game>(result);
		
		assertAll(() -> assertEquals(1, games.get(0).getAppId()),
				() -> assertEquals("test-title", games.get(0).getTitle()),
				() -> assertEquals(5.0, games.get(0).getInitialPrice()),
				() -> assertEquals(3.0, games.get(0).getCurrentPrice()),
				() -> assertEquals(true, games.get(0).isOnSale()),
				() -> assertEquals(true, result.getNotificationCriteria(games.get(0)).shouldNotifyWhenBelowTargetPrice()),
				() -> assertEquals(true, result.getNotificationCriteria(games.get(0)).shouldNotifyOnSale()),
				() -> assertEquals(15.0, result.getNotificationCriteria(games.get(0)).getTargetPrice()),
				() -> assertEquals(2, games.get(1).getAppId()),
				() -> assertEquals("test-title-1", games.get(1).getTitle()),
				() -> assertEquals(25.0, games.get(1).getInitialPrice()),
				() -> assertEquals(25.0, games.get(1).getCurrentPrice()),
				() -> assertEquals(false, games.get(1).isOnSale()),
				() -> assertEquals(true, result.getNotificationCriteria(games.get(1)).shouldNotifyWhenBelowTargetPrice()),
				() -> assertEquals(false, result.getNotificationCriteria(games.get(1)).shouldNotifyOnSale()),
				() -> assertEquals(5.0, result.getNotificationCriteria(games.get(1)).getTargetPrice()));
	}

}
