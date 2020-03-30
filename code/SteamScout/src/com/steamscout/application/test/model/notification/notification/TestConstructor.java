package com.steamscout.application.test.model.notification.notification;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

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
		Notification aNotification = new Notification(aGame);
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		assertAll(() -> assertEquals(50.00, aNotification.getCurrentPrice(), 0.00001),
				() -> assertEquals(60.00, aNotification.getInitialPrice()),
				() -> assertEquals("Pizza Salesman", aNotification.getTitle()),
				() -> assertEquals("https://store.steampowered.com/app/0", aNotification.getSteamLink()),
				() -> assertTrue(aNotification.getTimeNotified().isBefore(LocalTime.now())));
	}

}
