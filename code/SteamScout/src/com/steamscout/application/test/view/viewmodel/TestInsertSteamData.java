package com.steamscout.application.test.view.viewmodel;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import com.steamscout.application.connection.ServerGameFetchService;
import com.steamscout.application.view.ViewModel;

public class TestInsertSteamData {

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
		ServerGameFetchService data = new ServerGameFetchService();
		ViewModel.get().insertSteamData(data);
		
		assertAll(() -> assertEquals(false, ViewModel.get().getSteamGames().getTitles().isEmpty()),
				() -> assertEquals(false, ViewModel.get().getSteamGames().getIds().isEmpty()));
	}
}
