package com.steamscout.application.test.view.viewmodel;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.steamscout.application.connection.interfaces.WatchlistFetchService;
import com.steamscout.application.model.game_data.Game;
import com.steamscout.application.model.game_data.Watchlist;
import com.steamscout.application.model.notification.NotificationCriteria;
import com.steamscout.application.model.user.Credentials;
import com.steamscout.application.model.user.User;
import com.steamscout.application.view.ViewModel;

public class TestLoadWatchlist {

	private class TestWatchlistFetchService implements WatchlistFetchService {
		@Override
		public Watchlist fetchWatchlist(String username) {
			Watchlist list = new Watchlist();
			Game game1 = new Game(1, "test-game-1");
			NotificationCriteria criteria1 = new NotificationCriteria();
			criteria1.setTargetPrice(25.0);
			criteria1.shouldNotifyOnSale(true);
			criteria1.shouldNotifyWhenBelowTargetPrice(true);
			
			Game game2 = new Game(2, "test-game-2");
			NotificationCriteria criteria2 = new NotificationCriteria();
			criteria2.setTargetPrice(25.0);
			criteria2.shouldNotifyOnSale(true);
			criteria2.shouldNotifyWhenBelowTargetPrice(true);
			
			list.add(game1);
			list.add(game2);
			list.putNotificationCriteria(game1, criteria1);
			list.putNotificationCriteria(game2, criteria2);
			return list;
		}
	}
	
	@BeforeEach
	public void setUp() {
		Credentials creds = new Credentials("test-username", "test-password");
		User user = new User(creds);
		ViewModel.get().userProperty().setValue(user);
	}
	
	@AfterEach
	public void tearDown() {
		ViewModel.get().watchlistProperty().clear();
		ViewModel.get().userProperty().setValue(null);
	}
	
	@Test
	public void testNotAllowNullService() {
		assertThrows(IllegalArgumentException.class, () -> ViewModel.get().loadWatchlist(null));
	}
	
	@Test
	public void testLoadsCorrectly() {
		ViewModel.get().loadWatchlist(new TestWatchlistFetchService());
		
		assertAll(() -> assertEquals(2, ViewModel.get().watchlistProperty().getValue().size()),
				() -> assertEquals(1, ViewModel.get().watchlistProperty().getValue().get(0).getAppId()),
				() -> assertEquals(2, ViewModel.get().watchlistProperty().getValue().get(1).getAppId()));
	}

}
