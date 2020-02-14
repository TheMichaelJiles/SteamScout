package com.steamscout.application.test.model.notification.notification;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.steamscout.application.model.game_data.Game;
import com.steamscout.application.model.notification.Notification;

class TestGetPriceReduction {

	@Test
	void testGetPriceReduction() {
		Game aGame = new Game(00000, "Pizza Salesman");
		aGame.setCurrentPrice(50.00);
		aGame.setInitialPrice(60.00);
		Notification aNotification = new Notification(aGame);
		
		assertEquals(10.00, aNotification.getPriceReduction());
	}

}
