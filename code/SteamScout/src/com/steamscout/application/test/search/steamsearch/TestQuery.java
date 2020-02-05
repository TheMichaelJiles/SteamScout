package com.steamscout.application.test.search.steamsearch;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.steamscout.application.model.game_data.Game;
import com.steamscout.application.search.SteamSearch;

@Disabled
public class TestQuery {

	@Test
	public void testPullsSkyrimData() {
		Iterable<Game> games = SteamSearch.query("skyrim");
		List<Game> testGameList = new ArrayList<Game>();
		games.forEach(game -> testGameList.add(game));
		
		Game originalSkyrim = new Game(72850, "The Elder Scrolls V: Skyrim");
		Game skyrimVR = new Game(611670, "The Elder Scrolls V: Skyrim VR");
		Game skyrimSpecialEdition = new Game(489830, "The Elder Scrolls V: Skyrim Special Edition");
		
		assertAll(() -> assertEquals(true, testGameList.contains(originalSkyrim)),
				() -> assertEquals(true, testGameList.contains(skyrimVR)),
				() -> assertEquals(true, testGameList.contains(skyrimSpecialEdition)));
	}

}
