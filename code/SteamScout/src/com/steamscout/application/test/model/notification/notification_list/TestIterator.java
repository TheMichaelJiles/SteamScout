package com.steamscout.application.test.model.notification.notification_list;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Iterator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.steamscout.application.model.game_data.Game;
import com.steamscout.application.model.notification.Notification;
import com.steamscout.application.model.notification.NotificationList;

public class TestIterator {
	
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
	public void testCanIterate() {
		NotificationList notificationsList = new NotificationList();
		notificationsList.add(this.testNotification);
		notificationsList.add(this.testNotification2);
		notificationsList.add(this.testNotification3);
		
		double total = 0;
		Iterator<Notification> iterator = notificationsList.iterator();
		while (iterator.hasNext()) {
			Notification currentNotification = iterator.next();
			total += currentNotification.getCurrentPrice();
		}
		
		assertEquals(120, total);
	}

}
