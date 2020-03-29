package com.steamscout.application.connection.interfaces;

import com.steamscout.application.model.game_data.Game;
import com.steamscout.application.model.game_data.Watchlist;
import com.steamscout.application.model.user.Credentials;

/**
 * Defines behavior for removing a game on the watchlist
 * @author TheMichaelJiles
 *
 */
public interface WatchlistRemovalService {

	/**
	 * Attempts to remove the passed in game from the watchlist of the user
	 * with the passed in credentials.
	 * @param credentials the credentials of the current user
	 * @param game the game to be removed from the watchlist
	 * 
	 * @precondition game != null
	 * @postcondition the game is removed from the watchlist
	 * 
	 * @return the new watchlist post-removal
	 */
	Watchlist removeGameFromWatchlist(Credentials credentials, Game game);
}
