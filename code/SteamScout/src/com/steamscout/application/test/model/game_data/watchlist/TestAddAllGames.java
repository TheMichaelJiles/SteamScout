package com.steamscout.application.test.model.game_data.watchlist;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.jupiter.api.Test;

import com.steamscout.application.model.game_data.Game;
import com.steamscout.application.model.game_data.Watchlist;

public class TestAddAllGames {

	@Test
	public void testNotAllowNullCollection() {
		Watchlist list = new Watchlist();
		assertThrows(IllegalArgumentException.class, () -> list.addAllGames(null));
	}
	
	@Test
	public void testAddsAllUniqueGames() {
		Game game0 = new Game(0, "a");
		Game game1 = new Game(1, "aa");
		Game game2 = new Game(2, "aaa");
		
		Collection<Game> games = new ArrayList<Game>();
		games.add(game0);
		games.add(game1);
		games.add(game2);
		
		Watchlist list = new Watchlist();
		list.addAllGames(games);
		
		assertAll(() -> assertEquals(true, list.contains(game0)),
				() -> assertEquals(true, list.contains(game1)),
				() -> assertEquals(true, list.contains(game2)));
	}

	@Test
	public void testAddsOnlyUniqueGames() {
		Game game0 = new Game(0, "a");
		Game game1 = new Game(1, "aa");
		Game game2 = new Game(2, "aaa");
		
		Collection<Game> games = new ArrayList<Game>();
		games.add(game0);
		games.add(game1);
		games.add(game2);
		
		Watchlist list = new Watchlist();
		list.addGame(game0);
		list.addAllGames(games);
		
		assertAll(() -> assertEquals(true, list.contains(game0)),
				() -> assertEquals(true, list.contains(game1)),
				() -> assertEquals(true, list.contains(game2)),
				() -> assertEquals(3, list.size()));
	}
}
