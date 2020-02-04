package com.steamscout.application.test.model.game_data.watchlist;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.steamscout.application.model.game_data.Game;
import com.steamscout.application.model.game_data.Watchlist;

public class TestAddGame {

	@Test
	public void testSuccessfulAddition() {
		Game test = new Game(4, "Luke");
		Watchlist list = new Watchlist();
		
		list.addGame(test);
		
		assertEquals(1, list.size());
	}
	
	@Test
	public void testNotAllowSameGame() {
		Game test = new Game(4, "Luke");
		Watchlist list = new Watchlist();
		
		list.addGame(test);
		assertThrows(IllegalArgumentException.class, () -> list.addGame(test));
	}

}
