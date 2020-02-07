package com.steamscout.application.test.model.api.gamesearchapi;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import com.steamscout.application.model.api.GameSearchAPI;
import com.steamscout.application.model.api.exceptions.GameNotFoundException;
import com.steamscout.application.model.game_data.Game;

public class TestTropico {

	private class InvalidJson extends GameSearchAPI {
		
		public InvalidJson() {
			super(57690);
		}

		@Override
		protected JSONObject pollApi() throws IOException {
			final String testJson = "{\"57690\": {\"success\": true, \"data\": {\"header_image\": \"imageurl\", \"type\": \"game\", \"name\": \"Tropico 4\", \"steam_appid\": 57690, \"developers\": [\"Haemimont Games\"]}}}";
			return new JSONObject(testJson);
		}
		
	}
	
	private class NoErrorGameSearchAPI extends GameSearchAPI {

		public NoErrorGameSearchAPI() {
			super(57690);
		}

		@Override
		protected JSONObject pollApi() throws IOException {
			final String testJson = "{\"57690\": {\"success\": true, \"data\": {\"header_image\": \"imageurl\", \"type\": \"game\", \"name\": \"Tropico 4\", \"steam_appid\": 57690, \"price_overview\": {\"final\": 1999, \"initial\": 1999, \"discount_percent\": 0}, \"developers\": [\"Haemimont Games\"]}}}";
			return new JSONObject(testJson);
		}
		
	}
	
	private class OnSaleGameSearchAPI extends GameSearchAPI {

		public OnSaleGameSearchAPI() {
			super(57690);
		}

		@Override
		protected JSONObject pollApi() throws IOException {
			final String testJson = "{\"57690\": {\"success\": true, \"data\": {\"header_image\": \"imageurl\", \"type\": \"game\", \"name\": \"Tropico 4\", \"steam_appid\": 57690, \"price_overview\": {\"final\": 1999, \"initial\": 1999, \"discount_percent\": 5}, \"developers\": [\"Haemimont Games\"]}}}";
			return new JSONObject(testJson);
		}
		
	}
	
	private class UnsuccessfulGameSearchAPI extends GameSearchAPI {

		public UnsuccessfulGameSearchAPI() {
			super(57690);
		}

		@Override
		protected JSONObject pollApi() throws IOException {
			final String testJson = "{\"57690\": {\"success\": false, \"data\": {\"header_image\": \"imageurl\", \"type\": \"game\", \"name\": \"Tropico 4\", \"steam_appid\": 57690, \"price_overview\": {\"final\": 1999, \"initial\": 1999, \"discount_percent\": 0}, \"developers\": [\"Haemimont Games\"]}}}";
			return new JSONObject(testJson);
		}
		
	}
	
	private class NotAGameGameSearchAPI extends GameSearchAPI {

		public NotAGameGameSearchAPI() {
			super(57690);
		}

		@Override
		protected JSONObject pollApi() throws IOException {
			final String testJson = "{\"57690\": {\"success\": true, \"data\": {\"header_image\": \"imageurl\", \"type\": \"dlc\", \"name\": \"Tropico 4\", \"steam_appid\": 57690, \"price_overview\": {\"final\": 1999, \"initial\": 1999, \"discount_percent\": 0}, \"developers\": [\"Haemimont Games\"]}}}";
			return new JSONObject(testJson);
		}
		
	}
	
	@Test
	public void testPullsData() throws IOException {
		NoErrorGameSearchAPI api = new NoErrorGameSearchAPI();
		Game tropico = api.makeRequest();
		
		assertAll(() -> assertEquals("Tropico 4", tropico.getTitle()),
				() -> assertEquals(57690, tropico.getAppId()),
				() -> assertEquals("Haemimont Games", tropico.getStudioDescription()),
				() -> assertEquals(19.99, tropico.getInitialPrice(), 0.00001),
				() -> assertEquals(19.99, tropico.getCurrentPrice(), 0.00001),
				() -> assertEquals(false, tropico.isOnSale()),
				() -> assertEquals("https://store.steampowered.com/app/57690", tropico.getSteamLink()));
	}

	@Test
	public void testGameOnSale() throws IOException {
		OnSaleGameSearchAPI api = new OnSaleGameSearchAPI();
		Game tropico = api.makeRequest();
		
		assertEquals(true, tropico.isOnSale());
	}
	
	@Test
	public void testUnsuccessfulRequest() throws IOException {
		UnsuccessfulGameSearchAPI api = new UnsuccessfulGameSearchAPI();
		assertThrows(GameNotFoundException.class, () -> api.makeRequest());
	}
	
	@Test
	public void testFoundItemButNotAGameRequest() throws IOException {
		NotAGameGameSearchAPI api = new NotAGameGameSearchAPI();
		assertThrows(GameNotFoundException.class, () -> api.makeRequest());
	}
	
	@Test
	public void testInvalidJson() throws IOException {
		assertThrows(GameNotFoundException.class, () -> {
			InvalidJson api = new InvalidJson();
			api.makeRequest();
		});
	}
}
