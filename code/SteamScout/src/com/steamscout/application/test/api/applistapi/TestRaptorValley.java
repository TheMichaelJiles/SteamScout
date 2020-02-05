package com.steamscout.application.test.api.applistapi;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.steamscout.application.model.api.AppListAPI;

public class TestRaptorValley {

	@Test
	public void testCanAccessRaptorValley() throws IOException {
		AppListAPI api = new AppListAPI();
		Map<Integer, String> games = api.makeRequest();
		
		assertEquals("Raptor Valley", games.get(524120));
	}

}
