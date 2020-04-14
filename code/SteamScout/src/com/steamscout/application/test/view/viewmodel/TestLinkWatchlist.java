package com.steamscout.application.test.view.viewmodel;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.steamscout.application.connection.interfaces.LinkWishlistService;
import com.steamscout.application.model.game_data.Game;
import com.steamscout.application.model.game_data.Watchlist;
import com.steamscout.application.model.user.Credentials;
import com.steamscout.application.model.user.User;
import com.steamscout.application.view.ViewModel;

public class TestLinkWatchlist {

	private class PassingLinkService implements LinkWishlistService {

		@Override
		public Watchlist linkSteamWishlist(String username) {
			Watchlist games = new Watchlist();
			games.add(new Game(1, "game1"));
			games.add(new Game(2, "game2"));
			return games;
		}
		
	}
	
	@BeforeEach
	public void setUp() {
		ViewModel.get().userProperty().setValue(new User(new Credentials("user", "pass")));
	}
	
	@AfterEach
	public void tearDown() {
		ViewModel.get().userProperty().setValue(null);
		ViewModel.get().watchlistProperty().clear();
	}
	
	@Test
	public void testNotAllowNullLinkingSystem() {
		assertThrows(IllegalArgumentException.class, () -> ViewModel.get().linkWatchlist(null));
	}

	@Test
	public void testNothingChangesOnFailingLink() {
		ViewModel.get().linkWatchlist(username -> null);
		assertEquals(0, ViewModel.get().userProperty().getValue().getWatchlist().size());
	}
	
	@Test
	public void testLinkedGamesAreAdded() {
		ViewModel.get().linkWatchlist(new PassingLinkService());
		assertAll(() -> assertEquals(2, ViewModel.get().userProperty().getValue().getWatchlist().size()),
				() -> assertEquals(2, ViewModel.get().watchlistProperty().getValue().size()));
	}
}
