package com.steamscout.application.test.model.notification.notification_list;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.steamscout.application.model.game_data.Game;
import com.steamscout.application.model.notification.Notification;
import com.steamscout.application.model.notification.NotificationList;

public class TestAddAll {
	
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
	void testNotAllowNullCollection() {
		NotificationList list = new NotificationList();
		assertThrows(IllegalArgumentException.class, () -> list.addAll(null));
	}
	
	@Test
	void testAddAllWithValidUniqueNotifications() {
		NotificationList notificationlist = new NotificationList();
		
		List<Notification> list = new ArrayList<Notification>();
		list.add(this.testNotification);
		list.add(this.testNotification2);
		list.add(this.testNotification3);
		notificationlist.addAll(list);
		
		assertAll(() -> assertEquals(true, notificationlist.contains(this.testNotification)),
				() -> assertEquals(true, notificationlist.contains(this.testNotification2)),
				() -> assertEquals(true, notificationlist.contains(this.testNotification3)),
				() -> assertEquals(3, notificationlist.size()));
	}
	
	@Test
	void testAddAllWithValidButSingleSameNotification() {
		NotificationList notificationlist = new NotificationList();
		
		Collection<Notification> list = new ArrayList<Notification>();
		list.add(this.testNotification);
		list.add(this.testNotification);
		list.add(this.testNotification3);
		
		notificationlist.addAll(list);
		
		assertAll(() -> assertEquals(true, notificationlist.contains(this.testNotification)),
				() -> assertEquals(false, notificationlist.contains(this.testNotification2)),
				() -> assertEquals(true, notificationlist.contains(this.testNotification3)),
				() -> assertEquals(2, notificationlist.size()));
	}
	
	@Test
	void testAddAllWithSingleNullNotifications() {
		NotificationList notificationlist = new NotificationList();
		
		Collection<Notification> list = new ArrayList<Notification>();
		list.add(this.testNotification);
		list.add(this.testNotification2);
		list.add(null);
		
		assertThrows(IllegalArgumentException.class, () -> notificationlist.addAll(list));
	}
	

}
