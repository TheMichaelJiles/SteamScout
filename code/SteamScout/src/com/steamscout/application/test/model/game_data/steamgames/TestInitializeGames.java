package com.steamscout.application.test.model.game_data.steamgames;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.steamscout.application.model.game_data.SteamGames;

public class TestInitializeGames {

	private SteamGames games;
	
	@BeforeEach
	public void setUp() {
		this.games = new SteamGames();
	}
	
	@Test
	public void testNotAllowNullData() {
		assertThrows(IllegalArgumentException.class, () -> this.games.initializeGames(null));
	}

	@Test
	public void testNotAllowEmptyData() {
		assertThrows(IllegalArgumentException.class, () -> this.games.initializeGames(Collections.emptyMap()));
	}
	
	@Test
	public void testNotAllowRepeatedInitializations() {
		Map<String, Integer> data = new HashMap<String, Integer>();
		data.put("yes", 3);
		this.games.initializeGames(data);
		assertThrows(IllegalArgumentException.class, () -> this.games.initializeGames(data));
	}
	
	@Test
	public void testNotAllowNullKeys() {
		Map<String, Integer> data = new HashMap<String, Integer>();
		data.put(null, 3);
		assertThrows(IllegalArgumentException.class, () -> this.games.initializeGames(data));
	}
	
	@Test
	public void testNotAllowNullValues() {
		Map<String, Integer> data = new HashMap<String, Integer>();
		data.put("test", null);
		assertThrows(IllegalArgumentException.class, () -> this.games.initializeGames(data));
	}
	
	@Test
	public void testValidAddition() {
		Map<String, Integer> data = new HashMap<String, Integer>();
		data.put("yes", 3);
		this.games.initializeGames(data);
		
		assertAll(() -> assertEquals(false, this.games.getTitles().isEmpty()),
				() -> assertEquals(false, this.games.getIds().isEmpty()));
	}
}
