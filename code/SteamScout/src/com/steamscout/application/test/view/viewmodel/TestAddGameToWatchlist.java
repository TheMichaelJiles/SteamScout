package com.steamscout.application.test.view.viewmodel;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.steamscout.application.connection.exceptions.InvalidAdditionException;
import com.steamscout.application.connection.interfaces.WatchlistAdditionService;
import com.steamscout.application.model.game_data.Game;
import com.steamscout.application.model.game_data.Watchlist;
import com.steamscout.application.model.user.Credentials;
import com.steamscout.application.model.user.User;
import com.steamscout.application.view.ViewModel;

public class TestAddGameToWatchlist {

	private class SuccessfulAdditionService implements WatchlistAdditionService {

		@Override
		public Watchlist addGameToWatchlist(Credentials credentials, Game gameToAdd) {
			Watchlist watchlist = new Watchlist();
			watchlist.add(gameToAdd);
			return watchlist;
		}
	}

	private class InvalidAdditionService implements WatchlistAdditionService {
		@Override
		public Watchlist addGameToWatchlist(Credentials credentials, Game gameToAdd) throws InvalidAdditionException {
			throw new InvalidAdditionException(gameToAdd);
		}
	}
	
	private class NullReturnAdditionService implements WatchlistAdditionService {

		@Override
		public Watchlist addGameToWatchlist(Credentials credentials, Game gameToAdd) throws InvalidAdditionException {
			return null;
		}
		
	}

	@BeforeEach
	public void setUp() {
		ViewModel.get().watchlistProperty().getValue().clear();
		ViewModel.get().userProperty().setValue(null);
	}

	@AfterEach
	public void tearDown() {
		ViewModel.get().watchlistProperty().getValue().clear();
		ViewModel.get().userProperty().setValue(null);
		ViewModel.get().browsePageSelectedGameProperty().setValue(null);
	}

	@Test
	public void testNotAllowNullGame() {
		assertThrows(IllegalArgumentException.class, () -> ViewModel.get().addGameToWatchlist(null));
	}

	@Test
	public void testSuccessfulAddition() {
		ViewModel vm = ViewModel.get();
		vm.browsePageSelectedGameProperty().setValue(new Game(1, "test"));
		vm.userProperty().setValue(new User(new Credentials("twhal", "123")));
		vm.addSelectedGameToWatchlist(new SuccessfulAdditionService());

		assertEquals(1, vm.watchlistProperty().getValue().size());
	}

	@Test
	public void testInvalidAddition() {
		ViewModel vm = ViewModel.get();
		Game game = new Game(1, "test");
		vm.browsePageSelectedGameProperty().setValue(game);
		vm.userProperty().setValue(new User(new Credentials("twhal", "123")));
		boolean wasAdded = vm.addSelectedGameToWatchlist(new InvalidAdditionService());
		
		assertEquals(false, wasAdded);
	}
	
	@Test
	public void testNullUser() {
		ViewModel vm = ViewModel.get();
		vm.userProperty().setValue(null);
		Game game = new Game(1, "test");
		assertEquals(false, vm.addGameToWatchlist(game));
	}
	
	@Test
	public void testAdditionSystemNull() {
		assertThrows(IllegalArgumentException.class, () -> ViewModel.get().addSelectedGameToWatchlist(null));
	}
	
	@Test
	public void testAdditionSystemReturnNull() {
		ViewModel vm = ViewModel.get();
		vm.userProperty().setValue(new User(new Credentials("test", "1234")));
		Game game = new Game(1, "test");
		vm.browsePageSelectedGameProperty().setValue(game);
		assertThrows(IllegalArgumentException.class, () -> vm.addSelectedGameToWatchlist(new NullReturnAdditionService()));
	}
}
