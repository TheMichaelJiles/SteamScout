package com.steamscout.application.test.view.viewmodel;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.steamscout.application.connection.interfaces.GameFetchService;
import com.steamscout.application.model.game_data.Game;
import com.steamscout.application.model.game_data.SteamGames;
import com.steamscout.application.model.user.Credentials;
import com.steamscout.application.model.user.User;
import com.steamscout.application.view.ViewModel;

public class TestPredictions {

	private class PassingGameFetchService implements GameFetchService {
		@Override
		public Map<String, Integer> fetchGames() {
			Map<String, Integer> games = new HashMap<String, Integer>();
			games.put("Orianna's Revenge", 134);
			games.put("Chicken Little", 142);
			return games;
		}
	}
	
	@BeforeEach
	public void setUp() {
		ViewModel.get().userProperty().setValue(new User(new Credentials("test-user", "test-pass")));
		ViewModel.get().addGameToWatchlist(new Game(134, "Orianna's Revenge"));
		ViewModel.get().insertSteamData(new PassingGameFetchService());
	}
	
	@AfterEach
	public void tearDown() {
		ViewModel.get().userProperty().setValue(null);
		ViewModel.get().watchlistProperty().clear();
		ViewModel.get().setSteamGames(new SteamGames());
	}
	
	@Test
	public void testBrowsePagePredictions() {
		List<String> results = ViewModel.get().makeBrowsePagePrediction("chick");
		assertEquals(true, results.contains("Chicken Little"));
	}

	@Test
	public void testWatchlistPagePredictions() {
		List<String> results = ViewModel.get().makeWatchlistPagePrediction("oria");
		assertEquals(true, results.contains("Orianna's Revenge"));
	}
	
}
