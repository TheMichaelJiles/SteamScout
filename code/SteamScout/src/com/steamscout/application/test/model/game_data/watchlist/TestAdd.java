package com.steamscout.application.test.model.game_data.watchlist;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.steamscout.application.model.game_data.Game;
import com.steamscout.application.model.game_data.Watchlist;

public class TestAdd {

	@Test
	public void testNotAllowNullGame() {
		Watchlist list = new Watchlist();
		assertThrows(IllegalArgumentException.class, () -> list.add(null));
	}
	
	@Test
	public void testSuccessfulAddition() {
		Game test = new Game(4, "Luke");
		Watchlist list = new Watchlist();
		
		list.add(test);
		
		assertEquals(false, list.isEmpty());
	}
	
	@Test
	public void testDoesNotAddSameGame() {
		Game test = new Game(4, "Luke");
		Watchlist list = new Watchlist();
		
		list.add(test);
		list.add(test);
		
		assertEquals(1, list.size());
	}

}
