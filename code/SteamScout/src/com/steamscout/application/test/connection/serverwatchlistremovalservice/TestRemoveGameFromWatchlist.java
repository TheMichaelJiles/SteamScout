package com.steamscout.application.test.connection.serverwatchlistremovalservice;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.steamscout.application.connection.ServerWatchlistRemovalService;
import com.steamscout.application.model.game_data.Watchlist;
import com.steamscout.application.model.user.Credentials;
import com.steamscout.application.model.user.User;

public class TestRemoveGameFromWatchlist {

	private class TestServerWatchlistRemovalService extends ServerWatchlistRemovalService {

		@Override
		protected Watchlist send() {
			return new Watchlist();
		}
		
	}
	
	@Test
	public void testRemoval() {
		TestServerWatchlistRemovalService service = new TestServerWatchlistRemovalService();
		Watchlist result = service.removeGameFromWatchlist(new User(new Credentials("test", "123")), null);
		assertEquals(0, result.size());
	}

}
