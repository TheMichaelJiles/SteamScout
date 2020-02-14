package com.steamscout.application.test.model.game_data.watchlist;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.steamscout.application.model.game_data.Game;
import com.steamscout.application.model.game_data.Watchlist;
import com.steamscout.application.model.notification.NotificationCriteria;

public class TestPutNotificationCriteria {

	private Watchlist watchlist;
	private NotificationCriteria criteria;
	
	@BeforeEach
	public void setUp() {
		this.watchlist = new Watchlist();
		this.watchlist.add(new Game(0, "test0"));
		this.watchlist.add(new Game(1, "test1"));
		this.watchlist.add(new Game(2, "test2"));
		
		this.criteria = new NotificationCriteria();
	}
	
	@AfterEach
	public void tearDown() {
		this.watchlist.clear();
	}
	
	@Test
	public void testNotAllowNullGame() {
		assertThrows(IllegalArgumentException.class, () -> this.watchlist.putNotificationCriteria(null, this.criteria));
	}
	
	@Test
	public void testNotAllowNullCriteria() {
		assertThrows(IllegalArgumentException.class, () -> this.watchlist.putNotificationCriteria(new Game(0, "test0"), null));
	}
	
	@Test
	public void testNotAllowGameNotOnWatchlist() {
		assertThrows(IllegalArgumentException.class, () -> this.watchlist.putNotificationCriteria(new Game(4, "test4"), this.criteria));
	}
	
	@Test
	public void testNotAllowNullGameOnGettingCriteria() {
		assertThrows(IllegalArgumentException.class, () -> this.watchlist.getNotificationCriteria(null));
	}

	@Test
	public void testSuccessfullyAddsInformation() {
		Game game0 = new Game(0, "test0");
		this.watchlist.putNotificationCriteria(game0, this.criteria);
		
		assertEquals(this.criteria, this.watchlist.getNotificationCriteria(game0));
	}
}
