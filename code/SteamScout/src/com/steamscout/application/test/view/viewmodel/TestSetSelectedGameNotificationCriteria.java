package com.steamscout.application.test.view.viewmodel;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.steamscout.application.model.game_data.Game;
import com.steamscout.application.model.notification.NotificationCriteria;
import com.steamscout.application.model.user.Credentials;
import com.steamscout.application.model.user.User;
import com.steamscout.application.view.ViewModel;

public class TestSetSelectedGameNotificationCriteria {

	@BeforeEach
	public void setUp() {
		User newUser = new User(new Credentials("TestUser", "1234"));
		ViewModel.get().userProperty().setValue(newUser);
		ViewModel.get().addGameToWatchlist(new Game(0, "test0"));
		ViewModel.get().addGameToWatchlist(new Game(1, "test1"));
		ViewModel.get().watchlistPageSelectedGameProperty().setValue(new Game(0, "test0"));
	}
	
	@AfterEach
	public void tearDown() {
		ViewModel.get().userProperty().setValue(null);
		ViewModel.get().watchlistProperty().getValue().clear();
		ViewModel.get().watchlistPageSelectedGameProperty().setValue(null);
	}
	
	@Test
	public void testSuccessfullyAddsCriteria() {
		ViewModel.get().setSelectedGameNotificationCriteria(true, true, 10.50);
		
		NotificationCriteria criteria = ViewModel.get().userProperty().getValue().getWatchlist().getNotificationCriteria(new Game(0, "test0"));
		assertAll(() -> assertEquals(true, criteria.shouldNotifyOnSale()),
				() -> assertEquals(true, criteria.shouldNotifyWhenBelowTargetPrice()),
				() -> assertEquals(10.50, criteria.getTargetPrice(), 0.0000001));
	}

}