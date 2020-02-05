package com.steamscout.application.model.game_data;

import java.util.ArrayList;
import java.util.Collection;
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
	 * Adds the specified game to this watchlist if this
	 * watchlist does not already contain the game.
	 * 
	 * @precondition none
	 * @postcondition size() == size()@prev + 1
	 * 
	 * @param game the game to add to this watchlist.
	 */
	public void addGame(Game game) {
		if (!this.contains(game)) {
			this.games.add(game);	
		}
	}
	
	/**
	 * Adds all games that are not already in this watchlist to this
	 * watchlist.
	 * 
	 * @precondition games != null
	 * @postcondition all games not already in this watchlist are now in watchlist.
	 * 
	 * @param games the collection of games to add to this watchlist.
	 */
	public void addAllGames(Collection<Game> games) {
		if (games == null) {
			throw new IllegalArgumentException("games should not be null.");
		}
		
		for (Game currentGame : games) {
			this.addGame(currentGame);
		}
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
