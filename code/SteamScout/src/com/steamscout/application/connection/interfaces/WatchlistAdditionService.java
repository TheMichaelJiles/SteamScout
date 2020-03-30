package com.steamscout.application.connection.interfaces;

import com.steamscout.application.connection.exceptions.InvalidAdditionException;
import com.steamscout.application.model.game_data.Game;
import com.steamscout.application.model.game_data.Watchlist;
import com.steamscout.application.model.user.Credentials;

/**
 * Defines behavior for adding a game to a watchlist
 * @author Michael Jiles
 *
 */
public interface WatchlistAdditionService {
	
	/**
	 * Attempts to add the specified game to the watchlist related to the user
	 * with the passed in credentials. Returns the newly modified watchlist
	 * 
	 * @precondition credentials != null, gameToAdd != null
	 * @postcondition the game is added if successful
	 * 
	 * @param credentials the credentials of the user that owns the watchlist the
	 * game should be added to
	 * @param gameToAdd the game to be added
	 * @throws InvalidAdditionException if the game can not be added to the watchlist.
	 * @return the newly modified watchlist
	 */
	Watchlist addGameToWatchlist(Credentials credentials, Game gameToAdd) throws InvalidAdditionException;
}
