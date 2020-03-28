package com.steamscout.application.model.user;

import com.steamscout.application.model.game_data.Watchlist;

/**
 * A user abstraction that maintains data related to a specific user. A user
 * has a watchlist of games that they have subscribed to.
 * 
 * @author Thomas Whaley
 *
 */
public class User {

	private Watchlist watchlist;
	private Credentials credentials;
	
	/**
	 * Creates a new User object with the specified credentials.
	 * 
	 * @precondition credentials != null
	 * @postcondition getCredentials().equals(credentials) && getWatchlist().size() == 0
	 * 
	 * @param credentials the credentials for this user.
	 */
	public User(Credentials credentials) {
		this(credentials, new Watchlist());
	}
	
	/**
	 * Creates a new User object with the specified credentials and 
	 * games to put in this User's watchlist.
	 * 
	 * @precondition credentials != null && games != null && !games.contains(null)
	 * @postcondition getCredentials().equals(credentials) && getWatchlist().size() == games.size()
	 * 
	 * @param credentials the credentials for this user.
	 * @param games the games to put in this user's watchlist.
	 */
	public User(Credentials credentials, Watchlist games) {
		if (credentials == null) {
			throw new IllegalArgumentException("credentials should not be null.");
		}
		if (games == null) {
			throw new IllegalArgumentException("games should not be null.");
		}
		
		this.credentials = credentials;
		this.watchlist = games;
	}

	/**
	 * Gets this user's watchlist.
	 * 
	 * @precondition none
	 * @postcondition none
	 * 
	 * @return this user's watchlist.
	 */
	public Watchlist getWatchlist() {
		return this.watchlist;
	}

	/**
	 * Gets this user's credentials.
	 * 
	 * @precondition none
	 * @postcondition none
	 * 
	 * @return this user's credentials.
	 */
	public Credentials getCredentials() {
		return this.credentials;
	}

	public void setWatchlist(Watchlist newWatchlist) {
		this.watchlist = newWatchlist;
		
	}
	
}
