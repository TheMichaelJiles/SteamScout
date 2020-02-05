package com.steamscout.application.test.model.game_data.watchlist;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.steamscout.application.model.game_data.Game;
import com.steamscout.application.model.game_data.Watchlist;

public class TestContainsAll {

	@Test
	public void test() {
		Game game0 = new Game(0, "a");
		Game game1 = new Game(1, "aa");
		Game game2 = new Game(2, "aaa");
		Game game3 = new Game(3, "aaaa");
		Watchlist list = new Watchlist();
		List<Game> other = new ArrayList<Game>();
		
		list.add(game0);
		list.add(game1);
		list.add(game2);
		list.add(game3);
		
		other.add(game0);
		other.add(game1);
		
		assertEquals(true, list.containsAll(other));
	}

}
