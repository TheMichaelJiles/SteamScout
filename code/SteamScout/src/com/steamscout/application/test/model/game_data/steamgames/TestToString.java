package com.steamscout.application.test.model.game_data.steamgames;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.steamscout.application.model.game_data.SteamGames;

public class TestToString {

	@Test
	public void testBuildsString() {
		SteamGames games = new SteamGames();
		Map<String, Integer> data = new HashMap<String, Integer>();
		data.put("test", 1);
		data.put("test0", 2);
		games.initializeGames(data);
		
		String expected = "test - 1" + System.lineSeparator()
						+ "test0 - 2" + System.lineSeparator();
		String result = games.toString();
		
		assertEquals(expected, result);
	}

}
