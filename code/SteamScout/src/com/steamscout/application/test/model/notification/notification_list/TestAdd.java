package com.steamscout.application.test.model.notification.notification_list;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.steamscout.application.model.game_data.Game;
import com.steamscout.application.model.notification.Notification;
import com.steamscout.application.model.notification.NotificationList;

public class TestAdd {
	
	private Notification aNotification;
	
	@BeforeEach
	public void createTestNotifications() {
		Game aGame = new Game(00000, "Pizza Salesman");
		aGame.setCurrentPrice(50.00);
		aGame.setInitialPrice(60.00);
		aGame.setSteamLink("link");
		this.aNotification = new Notification(aGame);
	}
	
	@Test
	void testNullNotificationShouldFail() {
		NotificationList list = new NotificationList();
		assertThrows(IllegalArgumentException.class, () -> list.add(null));
	}
	
	@Test
	void testAddValidNotificationPasses() {
		NotificationList list = new NotificationList();
		list.add(this.aNotification);
		
		assertEquals(false, list.isEmpty());
	}
	
	@Test
	void testAddPreventsSameNotificationFromAdding() {
		NotificationList list = new NotificationList();
		list.add(this.aNotification);
		list.add(this.aNotification);
		
		assertEquals(false, list.isEmpty());
	}
}
