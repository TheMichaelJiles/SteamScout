package com.steamscout.application.test.view.viewmodel;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.steamscout.application.view.ViewModel;
import com.steamscout.application.model.game_data.Game;
import com.steamscout.application.model.notification.NotificationCriteria;
import com.steamscout.application.model.user.Credentials;
import com.steamscout.application.model.user.User;

public class TestContainsNotificationCriteria {

	@BeforeEach
	public void setUp() {
		ViewModel.get().userProperty().setValue(new User(new Credentials("user", "pass")));
		
		Game game1 = new Game(1, "game1");
		Game game2 = new Game(2, "game2");
		
		ViewModel.get().addGameToWatchlist(game1);
		ViewModel.get().addGameToWatchlist(game2);
		
		ViewModel.get().userProperty().getValue().getWatchlist().putNotificationCriteria(game1, new NotificationCriteria());
		
		NotificationCriteria game2criteria = new NotificationCriteria();
		game2criteria.setTargetPrice(5.99);
		ViewModel.get().userProperty().getValue().getWatchlist().putNotificationCriteria(game2, game2criteria);
	}
	
	@AfterEach
	public void tearDown() {
		ViewModel.get().userProperty().setValue(null);
		ViewModel.get().watchlistProperty().clear();
	}
	
	@Test
	public void testNotAllowNullGame() {
		assertThrows(IllegalArgumentException.class, () -> ViewModel.get().containsNotificationCriteria(null));
	}
	
	@Test
	public void testIsFalse() {
		Game game1 = new Game(1, "game1");
		boolean result = ViewModel.get().containsNotificationCriteria(game1);
		
		assertEquals(false, result);
	}
	
	@Test
	public void testIsFalseNoMapping() {
		Game game1 = new Game(10, "game1");
		boolean result = ViewModel.get().containsNotificationCriteria(game1);
		
		assertEquals(false, result);
	}
	
	@Test
	public void testIsTrue() {
		Game game2 = new Game(2, "game2");
		boolean result = ViewModel.get().containsNotificationCriteria(game2);
		
		assertEquals(true, result);
	}

}
