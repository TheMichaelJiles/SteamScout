package com.steamscout.application.notification;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import com.steamscout.application.model.api.GameSearchAPI;
import com.steamscout.application.model.game_data.Game;
import com.steamscout.application.model.notification.Notification;

import javafx.concurrent.Task;

/**
 * Contains a static method that allows for a collection of games
 * to be checked against the steam api.
 * 
 * @author Thomas Whaley
 *
 */
public class NotificationCheck extends Task<Collection<Notification>> {

	private Collection<Game> games;
	
	/**
	 * Creates a new NotificationCheck Task.
	 * 
	 * @precondition games != null
	 * @postcondition none
	 * 
	 * @param games the games to check for.
	 */
	public NotificationCheck(Collection<Game> games) {
		if (games == null) {
			throw new IllegalArgumentException("games should not be null.");
		}
		
		this.games = games;
	}
	
	@Override
	protected Collection<Notification> call() throws Exception {
		return this.query();
	}
	
	/**
	 * Queries the steam api against the specified collection of games.
	 * For each game, it will contact the steam api to retrieve the most current
	 * information. Then, from the current information, if the game is on sale or
	 * the game's current price is less than the price threshold set by the user,
	 * it will generate a notification for that game.
	 * 
	 * @precondition games != null
	 * @postcondition none
	 * 
	 * @param games the collection of games to check against the steam api.
	 * @return a collection of Notification objects.
	 */
	private Collection<Notification> query() {
		Collection<Notification> notifications = new ArrayList<Notification>();
		for (Game currentGame : games) {
			GameSearchAPI steamSearch = new GameSearchAPI(currentGame.getAppId());
			try {
				Game updatedCurrentGame = steamSearch.makeRequest();	
				if (updatedCurrentGame.isOnSale() || (updatedCurrentGame.getCurrentPrice() <= currentGame.getUserPriceThreshold())) {
					Notification notification = new Notification();
					notifications.add(notification);
				}
			} catch (IOException e) {
				System.err.println(currentGame.getAppId() + ": notification update query failed.");
			}
		}
		return notifications;
	}

}
