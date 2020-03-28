package com.steamscout.application.test.view.viewmodel;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.steamscout.application.model.game_data.Game;
import com.steamscout.application.model.user.Credentials;
import com.steamscout.application.model.user.User;
import com.steamscout.application.view.ViewModel;

class TestRemoveGameFromWatchlist {
	
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
		//this.vm.addSelectedGameToWatchlist();
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
		assertThrows(IllegalArgumentException.class, () -> ViewModel.get().removeGameFromWatchlist(null));
	}
	
	@Test
	public void testSuccessfulAddition() {
		this.vm.watchlistPageSelectedGameProperty().setValue(this.testGame);
		this.vm.removeSelectedGameFromWatchlist();
		
		assertEquals(0, this.vm.watchlistProperty().getValue().size());
	}
	
	@Test
	public void testRemoveWithNoUser() {
		this.vm.userProperty().setValue(null);
		this.vm.watchlistPageSelectedGameProperty().setValue(this.testGame);
		this.vm.removeSelectedGameFromWatchlist();
		
		assertEquals(1, vm.watchlistProperty().getValue().size());
	}

}
