package com.steamscout.application.test.connection.serverwatchlistfetchservice;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.steamscout.application.connection.ServerWatchlistFetchService;
import com.steamscout.application.model.game_data.Watchlist;

public class TestFetchWatchlist {

	private class TestServerWatchlistFetchService extends ServerWatchlistFetchService {

		@Override
		protected Watchlist send() {
			return new Watchlist();
		}
		
	}
	
	@Test
	public void testFetches() {
		TestServerWatchlistFetchService service = new TestServerWatchlistFetchService();
		Watchlist result = service.fetchWatchlist("chicken");
		assertEquals(0, result.size());
	}

}
