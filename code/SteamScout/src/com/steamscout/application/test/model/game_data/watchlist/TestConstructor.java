package com.steamscout.application.test.model.game_data.watchlist;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.steamscout.application.model.game_data.Watchlist;

public class TestConstructor {

	@Test
	public void testMeetsPostconditions() {
		Watchlist list = new Watchlist();
		
		assertEquals(0, list.size());
	}

}
