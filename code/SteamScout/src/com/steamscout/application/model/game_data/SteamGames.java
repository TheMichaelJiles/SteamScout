package com.steamscout.application.model.game_data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.steamscout.application.model.autocomplete.trie.TitlePredictor;
import com.steamscout.application.util.IterationSpeed;
import com.steamscout.application.util.ParallelIterable;

/**
 * This class wraps the titles and appids for each and 
 * every one of the products on steam.
 * 
 * @author Thomas Whaley
 *
 */
public class SteamGames {
	
	private TitlePredictor predictor;
	private Map<String, Integer> games;
	
	/**
	 * Creates a new SteamGames object.
	 * 
	 * @precondition none
	 * @postcondition getTitles().size() == 0 && getIds().size() == 0
	 */
	public SteamGames() {
		this.games = Collections.synchronizedMap(new HashMap<String, Integer>());
		this.predictor = new TitlePredictor(TitlePredictor.MINIMUM_SEARCH_CHARACTERS);
	}
	
	/**
	 * Initializes this SteamGames object with the specified game data.
	 * This operation is thread-safe. 
	 * 
	 * @precondition games != null && !games.isEmpty() && getTitles().isEmpty() && getIds().isEmpty()
	 * 				 && !games.keySet().contains(null) && !games.values().contains(null)
	 * @postcondition !getTitles().isEmpty() && !getIds().isEmpty()
	 * 
	 * @param games the games to initialize this SteamGames object with.
	 */
	public void initializeGames(Map<String, Integer> games) {
		if (games == null) {
			throw new IllegalArgumentException("games should not be null.");
		}
		if (games.isEmpty()) {
			throw new IllegalArgumentException("games must have at least one key/value pair.");
		}
		if (!this.games.isEmpty()) {
			throw new IllegalArgumentException("SteamGames has already been initialized");
		}
		if (games.keySet().contains(null)) {
			throw new IllegalArgumentException("games can not have null keys.");
		}
		if (games.values().contains(null)) {
			throw new IllegalArgumentException("games should not have null values.");
		}
		
		this.games.putAll(games);
		this.predictor.populate(this.games.keySet());
	}
	
	/**
	 * Makes a prediction using this object's trie.
	 * 
	 * @precondition none
	 * @postcondition none
	 * 
	 * @param term the search term.
	 * @return the list of matching game titles.
	 */
	public List<String> makePrediction(String term) {
		return this.predictor.predict(term);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		this.games.forEach((name, id) -> {
			sb.append(name + " - " + id + System.lineSeparator());
		});
		return sb.toString();
	}
	
	/**
	 * Clears all the games in this collection.
	 * 
	 * @precondition none
	 * @postcondition getTitles.isEmpty() && getIds().isEmpty()
	 */
	public void clear() {
		this.games.clear();
		this.predictor.clear();
	}
	
	/**
	 * Gets all of the ids that are mapped with a name that
	 * contains the given term. The containment check is not 
	 * case-sensitive.
	 * 
	 * @precondition term != null
	 * @postcondition getIds().containsAll(getMatchingIds(term))
	 * 
	 * @param term the term to find matching ids for.
	 * @return a grouping of ids that are mapped with a name that contains the given term.
	 * @throws InterruptedException 
	 */
	public List<Integer> getMatchingIds(String term) throws InterruptedException {
		List<String> matchingTitles = this.getMatchingTitles(term);
		
		return matchingTitles.stream().map(match -> this.games.get(match)).collect(Collectors.toList());
	}
	
	/**
	 * Gets a collection of game objects with titles that contain
	 * the given term.
	 * 
	 * @precondition term != null
	 * @postcondition getTitles().containsAll(getMatchingGames(term).stream().map(game -> game.getTitle()).collect(Collectors.toList()))
	 * 
	 * @param term the term to compare against this SteamGames object's titles.
	 * @return a collection of game objects with titles that contain the given term.
	 * @throws InterruptedException if the search threads are interrupted.
	 */
	public Collection<Game> getMatchingGames(String term) throws InterruptedException {
		List<String> matchingTitles = this.getMatchingTitles(term);
		
		var games = matchingTitles.stream().map(match -> new Game(this.games.get(match), match)).collect(Collectors.toList());
		Collections.sort(games);
		return games;
	}
	
	/**
	 * Gets a list of steam titles that contain the given search term.
	 * 
	 * @precondition term != null
	 * @postcondition getTitles().containsAll(getMatchingTitles(term))
	 * 
	 * @param term the term to compare against the steam titles.
	 * @return a list of titles that are contained in the steam games.
	 * @throws InterruptedException if the search threads are interrupted.
	 */
	public List<String> getMatchingTitles(String term) throws InterruptedException {
		if (term == null) {
			throw new IllegalArgumentException("term should not be null.");
		}
		
		List<String> matches = Collections.synchronizedList(new ArrayList<String>());
		ParallelIterable<String> titles = new ParallelIterable<String>(this.games.keySet(), IterationSpeed.HIGH);
		titles.forEach(title -> {
			if (title.toLowerCase().contains(term.toLowerCase())) {
				matches.add(title);
			}
		});
		
		return matches;
	}
	
	/**
	 * Gets the titles of all SteamGames.
	 * 
	 * @precondition none
	 * @postcondition none
	 * 
	 * @return the titles of all SteamGames.
	 */
	public Collection<String> getTitles() {
		return Collections.unmodifiableCollection(this.games.keySet());
	}
	
	/**
	 * Gets the ids of all steam games.
	 * 
	 * @precondition none
	 * @postcondition none
	 * 
	 * @return the ids of all steam games.
	 */
	public Collection<Integer> getIds() {
		return Collections.unmodifiableCollection(this.games.values());
	}

}
