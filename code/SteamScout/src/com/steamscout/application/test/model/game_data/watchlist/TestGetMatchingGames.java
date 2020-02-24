package com.steamscout.application.test.model.game_data.watchlist;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.steamscout.application.model.game_data.Game;
import com.steamscout.application.model.game_data.Watchlist;

public class TestGetMatchingGames {

	private Watchlist list;
	
	@BeforeEach
	public void setUp() {
		this.list = new Watchlist();
		this.list.add(new Game(34, "Bop"));
		this.list.add(new Game(35, "Bope"));
	}
	
	@Test
	public void testNotAllowNullTerm() {
		assertThrows(IllegalArgumentException.class, () -> this.list.getMatchingGames(null));
	}
	
	@Test
	public void testNoMatches() {
		Collection<Game> matches = this.list.getMatchingGames("zim");
		
		assertEquals(0, matches.size());
	}

	@Test
	public void testOneMatch() {
		Collection<Game> matches = this.list.getMatchingGames("bope");
		
		assertEquals(1, matches.size());
	}
	
	@Test
	public void testMultipleMatches() {
		Collection<Game> matches = this.list.getMatchingGames("bop");
		
		assertEquals(2, matches.size());
	}
	
	@Test
	public void testCaseInsensitive() {
		Collection<Game> matches = this.list.getMatchingGames("BOP");
		
		assertEquals(2, matches.size());
	}
}
