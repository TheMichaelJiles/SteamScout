package com.steamscout.application.model.game_data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
	
	/**
	 * Creates a new Watchlist object.
	 * 
	 * @precondition none
	 * @postcondition size() == 0
	 */
	public Watchlist() {
		this.games = new ArrayList<Game>();
		this.criteria = new HashMap<Game, NotificationCriteria>();
	}
	
	/**
	 * Sets the notification criteria for the specified game.
	 * 
	 * @precondition game != null &&
	 * 				 criteria != null &&
	 * 				 contains(game)
	 * @param game
	 * @param criteria
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
	
	@Override
	public void clear() {
		this.games.clear();
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
