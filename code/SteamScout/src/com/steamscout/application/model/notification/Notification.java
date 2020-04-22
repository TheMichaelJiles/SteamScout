package com.steamscout.application.model.notification;

import java.time.LocalTime;

import com.steamscout.application.model.game_data.Game;

/**
 * This is a data class that encapsulates information
 * about a notification for a particular game.
 * 
 * @author Nathan Lightholder
 *
 */
public class Notification {
	
	private LocalTime timeNotified; 
	private String title;
	private String steamLink;
	private double currentPrice;
	private double initialPrice;
	
	/**
	 * Creates a notification object
	 * 
	 * @precondition game != null
	 * @postcondition a notification is created
	 * 
	 * @param game the game the notification is for
	 */
	public Notification(Game game) {
		if (game == null) {
			throw new IllegalArgumentException("Game is null");
		}
		this.timeNotified = LocalTime.now();
		this.title = game.getTitle();
		this.steamLink = game.getSteamLink();
		this.currentPrice = game.getCurrentPrice();
		this.initialPrice = game.getInitialPrice();
	}
	
	/**
	 * Gets the time the notification was created AKA
	 * the time the user was notified
	 * 
	 * @precondition none
	 * @return the time notified
	 */
	public LocalTime getTimeNotified() {
		return this.timeNotified;
	}
	
	/**
	 * Gets the title of the game the notification is for
	 * 
	 * @precondition none
	 * @return the title of the game
	 */
	public String getTitle() {
		return this.title;
	}
	
	/**
	 * Gets the steam link of the game the notification is for
	 * 
	 * @precondition none
	 * @return the steam link of the game
	 */
	public String getSteamLink() {
		return this.steamLink;
	}
	
	/**
	 * Gets the current price of the game the notification is for
	 * 
	 * @precondition none
	 * @return the current price of the game
	 */
	public double getCurrentPrice() {
		return this.currentPrice;
	}
	
	/**
	 * Gets the initial price of the game the notification is for
	 * 
	 * @precondition none
	 * @return the initial price of the game
	 */
	public double getInitialPrice() {
		return this.initialPrice;
	}
	
	/**
	 * Gets the amount the game price is reduced by
	 * 
	 * @precondition none
	 * @return the amount the game is reduced by
	 */
	public double getPriceReduction() {
		return this.initialPrice - this.currentPrice;
	}
	
	/**
	 * Gets the price reduction as a percentage
	 * 
	 * @precondition none
	 * @return the price reduction as a percentage
	 */
	public double getReductionAsPercentage() {
		return this.getPriceReduction() / this.initialPrice;
	}

}
