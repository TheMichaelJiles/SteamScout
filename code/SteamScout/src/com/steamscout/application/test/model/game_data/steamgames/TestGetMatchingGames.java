package com.steamscout.application.test.model.game_data.steamgames;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.steamscout.application.model.game_data.Game;
import com.steamscout.application.model.game_data.SteamGames;

public class TestGetMatchingGames {

	private SteamGames games;
	
	@BeforeEach
	public void setUp() {
		this.games = new SteamGames();
		Map<String, Integer> data = new HashMap<String, Integer>();
		data.put("test0", 0);
		data.put("test01", 1);
		data.put("test2", 2);
		this.games.initializeGames(data);
	}
	
	@Test
	public void testGetsAllGames() throws InterruptedException {
		Collection<Game> matches = this.games.getMatchingGames("test");
		
		assertAll(() -> assertEquals(3, matches.size()),
				() -> assertEquals(true, this.games.getTitles().containsAll(matches.stream().map(game -> game.getTitle()).collect(Collectors.toList()))));
	}

	@Test
	public void testContainsSomeGames() throws InterruptedException {
		Collection<Game> matches = this.games.getMatchingGames("test0");
		
		assertAll(() -> assertEquals(2, matches.size()),
				() -> assertEquals(true, this.games.getTitles().containsAll(matches.stream().map(game -> game.getTitle()).collect(Collectors.toList()))));
	}
	
	@Test
	public void testContainsNoGames() throws InterruptedException {
		Collection<Game> matches = this.games.getMatchingGames("zr");
		
		assertAll(() -> assertEquals(0, matches.size()),
				() -> assertEquals(true, this.games.getTitles().containsAll(matches.stream().map(game -> game.getTitle()).collect(Collectors.toList()))));
	}
}
