package com.steamscout.application.test.connection.servergamefetchservice;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.steamscout.application.connection.ServerGameFetchService;

public class TestFetchGames {

	private class TestServerGameFetchService extends ServerGameFetchService {

		@Override
		protected Map<String, Integer> send() {
			return new HashMap<String, Integer>();
		}
		
	}
	
	@Test
	public void testFetches() {
		TestServerGameFetchService service = new TestServerGameFetchService();
		Map<String, Integer> result = service.fetchGames();
		assertEquals(0, result.size());
	}

}
