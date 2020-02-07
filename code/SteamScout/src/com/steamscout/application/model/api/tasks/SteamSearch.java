package com.steamscout.application.model.api.tasks;

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
	
	private String term;
	
	/**
	 * Creates a new SteamSearch object that can search
	 * the steam api for the specified term.
	 * 
	 * @precondition term != null
	 * @postcondition none
	 * 
	 * @param term the term to search the api for.
	 */
	public SteamSearch(String term) {
		if (term == null) {
			throw new IllegalArgumentException("term should not be null.");
		}

		this.term = term;
	}
	
	/**
	 * Performs a query on the steam database with the specified search term.
	 * 
	 * @precondition none
	 * @postcondition none
	 * 
	 * @throws IOException if an error occurs polling the api.
	 * @return a collection of game objects that match the term on the steam database.
	 */
	public Collection<Game> query() throws IOException {
		Collection<Game> matchedGames = new ArrayList<Game>();
		Collection<Integer> matchedIds = new ArrayList<Integer>();
			
		AppListAPI steamAppList = new AppListAPI();
		Map<Integer, String> games = steamAppList.makeRequest();
		games.forEach((id, name) -> {
			if (name.toLowerCase().contains(this.term.toLowerCase())) {
				matchedIds.add(id);
			}
		});
			
		for (Integer id : matchedIds) {
			GameSearchAPI steamSearch = new GameSearchAPI(id);
			try {
				Game loadedGame = steamSearch.makeRequest();
				matchedGames.add(loadedGame);	
			} catch (GameNotFoundException e) {
			}
		}
		
		
		return matchedGames;
	}

}
