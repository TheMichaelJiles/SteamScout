package com.steamscout.application.connection.interfaces;

import com.steamscout.application.model.game_data.Watchlist;

/**
 * Defines behavior that allows the system to link a steam wishlist
 * to a user's watchlist. This effectively puts all games that are 
 * on the user's steam account's wishlist on their watchlist.
 * 
 * @author Luke Whaley
 *
 */
public interface LinkWishlistService {

	/**
	 * Links the wishlist associated with a steam account id to 
	 * the currently logged in user's watchlist. If there were no games
	 * on the wishlist, or if an error occurs, the watchlist will be 
	 * will remain the same as the current watchlist.
	 * 
	 * @precondition accountId != null
	 * @postcondition none
	 * 
	 * @param username the username of the currently logged inuser.
	 * @return the newly linked Watchlist.
	 */
	Watchlist linkSteamWishlist(String username);
}
