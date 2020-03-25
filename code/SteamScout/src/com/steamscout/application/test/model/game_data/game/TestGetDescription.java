package com.steamscout.application.test.model.game_data.game;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.steamscout.application.model.game_data.Game;

public class TestGetDescription {

	@Test
	public void testBuildsCorrectDescription() {
		Game game = new Game(1, "bop");
		game.setCurrentPrice(4);
		game.setInitialPrice(5);
		game.setOnSale(false);
		
		String expected = "Title: bop" + System.lineSeparator()
						+ "AppId: 1" + System.lineSeparator()
						+ "SteamLink: https://store.steampowered.com/app/1" + System.lineSeparator()
						+ "InitialPrice: 5.0" + System.lineSeparator()
						+ "CurrentPrice: 4.0" + System.lineSeparator()
						+ "IsOnSale: false" + System.lineSeparator()
						+ System.lineSeparator();
		
		assertEquals(expected, game.getDescription());
	}

}
