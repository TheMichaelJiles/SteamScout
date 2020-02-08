package com.steamscout.application.test.model.notification;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.steamscout.application.model.game_data.Game;
import com.steamscout.application.model.notification.Notification;

class TestGetReductionAsPercentage {

	@Test
	void testGetReductionasPercentage() {
		Game aGame = new Game(00000, "Pizza Salesman");
		aGame.setCurrentPrice(30.00);
		aGame.setInitialPrice(60.00);
		Notification aNotification = new Notification(aGame);
		
		assertEquals(0.5, aNotification.getReductionAsPercentage());
	}

}
