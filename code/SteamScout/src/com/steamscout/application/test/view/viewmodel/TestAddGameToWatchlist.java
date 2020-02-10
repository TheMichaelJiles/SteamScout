package com.steamscout.application.test.view.viewmodel;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.steamscout.application.model.game_data.Game;
import com.steamscout.application.model.user.Credentials;
import com.steamscout.application.model.user.User;
import com.steamscout.application.view.ViewModel;

public class TestAddGameToWatchlist {

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
		vm.addSelectedGameToWatchlist();
		
		assertEquals(1, vm.watchlistProperty().getValue().size());
	}
	
	@Test
	public void testAdditionWithNoUser() {
		ViewModel vm = ViewModel.get();
		vm.browsePageSelectedGameProperty().setValue(new Game(1, "test"));
		vm.addSelectedGameToWatchlist();
		
		assertEquals(0, vm.watchlistProperty().getValue().size());
	}
}
