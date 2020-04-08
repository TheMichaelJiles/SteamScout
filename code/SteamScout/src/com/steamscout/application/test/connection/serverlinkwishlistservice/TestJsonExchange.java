package com.steamscout.application.test.connection.serverlinkwishlistservice;

import static org.junit.jupiter.api.Assertions.*;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import com.steamscout.application.connection.ServerLinkWishlistService;
import com.steamscout.application.model.game_data.Watchlist;

public class TestJsonExchange {

	private class TestServerLinkWishlistService extends ServerLinkWishlistService {

		public TestServerLinkWishlistService(String accountId, boolean shouldSaveId) {
			super(accountId, shouldSaveId);
		}

		public Watchlist interpretJsonString(String json) {
			return super.interpretJsonString(json);
		}

		public String getSendingJsonString() {
			return super.getSendingJsonString();
		}
		
	}
	
	@Test
	public void testDoesNotAllowNullUsername() {
		TestServerLinkWishlistService service = new TestServerLinkWishlistService("5465465", true);
		assertThrows(IllegalArgumentException.class, () -> service.setUsername(null));
	}
	
	@Test
	public void testDoesNotAllowNullAccountId() {
		assertThrows(IllegalArgumentException.class, () -> new TestServerLinkWishlistService(null, false));
	}
	
	@Test
	public void testBuildsCorrectSendingJson() {
		TestServerLinkWishlistService service = new TestServerLinkWishlistService("655449849", true);
		service.setUsername("test-username");
		
		String json = service.getSendingJsonString();
		JSONObject jsonObj = new JSONObject(json);
		
		assertAll(() -> assertEquals("test-username", jsonObj.getJSONObject("data").getJSONObject("user").getString("username")),
				() -> assertEquals("655449849", jsonObj.getJSONObject("data").getJSONObject("user").getString("steamid")),
				() -> assertEquals(true, jsonObj.getJSONObject("data").getJSONObject("user").getBoolean("should_save")),
				() -> assertEquals("link_steam", jsonObj.getString("type")));
	}
	
	@Test
	public void testInterpretsSuccessfulJsonCorrectly() {
		TestServerLinkWishlistService service = new TestServerLinkWishlistService("655449849", true);
		String json = "{\"result\": true, \"watchlist\": "
				+ "[{\"steamid\": 1, \"title\": \"game-1\", \"actualprice\": 20.00, \"initialprice\": 25.00, \"onsale\": true, \"targetprice_criteria\": 5.00, \"onsale_selected\": true, \"targetprice_selected\": true}, "
				+ "{\"steamid\": 2, \"title\": \"game-2\", \"actualprice\": 15.00, \"initialprice\": 15.00, \"onsale\": false, \"targetprice_criteria\": 10.00, \"onsale_selected\": false, \"targetprice_selected\": true}]}";
		Watchlist result = service.interpretJsonString(json);
		assertAll(() -> assertEquals(2, result.size()),
				() -> assertEquals(1, result.getMatchingGames("game-1").size()),
				() -> assertEquals(1, result.getMatchingGames("game-2").size()));
	}

	@Test
	public void testInterpretsUnsuccessfulJsonCorrectly() {
		TestServerLinkWishlistService service = new TestServerLinkWishlistService("655449849", true);
		String json = "{\"result\": false}";
		Watchlist result = service.interpretJsonString(json);
		assertEquals(null, result);
	}
}
