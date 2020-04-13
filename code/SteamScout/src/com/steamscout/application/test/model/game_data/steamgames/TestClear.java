package com.steamscout.application.test.model.game_data.steamgames;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.steamscout.application.model.game_data.SteamGames;

public class TestClear {

	@Test
	public void testClearsCorrectly() {
		SteamGames games = new SteamGames();
		Map<String, Integer> data = new HashMap<String, Integer>();
		data.put("yes", 3);
		games.initializeGames(data);
		
		int previousSize = games.getTitles().size();
		games.clear();
		int postSize = games.getTitles().size();
		
		assertAll(() -> assertEquals(1, previousSize),
				() -> assertEquals(0, postSize));
	}

}
