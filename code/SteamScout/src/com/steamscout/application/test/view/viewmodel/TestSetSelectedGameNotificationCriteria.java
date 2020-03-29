package com.steamscout.application.test.view.viewmodel;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.steamscout.application.connection.interfaces.WatchlistModificationService;
import com.steamscout.application.model.game_data.Game;
import com.steamscout.application.model.game_data.Watchlist;
import com.steamscout.application.model.notification.NotificationCriteria;
import com.steamscout.application.model.user.Credentials;
import com.steamscout.application.model.user.User;
import com.steamscout.application.view.ViewModel;

public class TestSetSelectedGameNotificationCriteria {
	
	private class SuccessfulModificationService implements WatchlistModificationService {

		@Override
		public Watchlist modifyGameOnWatchlist(User currentUser, Game gameToModify,
				NotificationCriteria notificationCriteria) {
			Watchlist watchlist = currentUser.getWatchlist();
			watchlist.putNotificationCriteria(gameToModify, notificationCriteria);
			return watchlist;
		}
	}

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
		ViewModel.get().browsePageSelectedGameProperty().setValue(null);
	}
	
	@Test
	public void testSuccessfullyAddsWatchlistPageSelectedGameCriteria() {
		ViewModel.get().setSelectedGameNotificationCriteria(new SuccessfulModificationService(), true, true, 10.50);
		
		NotificationCriteria criteria = ViewModel.get().userProperty().getValue().getWatchlist().getNotificationCriteria(new Game(0, "test0"));
		assertAll(() -> assertEquals(true, criteria.shouldNotifyOnSale()),
				() -> assertEquals(true, criteria.shouldNotifyWhenBelowTargetPrice()),
				() -> assertEquals(10.50, criteria.getTargetPrice(), 0.0000001));
	}

	@Test
	public void testSuccessfullyAddsBrowsePageSelectedGameCriteria() {
		ViewModel.get().watchlistPageSelectedGameProperty().setValue(null);
		ViewModel.get().browsePageSelectedGameProperty().setValue(new Game(1, "test1"));
		ViewModel.get().setSelectedGameNotificationCriteria(new SuccessfulModificationService(), true, true, 10.50);
		
		NotificationCriteria criteria = ViewModel.get().userProperty().getValue().getWatchlist().getNotificationCriteria(new Game(1, "test1"));
		assertAll(() -> assertEquals(true, criteria.shouldNotifyOnSale()),
				() -> assertEquals(true, criteria.shouldNotifyWhenBelowTargetPrice()),
				() -> assertEquals(10.50, criteria.getTargetPrice(), 0.0000001));
	}
}
