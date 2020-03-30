package com.steamscout.application.connection.interfaces;

import com.steamscout.application.model.game_data.Watchlist;

/**
 * This service fetches the watchlist for the account with the
 * specified username.
 * 
 * @author Luke Whaley
 *
 */
public interface WatchlistFetchService {

	/**
	 * Retrieves the watchlist for the account with the specified 
	 * username.
	 * 
	 * @precondition username != null
	 * @postcondition none
	 * 
	 * @param username the username of the account to fetch the watchlist for
	 * @return the watchlist of the account with the specified username
	 */
	Watchlist fetchWatchlist(String username);
	
}
