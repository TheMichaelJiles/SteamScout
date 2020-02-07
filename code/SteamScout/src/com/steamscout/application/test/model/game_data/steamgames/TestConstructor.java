package com.steamscout.application.test.model.game_data.steamgames;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.steamscout.application.model.game_data.SteamGames;

public class TestConstructor {

	@Test
	public void testConstruction() {
		SteamGames games = new SteamGames();
		
		assertAll(() -> assertEquals(0, games.getIds().size()),
				() -> assertEquals(0, games.getTitles().size()));
	}

}
