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
	public void testNotAllowNullStudioDescription() {
		Game game = new Game(1, "bop");
		assertThrows(IllegalArgumentException.class, () -> game.setStudioDescription(null));
	}
	
	@Test
	public void testNotAllowNullSteamLink() {
		Game game = new Game(1, "bop");
		assertThrows(IllegalArgumentException.class, () -> game.setSteamLink(null));
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
	public void testNotAllowNegativeUserPriceThreshold() {
		Game game = new Game(1, "bop");
		assertThrows(IllegalArgumentException.class, () -> game.setUserPriceThreshold(-1));
	}
	
	@Test
	public void testValidPostconditions() {
		Game game = new Game(1, "bop");
		game.setStudioDescription("boo");
		game.setSteamLink("bam");
		game.setCurrentPrice(4);
		game.setInitialPrice(5);
		game.setUserPriceThreshold(6);
		game.setOnSale(false);
		
		assertAll(() -> assertEquals("bop", game.getTitle()),
				() -> assertEquals("boo", game.getStudioDescription()),
				() -> assertEquals("bam", game.getSteamLink()),
				() -> assertEquals(4, game.getCurrentPrice(), 0.000001),
				() -> assertEquals(5, game.getInitialPrice(), 0.000001),
				() -> assertEquals(6, game.getUserPriceThreshold(), 0.000001),
				() -> assertEquals(false, game.isOnSale()),
				() -> assertEquals(1, game.getAppId()));
	}
}
