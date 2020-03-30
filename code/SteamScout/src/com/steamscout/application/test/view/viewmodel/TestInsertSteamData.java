package com.steamscout.application.test.view.viewmodel;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import com.steamscout.application.connection.interfaces.GameFetchService;
import com.steamscout.application.view.ViewModel;

public class TestInsertSteamData {

	
	private class PassingGameFetchService implements GameFetchService {

		@Override
		public Map<String, Integer> fetchGames() {
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
