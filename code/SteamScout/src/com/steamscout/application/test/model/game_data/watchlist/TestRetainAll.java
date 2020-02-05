package com.steamscout.application.test.model.game_data.watchlist;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.jupiter.api.Test;

import com.steamscout.application.model.game_data.Game;
import com.steamscout.application.model.game_data.Watchlist;

public class TestRetainAll {

	@Test
	public void testRetainsAll() {
		Game game0 = new Game(0, "a");
		Game game1 = new Game(1, "aa");
		Game game2 = new Game(2, "aaa");
		
		Collection<Game> games = new ArrayList<Game>();
		games.add(game0);
		
		Watchlist list = new Watchlist();
		list.add(game0);
		list.add(game1);
		list.add(game2);
		
		list.retainAll(games);
		
		assertEquals(1, list.size());
	}

}
