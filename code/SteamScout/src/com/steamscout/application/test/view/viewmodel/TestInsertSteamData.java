package com.steamscout.application.test.view.viewmodel;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import com.steamscout.application.connection.ServerGameFetchService;
import com.steamscout.application.connection.interfaces.GameFetchService;
import com.steamscout.application.connection.interfaces.NotificationService;
import com.steamscout.application.model.game_data.Game;
import com.steamscout.application.model.notification.Notification;
import com.steamscout.application.model.notification.NotificationList;
import com.steamscout.application.model.user.Credentials;
import com.steamscout.application.view.ViewModel;

public class TestInsertSteamData {

	
	private class PassingGameFetchService implements GameFetchService {

		@Override
		public Map<String, Integer> FetchGames() {
			Map<String, Integer> games = new HashMap<String, Integer>();
			games.put("TestName", 00000);
			return games;
		}
	}
	
	@AfterEach
	public void tearDown() {
		ViewModel.get().getSteamGames().clear();
	}
	
	@Test
	public void testNotAllowNullData() {
		assertThrows(IllegalArgumentException.class, () -> ViewModel.get().insertSteamData(null));
	} 

	@Test
	public void testSuccessfulInsertion() {
		PassingGameFetchService service = new PassingGameFetchService();
		ViewModel.get().insertSteamData(service);
		
		assertAll(() -> assertEquals(false, ViewModel.get().getSteamGames().getTitles().isEmpty()),
				() -> assertEquals(false, ViewModel.get().getSteamGames().getIds().isEmpty()));
	}
}
