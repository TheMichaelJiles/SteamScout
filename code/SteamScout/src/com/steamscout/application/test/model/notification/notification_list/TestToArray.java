package com.steamscout.application.test.model.notification.notification_list;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.steamscout.application.model.game_data.Game;
import com.steamscout.application.model.notification.Notification;
import com.steamscout.application.model.notification.NotificationList;

class TestToArray {
	
	private Notification testNotification;
	private Notification testNotification2;
	private Notification testNotification3;
	
	@BeforeEach
	public void createTestNotifications() {
		Game testGame = new Game(00000, "Pizza Salesman");
		testGame.setCurrentPrice(20.00);
		testGame.setInitialPrice(40.00);
		testGame.setSteamLink("link");
		this.testNotification = new Notification(testGame);
		Game testGame2 = new Game(00001, "Pizza Salesman 2");
		testGame2.setCurrentPrice(50.00);
		testGame2.setInitialPrice(60.00);
		testGame2.setSteamLink("link");
		this.testNotification2 = new Notification(testGame2);
		Game testGame3 = new Game(00002, "Pizza Salesman 3");
		testGame3.setCurrentPrice(50.00);
		testGame3.setInitialPrice(60.00);
		testGame3.setSteamLink("link");
		this.testNotification3 = new Notification(testGame3);
	}

	@Test
	void testBasicToArray() {
		NotificationList notificationsList = new NotificationList();
		notificationsList.add(this.testNotification);
		notificationsList.add(this.testNotification2);
		notificationsList.add(this.testNotification3);
		
		assertEquals(3, notificationsList.toArray().length);
	}
	
	@Test
	void testToArrayWithSmallerArray() {
		NotificationList notificationsList = new NotificationList();
		notificationsList.add(this.testNotification);
		notificationsList.add(this.testNotification2);
		notificationsList.add(this.testNotification3);
		
		Notification[] testArray = new Notification[2];
		testArray = notificationsList.toArray(testArray);
		
		assertEquals(this.testNotification2, testArray[1]);
	}

}
