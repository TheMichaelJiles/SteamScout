package com.steamscout.application.test.connection.serverwatchlistfetchservice;

import static org.junit.jupiter.api.Assertions.*;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import com.steamscout.application.connection.ServerWatchlistFetchService;

public class TestGetSendingJsonString {

	private class TestServerWatchlistFetchService extends ServerWatchlistFetchService {

		public String getSendingJsonString(String username) {
			this.setUsername(username);
			return super.getSendingJsonString();
		}
	}
	
	@Test
	public void testBuildsCorrectSendingJson() {
		TestServerWatchlistFetchService service = new TestServerWatchlistFetchService();
		String sendingJson = service.getSendingJsonString("test-username");
		
		JSONObject obj = new JSONObject(sendingJson);
		
		assertAll(() -> assertEquals("fetch_watchlist", obj.getString("type")),
				() -> assertEquals("test-username", obj.getJSONObject("data").getJSONObject("user").getString("username")));
	}

}
