package com.steamscout.application.test.model.api.tasks.notificationcheck;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.jupiter.api.Test;

import com.steamscout.application.model.api.tasks.NotificationCheck;
import com.steamscout.application.model.game_data.Game;

public class TestConstructor {

	@Test
	public void testNotAllowNullCollection() {
		assertThrows(IllegalArgumentException.class, () -> new NotificationCheck(null));
	}

	@Test
	public void testNotAllowCollectionWithNGamelements() {
		Collection<Game> games = new ArrayList<Game>();
		games.add(null);
		assertThrows(IllegalArgumentException.class, () -> new NotificationCheck(games));
	}
}