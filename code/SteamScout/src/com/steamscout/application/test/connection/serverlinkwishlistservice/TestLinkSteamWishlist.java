package com.steamscout.application.test.connection.serverlinkwishlistservice;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.steamscout.application.connection.ServerLinkWishlistService;
import com.steamscout.application.model.game_data.Watchlist;

public class TestLinkSteamWishlist {

	private class TestServerLinkWishlistService extends ServerLinkWishlistService {

		public TestServerLinkWishlistService(String accountId) {
			super(accountId);
		}

		@Override
		protected Watchlist send() {
			return new Watchlist();
		}
		
	}
	
	@Test
	public void testLinks() {
		TestServerLinkWishlistService service = new TestServerLinkWishlistService("234234");
		Watchlist result = service.linkSteamWishlist("test");
		assertEquals(0, result.size());
	}

}
