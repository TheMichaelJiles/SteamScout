package com.steamscout.application.test.model.notification.notification_list;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.steamscout.application.model.game_data.Game;
import com.steamscout.application.model.notification.Notification;
import com.steamscout.application.model.notification.NotificationList;

class TestContainsAll {
	
	private Notification testNotification;
	private Notification testNotification2;
	private Notification testNotification3;
	
	@BeforeEach
	public void createTestNotifications() {
		Game testGame = new Game(00000, "Pizza Salesman");
		testGame.setCurrentPrice(20.00);
		testGame.setInitialPrice(40.00);
		this.testNotification = new Notification(testGame);
		Game testGame2 = new Game(00001, "Pizza Salesman 2");
		testGame2.setCurrentPrice(50.00);
		testGame2.setInitialPrice(60.00);
		this.testNotification2 = new Notification(testGame2);
		Game testGame3 = new Game(00002, "Pizza Salesman 3");
		testGame3.setCurrentPrice(50.00);
		testGame3.setInitialPrice(60.00);
		this.testNotification3 = new Notification(testGame3);
	}

	@Test
	void testCotnainsAllFailsWithMissingElement() {
		NotificationList notificationsList = new NotificationList();
		notificationsList.add(this.testNotification);
		notificationsList.add(this.testNotification3);
		
		List<Notification> list = new ArrayList<Notification>();
		list.add(this.testNotification);
		list.add(this.testNotification2);
		list.add(this.testNotification3);
		
		assertFalse(notificationsList.containsAll(list));
	}
	
	@Test
	void testCotnainsAllWithAllElements() {
		NotificationList notificationsList = new NotificationList();
		notificationsList.add(this.testNotification);
		notificationsList.add(this.testNotification2);
		notificationsList.add(this.testNotification3);
		
		List<Notification> list = new ArrayList<Notification>();
		list.add(this.testNotification);
		list.add(this.testNotification2);
		list.add(this.testNotification3);
		
		assertTrue(notificationsList.containsAll(list));
	}

}
