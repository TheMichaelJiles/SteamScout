package com.steamscout.application.search;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.function.Consumer;

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
public class SteamSearch implements Runnable {
	
	private String term;
	
	private Consumer<Collection<Game>> response;
	
	/**
	 * Creates a new SteamSearch object that can search
	 * the steam api for the specified term.
	 * 
	 * @precondition term != null
	 * @postcondition none
	 * 
	 * @param term the term to search the api for.
	 */
	public SteamSearch(String term, Consumer<Collection<Game>> response) {
		if (term == null) {
			throw new IllegalArgumentException("term should not be null.");
		}
		if (response == null) {
			throw new IllegalArgumentException("response should not be null.");
		}
		
		this.term = term;
		this.response = response;
	}
	
	@Override
	public void run() {
		try {
			this.response.accept(this.query());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Performs a query on the steam database with the specified search term.
	 * 
	 * @precondition none
	 * @postcondition none
	 * 
	 * @param term the term to query against the steam database.
	 * @return a collection of game objects that match the term on the steam database.
	 */
	private Collection<Game> query() throws IOException {
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
				System.err.println(e.getMessage());
			}
		}
		
		
		return matchedGames;
	}

}
