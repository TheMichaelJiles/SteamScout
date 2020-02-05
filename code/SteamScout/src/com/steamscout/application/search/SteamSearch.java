package com.steamscout.application.search;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.steamscout.application.model.api.AppListAPI;
import com.steamscout.application.model.api.GameSearchAPI;
import com.steamscout.application.model.api.exceptions.GameNotFoundException;
import com.steamscout.application.model.game_data.Game;

/**
 * Contains static methods that search steam for useful
 * data.
 * 
 * @author Thomas Whaley
 *
 */
public class SteamSearch {
	
	/**
	 * Performs a query on the steam database with the specified search term.
	 * 
	 * @precondition none
	 * @postcondition none
	 * 
	 * @param term the term to query against the steam database.
	 * @return a collection of game objects that match the term on the steam database.
	 */
	public static Collection<Game> query(String term) {
		Collection<Game> matchedGames = new ArrayList<Game>();
		try {
			Collection<Integer> matchedIds = new ArrayList<Integer>();
			
			AppListAPI steamAppList = new AppListAPI();
			Map<Integer, String> games = steamAppList.makeRequest();
			games.forEach((id, name) -> {
				if (name.toLowerCase().contains(term.toLowerCase())) {
					matchedIds.add(id);
				}
			});
			
			for (Integer id : matchedIds) {
				GameSearchAPI steamSearch = new GameSearchAPI(id);
				try {
					Game loadedGame = steamSearch.makeRequest();
					matchedGames.add(loadedGame);	
				} catch (GameNotFoundException e) {
					System.err.println(e.getMessage());
				}
			}
		} catch (IOException e) {
			System.err.println("query ended earlier than expected due to IOException.");
			e.printStackTrace();
		}
		
		return matchedGames;
	}
}
