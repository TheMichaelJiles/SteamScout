package com.steamscout.application.test.model.game_data.steamgames;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.steamscout.application.model.game_data.SteamGames;

public class TestGetMatchingIds {

	private SteamGames games;
	
	@BeforeEach
	public void setUp() {
		this.games = new SteamGames();
		Map<String, Integer> data = new HashMap<String, Integer>();
		data.put("test", 1);
		data.put("test0", 2);
		this.games.initializeGames(data);
	}
	
	@Test
	public void testNotAllowNullSearch() {
		assertThrows(IllegalArgumentException.class, () -> this.games.getMatchingIds(null));
	}

	@Test
	public void testNoMatches() throws InterruptedException {
		Collection<Integer> ids = this.games.getMatchingIds("awlerkghlkw");
		assertEquals(0, ids.size());
	}
	
	@Test
	public void testAllLowerCaseMatches() throws InterruptedException {
		Collection<Integer> ids = this.games.getMatchingIds("te");
		assertEquals(2, ids.size());
	}
	
	@Test
	public void testAllUpperCaseMatches() throws InterruptedException {
		Collection<Integer> ids = this.games.getMatchingIds("TE");
		assertEquals(2, ids.size());
	}
	
	@Test
	public void testOnlyOneMatch() throws InterruptedException {
		Collection<Integer> ids = this.games.getMatchingIds("test0");
		assertEquals(1, ids.size());
	}
}
