package com.steamscout.application.test.connection.serverwatchlistadditionservice;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.steamscout.application.connection.ServerWatchlistAdditionService;
import com.steamscout.application.model.game_data.Watchlist;

public class TestAddGameToWatchlist {

	private class TestServerWatchlistAdditionService extends ServerWatchlistAdditionService {

		@Override
		protected Watchlist send() {
			return new Watchlist();
		}
		
	}
	
	@Test
	public void testAdd() {
		TestServerWatchlistAdditionService service = new TestServerWatchlistAdditionService();
		Watchlist result = service.addGameToWatchlist(null, null);
		assertEquals(0, result.size());
	}

}
