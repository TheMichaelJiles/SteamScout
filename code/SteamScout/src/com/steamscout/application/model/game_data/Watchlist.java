package com.steamscout.application.model.game_data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.steamscout.application.model.autocomplete.trie.Trie;
import com.steamscout.application.model.notification.NotificationCriteria;

/**
 * Stores a collection of games. Each user manages a watchlist.
 * 
 * @author Thomas Whaley
 *
 */
public class Watchlist implements Collection<Game> {

	private List<Game> games;
	private Map<Game, NotificationCriteria> criteria;
	private Trie trie;
	
	/**
	 * Creates a new Watchlist object.
	 * 
	 * @precondition none
	 * @postcondition size() == 0
	 */
	public Watchlist() {
		this.games = new ArrayList<Game>();
		this.criteria = new HashMap<Game, NotificationCriteria>();
		this.trie = new Trie(3);
	}
	
	/**
	 * Sets the notification criteria for the specified game.
	 * 
	 * @precondition game != null &&
	 * 				 criteria != null &&
	 * 				 contains(game)
	 * @postcondition getNotificationCriteria(game).equals(criteria)
	 * 
	 * @param game the game for which the notification criteria is for.
	 * @param criteria the criteria for the game.
	 */
	public void putNotificationCriteria(Game game, NotificationCriteria criteria) {
		if (game == null) {
			throw new IllegalArgumentException("game should not be null for notification criteria.");
		}
		if (criteria == null) {
			throw new IllegalArgumentException("criteria should not be null.");
		}
		if (!this.contains(game)) {
			throw new IllegalArgumentException("game must be on this watchlist.");
		}
		
		this.criteria.put(game, criteria);
	}
	
	/**
	 * Makes a prediction based off the the given term and the games in this
	 * watchlist.
	 * 
	 * @precondition none
	 * @postcondition none
	 * 
	 * @param term the term to make the prediction from.
	 * @return the list of all matches.
	 */
	public List<String> makePrediction(String term) {
		return this.trie.predict(term);
	}
	
	/**
	 * Gets the notification criteria for the specified game.
	 * 
	 * @precondition game != null
	 * @postcondition none
	 * 
	 * @param game the game to get the notification criteria for.
	 * @return the NotificationCriteria for the specified criteria or null if the game has no criteria stored.
	 */
	public NotificationCriteria getNotificationCriteria(Game game) {
		if (game == null) {
			throw new IllegalArgumentException("game should not be null when getting criteria.");
		}
		
		return this.criteria.get(game);
	}
	
	/**
	 * Gets all matching games in this watchlist that contain the specified
	 * search term in the game's title. This match is not case-sensitive.
	 * 
	 * @precondition term != null
	 * @postcondition none
	 * 
	 * @param term the search term.
	 * @return an unmodifiable collection of games whose title contains the given search term.
	 */
	public Collection<Game> getMatchingGames(String term) {
		if (term == null) {
			throw new IllegalArgumentException("term should not be null.");
		}
		
		Collection<Game> matchingGames = new ArrayList<Game>();
		for (Game currentGame : this.games) {
			if (currentGame.getTitle().toLowerCase().contains(term.toLowerCase())) {
				matchingGames.add(currentGame);
			}
		}
		
		return Collections.unmodifiableCollection(matchingGames);
	}
	
	@Override
	public void clear() {
		this.games.clear();
		this.criteria.clear();
		this.trie.clear();
	}

	@Override
	public Iterator<Game> iterator() {
		return this.games.iterator();
	}

	@Override
	public int size() {
		return this.games.size();
	}
	
	@Override
	public boolean add(Game game) {
		if (game == null) {
			throw new IllegalArgumentException("game should not be null.");
		}
		
		if (!this.contains(game)) {
			this.trie.populateWord(game.getTitle());
			return this.games.add(game);
		}
		
		return false;
	}

	@Override
	public boolean addAll(Collection<? extends Game> games) {
		if (games == null) {
			throw new IllegalArgumentException("games should not be null.");
		}
		if (games.contains(null)) {
			throw new IllegalArgumentException("games should not contain null.");
		}
		
		for (Game currentGame : games) {
			this.add(currentGame);
		}
		
		return true;
	}

	@Override
	public boolean contains(Object game) {
		return this.games.contains(game);
	}

	@Override
	public boolean containsAll(Collection<?> games) {
		return this.games.containsAll(games);
	}

	@Override
	public boolean isEmpty() {
		return this.games.isEmpty();
	}

	@Override
	public boolean remove(Object game) {
		this.criteria.remove(game);
		if (game instanceof Game) {
			Game target = (Game) game;
			this.trie.remove(target.getTitle());
		}
		return this.games.remove(game);
	}

	@Override
	public boolean removeAll(Collection<?> games) {
		return this.games.removeAll(games);
	}

	@Override
	public boolean retainAll(Collection<?> games) {
		return this.games.retainAll(games);
	}

	@Override
	public Object[] toArray() {
		return this.games.toArray();
	}

	@Override
	public <T> T[] toArray(T[] arg0) {
		return this.games.toArray(arg0);
	}
}
