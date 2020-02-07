package com.steamscout.application.test.model.game_data.watchlist;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.steamscout.application.model.game_data.Game;
import com.steamscout.application.model.game_data.Watchlist;

public class TestRemove {

	@Test
	public void testSuccessulRemoval() {
		Game test = new Game(4, "test");
		Watchlist list = new Watchlist();
		
		list.add(test);
		list.remove(test);
		
		assertEquals(0, list.size());
	}
	
	@Test
	public void testDoesNotRemoveIfDoesNotHave() {
		Game test = new Game(4, "test");
		Game test0 = new Game(5, "test0");
		Watchlist list = new Watchlist();
		
		list.add(test);
		list.remove(test0);
		
		assertEquals(1, list.size());
	}

	@Test
	public void testRemovingAll() {
		Game test = new Game(4, "test");
		Game test0 = new Game(5, "test0");
		Watchlist list = new Watchlist();
		List<Game> other = new ArrayList<Game>();
		
		list.add(test);
		list.add(test0);
		
		other.add(test);
		other.add(test0);
		
		list.removeAll(other);
		
		assertEquals(true, list.isEmpty());
	}
}