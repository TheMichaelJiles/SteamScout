package com.steamscout.application.model.notification;

/**
 * A data class that contains notification information about
 * when a notification should be sent.
 * 
 * @author Thomas Whaley
 *
 */
public class NotificationCriteria {

	private boolean shouldNotifyOnSale;
	
	private boolean shouldNotifyWhenBelowTargetPrice;
	private double targetPrice;
	
	/**
	 * Creates a new NotificationCriteria object.
	 * 
	 * @precondition none
	 * @postcondition !shouldNotifyOnSale() &&
	 * 				  !shouldNotifyWhenBelowTargetPrice() &&
	 * 				  getTargetPrice() == 0
	 */
	public NotificationCriteria() {
		this.shouldNotifyOnSale = false;
		this.shouldNotifyWhenBelowTargetPrice = false;
		this.targetPrice = 0;
	}

	/**
	 * Determines whether or not this NotificationCriteria object
	 * is in the default state.
	 * 
	 * @precondition none
	 * @postcondition none
	 * 
	 * @return true if this criteria is in the default state; false otherwise.
	 */
	public boolean isDefault() {
		return !this.shouldNotifyOnSale && !this.shouldNotifyWhenBelowTargetPrice && this.targetPrice == 0;
	}
	
	/**
	 * Gets whether or not the notification should be sent
	 * when the game is on sale.
	 * 
	 * @precondition none
	 * @postcondition none
	 * 
	 * @return true if should be notified on sale; false otherwise.
	 */
	public boolean shouldNotifyOnSale() {
		return this.shouldNotifyOnSale;
	}

	/**
	 * Sets whether or not the notification should be sent when
	 * the game is on sale.
	 * 
	 * @precondition none
	 * @postcondition shouldNotifyOnSale() == shouldNotifyOnSale
	 * 
	 * @param shouldNotifyOnSale whether or not the notification should be sent when
	 * the game is on sale.
	 */
	public void shouldNotifyOnSale(boolean shouldNotifyOnSale) {
		this.shouldNotifyOnSale = shouldNotifyOnSale;
	}

	/**
	 * Gets whether or not the notification should be sent when the
	 * game is below the target price.
	 * 
	 * @precondition none
	 * @postcondition none
	 * 
	 * @return true if the notification should be sent when below target price; false otherwise.
	 */
	public boolean shouldNotifyWhenBelowTargetPrice() {
		return this.shouldNotifyWhenBelowTargetPrice;
	}

	/**
	 * Sets whether or not the notification should be sent when the
	 * game is below the target price.
	 * 
	 * @precondition none
	 * @postcondition shouldNotifyWhenBelowTargetPrice() == shouldNotifyWhenBelowTargetPrice
	 * 
	 * @param shouldNotifyWhenBelowTargetPrice whether or not the notification should be sent when
	 * the game is on sale.
	 */
	public void shouldNotifyWhenBelowTargetPrice(boolean shouldNotifyWhenBelowTargetPrice) {
		this.shouldNotifyWhenBelowTargetPrice = shouldNotifyWhenBelowTargetPrice;
	}

	/**
	 * Gets the target price.
	 * 
	 * @precondition none
	 * @postcondition none
	 * 
	 * @return the target price.
	 */
	public double getTargetPrice() {
		return this.targetPrice;
	}

	/**
	 * Sets the target price.
	 * 
	 * @precondition targetPrice >= 0
	 * @postcondition getTargetPrice() == targetPrice
	 * 
	 * @param targetPrice the new target price.
	 */
	public void setTargetPrice(double targetPrice) {
		if (targetPrice < 0) {
			throw new IllegalArgumentException("targetPrice must be greater than or equal to zero.");
		}
		
		this.targetPrice = targetPrice;
	}
	
	
}
