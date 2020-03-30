package com.steamscout.application.test.connection.servergamefetchservice;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;

import org.json.JSONObject;
import org.junit.Test;

import com.steamscout.application.connection.ServerGameFetchService;

public class TestJsonExchange {

	private class TestServerGameFetchService extends ServerGameFetchService {

		public Map<String, Integer> interpretJsonString(String receivingJson) {
			return super.interpretJsonString(receivingJson);
		}

		public String getJsonString() {
			return super.getSendingJsonString();
		}

	}
	
	@Test
	public void testGameMapIsCreatedWithValidJsonString() {
		TestServerGameFetchService service = new TestServerGameFetchService();
		String jsonString = "{\"games\": [{\"steamid\": 5, \"title\": \"test-game\", \"actualprice\": 39.99, \"initialprice\": 59.99, \"onsale\": true}]}";
		Map<String, Integer> steamGames = service.interpretJsonString(jsonString);
		
		assertEquals(steamGames.get("test-game"), 5);
	}
	
	@Test
	public void testFormsValidJsonToSendToServer() {
		TestServerGameFetchService service = new TestServerGameFetchService();
		String json = service.getJsonString();
		JSONObject jsonobj = new JSONObject(json);
		
		assertAll(() -> assertEquals("fetch_games", jsonobj.getString("type")));			
	}
}
