package com.steamscout.application.test.model.game_data.game;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.steamscout.application.model.game_data.Game;

public class TestEquals {

	@Test
	public void testDoesEqualSameObject() {
		Game game = new Game(4, "Luke");
		Game otherGame = new Game(4, "Chicken");
		
		assertEquals(true, game.equals(otherGame));
	}

	@Test
	public void testDoesNotEqualSameTypeObject() {
		Game game = new Game(4, "Luke");
		Game otherGame = new Game(5, "Chicken");
		
		assertEquals(false, game.equals(otherGame));
	}
	
	@Test
	public void testDoesNotEqualDifferentTypeObject() {
		Game game = new Game(4, "Luke");
		Object otherGame = new Object();
		
		assertEquals(false, game.equals(otherGame));
	}
	
	@Test
	public void testSameHashcodes() {
		Game firstGame = new Game(5, "Luke");
		Game secondGame = new Game(5, "Chicken");
		
		assertEquals(firstGame.hashCode(), secondGame.hashCode());
	}
}
