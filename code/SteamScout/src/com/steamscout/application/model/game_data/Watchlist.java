package com.steamscout.application.model.game_data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Stores a collection of games. Each user manages a watchlist.
 * 
 * @author Thomas Whaley
 *
 */
public class Watchlist implements Iterable<Game> {

	private List<Game> games;
	
	/**
	 * Creates a new Watchlist object.
	 */
	public Watchlist() {
		this.games = new ArrayList<Game>();
	}
	
	@Override
	public Iterator<Game> iterator() {
		return this.games.iterator();
	}
	
	/**
	 * Gets the size of this watchlist.
	 * 
	 * @precondition none
	 * @postcondition none
	 * 
	 * @return this watchlist's size.
	 */
	public int size() {
		return this.games.size();
	}
	
	/**
	 * Adds the specified game to this watchlist.
	 * 
	 * @precondition !contains(game)
	 * @postcondition size() == size()@prev + 1
	 * 
	 * @param game the game to add to this watchlist.
	 */
	public void addGame(Game game) {
		if (this.games.contains(game)) {
			throw new IllegalArgumentException();
		}
		
		this.games.add(game);
	}
	
	/**
	 * If this watchlist contains the specified game, then 
	 * it is removed.
	 * 
	 * @precondition none
	 * @postcondition if contains(game), then size() == size()@prev - 1
	 * 
	 * @param game the game to remove from the watchlist.
	 */
	public void removeGame(Game game) {
		this.games.remove(game);
	}
}
