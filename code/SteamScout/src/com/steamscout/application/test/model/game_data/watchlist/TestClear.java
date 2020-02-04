package com.steamscout.application.test.model.game_data.watchlist;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.steamscout.application.model.game_data.Game;
import com.steamscout.application.model.game_data.Watchlist;

public class TestClear {

	@Test
	public void testOnEmptyList() {
		Watchlist list = new Watchlist();
		list.clear();
		
		assertEquals(0, list.size());
	}
	
	@Test
	public void testClearingMultipleGames() {
		Watchlist list = new Watchlist();
		list.addGame(new Game(4, "test"));
		list.addGame(new Game(5, "boooo"));
		list.clear();
		
		assertEquals(0, list.size());
	}

}
