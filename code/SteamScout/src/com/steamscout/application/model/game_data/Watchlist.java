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
	 * 
	 * @precondition none
	 * @postcondition size() == 0
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
	 * Gets whether or not this watchlist contains the specified
	 * game. 
	 * 
	 * @precondition none
	 * @postcondition none
	 * 
	 * @param game the game to check against this watchlist.
	 * @return true if this watchlist contains the specified game; false otherwise.
	 */
	public boolean contains(Game game) {
		return this.games.contains(game);
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
		if (this.contains(game)) {
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
	
	/**
	 * Clears this watchlist of all games.
	 * 
	 * @precondition none
	 * @postcondition size() == 0
	 */
	public void clear() {
		this.games.clear();
	}
}
