package com.steamscout.application.test.model.game_data.watchlist;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Iterator;

import org.junit.jupiter.api.Test;

import com.steamscout.application.model.game_data.Game;
import com.steamscout.application.model.game_data.Watchlist;

public class TestIterator {

	@Test
	public void testCanIterate() {
		Watchlist list = new Watchlist();
		list.add(new Game(5, "test"));
		list.add(new Game(6, "test0"));
		
		int sum = 0;
		Iterator<Game> iter = list.iterator();
		while (iter.hasNext()) {
			Game currentGame = iter.next();
			sum += currentGame.getAppId();
		}
		
		assertEquals(11, sum);
	}

}
