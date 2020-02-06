package com.steamscout.application.test.model.api.apirequest;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.json.JSONObject;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.steamscout.application.model.api.APIRequest;

@Disabled
public class TestPollApi {

	private class TestApiRequest extends APIRequest {

		protected TestApiRequest() {
			super("http://api.steampowered.com/ISteamApps/GetAppList/v0001/");
		}

		@Override
		public JSONObject makeRequest() throws IOException {
			return this.pollApi();
		}
		
	}
	
	@Test
	public void testPollsCorrectly() throws IOException {
		TestApiRequest request = new TestApiRequest();
		JSONObject result = request.makeRequest();
		JSONObject applist = result.getJSONObject("applist");
		
		assertNotEquals(null, applist);
	}

}
