package com.steamscout.application.test.connection.serverwatchlistmodificationservice;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.steamscout.application.connection.ServerWatchlistModificationService;
import com.steamscout.application.model.game_data.Watchlist;
import com.steamscout.application.model.user.Credentials;
import com.steamscout.application.model.user.User;

public class TestModifyGameOnWatchlist {

	private class TestServerWatchlistModificationService extends ServerWatchlistModificationService {

		@Override
		protected Watchlist send() {
			return new Watchlist();
		}
		
	}
	
	@Test
	public void testModifies() {
		TestServerWatchlistModificationService service = new TestServerWatchlistModificationService();
		Watchlist result = service.modifyGameOnWatchlist(new User(new Credentials("test", "123")), null, null);
		assertEquals(0, result.size());
	}

}
