package com.steamscout.application.model.api.exceptions;

/**
 * A custom exception that occurs when the api pulls
 * attempt to create a game from an id that does not 
 * link to an actual game.
 * 
 * @author Thomas Whaley
 *
 */
public class GameNotFoundException extends IllegalArgumentException {

	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new GameNotFoundException.
	 * 
	 * @param id the id of product on the steam store.
	 */
	public GameNotFoundException(int id) {
		super("Steam " + id + " is not a game.");
	}
}
