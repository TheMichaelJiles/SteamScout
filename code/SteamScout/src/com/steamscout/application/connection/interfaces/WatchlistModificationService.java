package com.steamscout.application.connection.interfaces;

import com.steamscout.application.model.game_data.Game;
import com.steamscout.application.model.game_data.Watchlist;
import com.steamscout.application.model.notification.NotificationCriteria;
import com.steamscout.application.model.user.User;

/**
 * Defines behavior for modifying a game on the watchlist.
 * @author TheMichaelJiles
 *
 */
public interface WatchlistModificationService {
	
	/**
	 * Attempts to modify the passed in game on the current user's watchlist with
	 * the passed in notification criteria.
	 * @param currentUser the current logged in user
	 * @param gameToModify the game to modify
	 * @param notificationCriteria the notificationCriteria
	 * 
	 * @precondition user != null, gameToModify != null
	 * @postcondition the game is successfully modified
	 * 
	 * @return the new watchlist after the modification
	 */
	Watchlist modifyGameOnWatchlist(User currentUser, Game gameToModify, NotificationCriteria notificationCriteria);
}
