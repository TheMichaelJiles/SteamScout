package com.steamscout.application.test.model.notification.notification_list;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.steamscout.application.model.game_data.Game;
import com.steamscout.application.model.notification.Notification;
import com.steamscout.application.model.notification.NotificationList;

class TestClear {
	
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
	void testClearWithNoItemInList() {
		NotificationList list = new NotificationList();
		list.clear();
		
		assertEquals(0, list.size());
	}
	
	@Test
	void testClearWithOneItemInList() {
		NotificationList list = new NotificationList();
		list.add(this.testNotification);
		list.clear();
		
		assertEquals(0, list.size());
	}
	
	@Test
	void testClearWithMultipleItemsInList() {
		NotificationList list = new NotificationList();
		list.add(this.testNotification);
		list.add(testNotification2);
		list.add(testNotification3);
		list.clear();
		
		assertEquals(0, list.size());
	}

}
