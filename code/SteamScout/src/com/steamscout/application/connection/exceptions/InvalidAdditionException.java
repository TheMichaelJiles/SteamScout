package com.steamscout.application.connection.exceptions;

import com.steamscout.application.model.game_data.Game;

/**
 * This is the exception that gets thrown when a user tries to add an invalid
 * game (game is already in watchlist or game does not exist)
 * 
 * @author TheMichaelJiles
 *
 */
public class InvalidAdditionException extends Exception {

	/**
	 * Constructs an invalidAdditionException for general addition. Signifies that
	 * either the game is already on the watchlist, or the game does not exist
	 * 
	 * @precondition none
	 * @postcondition none
	 */
	public InvalidAdditionException() {
		super("The game could not be added to the watchlist because it either already exists in the watchlist or the game does not exist on steam.");
	}

	/**
	 * Constructs an InvalidAdditionException for the specified game. Signifies that
	 * either the game is already on the watchlist, or the game does not exist on
	 * steam.
	 * 
	 * @precondition none
	 * @postcondition none
	 * 
	 * @param game the invalid game
	 */
	public InvalidAdditionException(Game game) {
		super(game.getTitle()
				+ " could not be added to the watchlist because it either already exists in the watchlist or the game does not exist on steam.");
	}
}
