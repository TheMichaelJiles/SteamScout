package com.steamscout.application.test.model.api.applistapi;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.Map;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import com.steamscout.application.model.api.AppListAPI;

public class TestRaptorValley {

	private class TestAppListAPI extends AppListAPI {

		@Override
		protected JSONObject pollApi() throws IOException {
			final String testJson = "{\"applist\": {\"apps\": [{\"appid\": 524120, \"name\": \"Raptor Valley\"}]}}";
			return new JSONObject(testJson);
		}
	}
	
	@Test
	public void testMakeRequest() throws IOException {
		TestAppListAPI api = new TestAppListAPI();
		Map<String, Integer> games = api.makeRequest();
		
		assertEquals(524120, games.get("Raptor Valley"));
	}

}
