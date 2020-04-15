package com.steamscout.application.test.model.game_data.watchlist;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.steamscout.application.model.game_data.Game;
import com.steamscout.application.model.game_data.Watchlist;

public class TestMakePrediction {

	@Test
	public void testMakesValidPredictionBasedOnWatchlistGames() {
		Game game0 = new Game(0, "abra");
		Game game1 = new Game(1, "abracadabra");
		Game game2 = new Game(2, "cathleen's revenge");
		Game game3 = new Game(3, "catheter");
		Watchlist list = new Watchlist();
		
		list.add(game0);
		list.add(game1);
		list.add(game2);
		list.add(game3);
		
		List<String> aresult = list.makePrediction("abr");
		List<String> cresult = list.makePrediction("cat");
		
		assertAll(() -> assertEquals(true, aresult.contains("abra")),
				() -> assertEquals(true, aresult.contains("abracadabra")),
				() -> assertEquals(true, cresult.contains("catheter")),
				() -> assertEquals(true, cresult.contains("cathleen's revenge")));
	}

}
