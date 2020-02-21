package com.steamscout.application.test.view.viewmodel;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.steamscout.application.view.ViewModel;
import com.steamscout.application.model.game_data.Game;
import com.steamscout.application.model.user.Credentials;
import com.steamscout.application.model.user.User;

public class TestPerformWatchlistSearch {

	@BeforeEach
	public void setUp() {
		Collection<Game> games = new ArrayList<Game>();
		games.add(new Game(1, "te"));
		games.add(new Game(2, "tes"));
		games.add(new Game(3, "test"));
		ViewModel.get().userProperty().setValue(new User(new Credentials("test", "123"), games));
	}
	
	@AfterEach
	public void tearDown() {
		ViewModel.get().userProperty().setValue(null);
	}
	
	@Test
	public void testCorrectlySetsWatchlistProperty() {
		ViewModel.get().performWatchlistSearch("te");
		
		assertEquals(3, ViewModel.get().watchlistProperty().size());
	}

}
