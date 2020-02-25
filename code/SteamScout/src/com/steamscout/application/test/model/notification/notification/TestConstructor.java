package com.steamscout.application.test.model.notification.notification;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalTime;

import org.junit.jupiter.api.Test;

import com.steamscout.application.model.game_data.Game;
import com.steamscout.application.model.notification.Notification;

class TestConstructor {

	@Test
	void testCreatingInValidNotifcation() {
		Game aGame = null;
		assertThrows(IllegalArgumentException.class, () -> new Notification(aGame));
	}
	
	@Test
	void testNotificationPostConditions() {
		Game aGame = new Game(00000, "Pizza Salesman");
		aGame.setCurrentPrice(50.00);
		aGame.setInitialPrice(60.00);
		aGame.setSteamLink("link");
		Notification aNotification = new Notification(aGame);
		
		assertAll(() -> assertEquals(50.00, aNotification.getCurrentPrice(), 0.00001),
				() -> assertEquals(60.00, aNotification.getInitialPrice()),
				() -> assertEquals("Pizza Salesman", aNotification.getTitle()),
				() -> assertEquals("link", aNotification.getSteamLink()),
				() -> assertTrue(aNotification.getTimeNotified().isBefore(LocalTime.now())));
	}

}
