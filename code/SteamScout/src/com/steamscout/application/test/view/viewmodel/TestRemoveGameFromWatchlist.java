package com.steamscout.application.test.view.viewmodel;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.steamscout.application.connection.ServerWatchlistRemovalService;
import com.steamscout.application.connection.interfaces.WatchlistAdditionService;
import com.steamscout.application.connection.interfaces.WatchlistRemovalService;
import com.steamscout.application.model.game_data.Game;
import com.steamscout.application.model.game_data.Watchlist;
import com.steamscout.application.model.user.Credentials;
import com.steamscout.application.model.user.User;
import com.steamscout.application.view.ViewModel;

//TODO
class TestRemoveGameFromWatchlist {
	
	private class SuccessfulRemovalService implements WatchlistRemovalService {

		@Override
		public Watchlist removeGameFromWatchlist(User currentUser, Game game) {
			currentUser.getWatchlist().remove(game);
			return currentUser.getWatchlist();
		}
	}
	
	private class SuccessfulAdditionService implements WatchlistAdditionService {

		@Override
		public Watchlist addGameToWatchlist(Credentials credentials, Game gameToAdd) {
			Watchlist watchlist = new Watchlist();
			watchlist.add(gameToAdd);
			return watchlist;
		}
	}
	
	private ViewModel vm;
	private Game testGame;
	
	@BeforeEach
	public void setUp() {
		this.vm = ViewModel.get();
		this.vm.watchlistProperty().getValue().clear();
		this.vm.userProperty().setValue(null);
		
		this.testGame = new Game(1, "game");
		
		this.vm.browsePageSelectedGameProperty().setValue(this.testGame);
		this.vm.userProperty().setValue(new User(new Credentials("person", "0000")));
		this.vm.addSelectedGameToWatchlist(new SuccessfulAdditionService());
	}
	
	@AfterEach
	public void tearDown() {
		ViewModel.get().watchlistProperty().getValue().clear();
		ViewModel.get().userProperty().setValue(null);
		ViewModel.get().watchlistPageSelectedGameProperty().setValue(null);
		ViewModel.get().browsePageSelectedGameProperty().setValue(null);
	}
	
	@Test
	public void testNotAllowNullGame() {
		assertThrows(IllegalArgumentException.class, () -> ViewModel.get().removeGameFromWatchlist(new ServerWatchlistRemovalService(), null));
	}
	
	@Test
	public void testSuccessfulRemoval() {
		this.vm.watchlistPageSelectedGameProperty().setValue(this.testGame);
		this.vm.removeSelectedGameFromWatchlist(new SuccessfulRemovalService());
		
		assertEquals(0, this.vm.watchlistProperty().getValue().size());
	}
	
	@Test
	public void testRemoveWithNoUser() {
		this.vm.userProperty().setValue(null);
		this.vm.watchlistPageSelectedGameProperty().setValue(this.testGame);
		this.vm.removeSelectedGameFromWatchlist(new SuccessfulRemovalService());
		
		assertEquals(1, vm.watchlistProperty().getValue().size());
	}

}
