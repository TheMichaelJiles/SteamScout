package com.steamscout.application.model.api.tasks;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.steamscout.application.model.api.GameSearchAPI;
import com.steamscout.application.model.api.exceptions.GameNotFoundException;
import com.steamscout.application.model.game_data.Game;
import com.steamscout.application.model.game_data.SteamGames;
import com.steamscout.application.util.ParallelIterable;

/**
 * Contains the ability to search steam for useful
 * data.
 * 
 * @author Thomas Whaley
 *
 */
public class SteamSearch {
	
	private SteamGames games;
	
	/**
	 * Creates a new SteamSearch object that can search
	 * the steam api for game objects with titles that match
	 * a given string.
	 * 
	 * @precondition games != null
	 * @postcondition none
	 * 
	 * @param games titles of all games on steam.
	 */
	public SteamSearch(SteamGames games) {
		if (games == null) {
			throw new IllegalArgumentException("games should not be null.");
		}

		this.games = games;
	}
	
	/**
	 * Performs a query on the steam database with the specified search term.
	 * 
	 * @precondition term != null
	 * @postcondition none
	 * 
	 * @param term the search term to query against steam games.
	 * 
	 * @throws IOException if an error occurs polling the api.
	 * @return a collection of game objects that match the term on the steam database.
	 * @throws InterruptedException 
	 */
	public Collection<Game> query(String term) throws InterruptedException {
		if (term == null) {
			throw new IllegalArgumentException("term should not be null.");
		}
		
		List<Integer> matchedIds = this.games.getMatchingIds(term);	
		Collection<Game> matchedGames = Collections.synchronizedCollection(new ArrayList<Game>());
		ParallelIterable<Integer> ids = new ParallelIterable<Integer>(matchedIds);
		ids.forEach(id -> {
			GameSearchAPI steamSearch = new GameSearchAPI(id);
			try {
				Game loadedGame = steamSearch.makeRequest();
				matchedGames.add(loadedGame);	
			} catch (GameNotFoundException | IOException e) {
			}
		});
		
		
		return matchedGames;
	}

}
