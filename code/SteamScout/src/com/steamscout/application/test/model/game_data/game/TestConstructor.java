package com.steamscout.application.test.model.game_data.game;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.steamscout.application.model.game_data.Game;

public class TestConstructor {

	@Test
	public void testNotAllowNullTitle() {
		assertThrows(IllegalArgumentException.class, () -> new Game(1, null));
	}
	
	@Test
	public void testNotAllowNegativeCurrentPrice() {
		Game game = new Game(1, "bop");
		assertThrows(IllegalArgumentException.class, () -> game.setCurrentPrice(-1));
	}
	
	@Test
	public void testNotAllowNegativeInitialPrice() {
		Game game = new Game(1, "bop");
		assertThrows(IllegalArgumentException.class, () -> game.setInitialPrice(-1));
	}
	
	@Test
	public void testValidPostconditions() {
		Game game = new Game(1, "bop");
		game.setCurrentPrice(4);
		game.setInitialPrice(5);
		game.setOnSale(false);
		
		assertAll(() -> assertEquals("bop", game.getTitle()),
				() -> assertEquals("https://store.steampowered.com/app/1", game.getSteamLink()),
				() -> assertEquals(4, game.getCurrentPrice(), 0.000001),
				() -> assertEquals(5, game.getInitialPrice(), 0.000001),
				() -> assertEquals(false, game.isOnSale()),
				() -> assertEquals(1, game.getAppId()));
	}
}
