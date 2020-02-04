package com.steamscout.application.test.model.game_data.watchlist;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.steamscout.application.model.game_data.Game;
import com.steamscout.application.model.game_data.Watchlist;

public class TestRemoveGame {

	@Test
	public void testSuccessulRemoval() {
		Game test = new Game(4, "test");
		Watchlist list = new Watchlist();
		
		list.addGame(test);
		list.removeGame(test);
		
		assertEquals(0, list.size());
	}
	
	@Test
	public void testDoesNotRemoveIfDoesNotHave() {
		Game test = new Game(4, "test");
		Game test0 = new Game(5, "test0");
		Watchlist list = new Watchlist();
		
		list.addGame(test);
		list.removeGame(test0);
		
		assertEquals(1, list.size());
	}

}
