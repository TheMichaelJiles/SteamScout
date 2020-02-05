package com.steamscout.application.test.model.game_data.watchlist;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.steamscout.application.model.game_data.Game;
import com.steamscout.application.model.game_data.Watchlist;

public class TestToArray {

	@Test
	public void testDefaultToArray() {
		Game test0 = new Game(0, "a");
		Game test1 = new Game(1, "aa");
		Game test2 = new Game(2, "aaa");
		Watchlist list = new Watchlist();
		list.add(test0);
		list.add(test1);
		list.add(test2);
		
		assertEquals(3, list.toArray().length);
	}

	@Test
	public void testResizedArray() {
		Game test0 = new Game(0, "a");
		Game test1 = new Game(1, "aa");
		Game test2 = new Game(2, "aaa");
		Watchlist list = new Watchlist();
		list.add(test0);
		list.add(test1);
		list.add(test2);
		
		
		Game[] firstGame = new Game[1];
		firstGame = list.toArray(firstGame);
		assertEquals(test0, firstGame[0]);
	}
}
