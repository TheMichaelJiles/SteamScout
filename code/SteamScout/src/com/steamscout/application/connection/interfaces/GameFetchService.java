/**
 * 
 */
package com.steamscout.application.connection.interfaces;

import java.util.Map;

/**
 * Fetches all of the steam games for the application
 * 
 * @author Nathan Lightholder
 *
 */
public interface GameFetchService {

	/**
	 * Attempts to fetch all of the games from steam
	 * 
	 * @precondition none
	 * @return the games in the system
	 */
	Map<String, Integer> FetchGames();
}
