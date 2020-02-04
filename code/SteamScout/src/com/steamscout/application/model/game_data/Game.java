package com.steamscout.application.model.game_data;

/**
 * An encapsulating game data that stores data
 * specific to a certain game provided by Steam.
 * 
 * @author Thomas Whaley
 *
 */
public class Game {

	private int appId;
	
	private double currentPrice;
	private double initialPrice;
	
	private boolean isOnSale;
	private double userPriceThreshold;
	
	private String title;
	private String studioDescription;
	private String steamLink;
	
	/**
	 * Creates a new Game object that encapsulates information.
	 * 
	 * @precondition title != null
	 * @postcondition getTitle().equals(title) && getAppId() == appId
	 * 
	 * @param title the title of the game.
	 */
	public Game(int appId, String title) {
		if (title == null) {
			throw new IllegalArgumentException("title cannot be null.");
		}
		
		this.title = title;
		this.appId = appId;
	}
	
	@Override
	public int hashCode() {
		return this.appId;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Game) {
			Game otherGame = (Game) obj;
			return this.appId == otherGame.getAppId();
		}
		
		return super.equals(obj);
	}

	/**
	 * Sets this games current price to the specified value.
	 * 
	 * @precondition currentPrice >= 0
	 * @postcondition getCurrentPrice() == currentPrice
	 * 
	 * @param currentPrice the game's new current price.
	 */
	public void setCurrentPrice(double currentPrice) {
		if (currentPrice < 0) {
			throw new IllegalArgumentException("currentPrice must be greater than or equal to zero.");
		}
		
		this.currentPrice = currentPrice;
	}

	/**
	 * Sets this games initial price to the specified value.
	 * 
	 * @precondition initialPrice >= 0
	 * @postcondition getInitialPrice() == initialPrice
	 * 
	 * @param initialPrice the game's new initial price.
	 */
	public void setInitialPrice(double initialPrice) {
		if (initialPrice < 0) {
			throw new IllegalArgumentException("initialPrice must be greater than or equal to zero.");
		}
		
		this.initialPrice = initialPrice;
	}

	/**
	 * Sets this games price threshold.
	 * 
	 * @precondition userPriceThreshold >= 0
	 * @postcondition getUserPriceThreshold() == userPriceThreshold
	 * 
	 * @param userPriceThreshold the game's price threshold.
	 */
	public void setUserPriceThreshold(double userPriceThreshold) {
		if (userPriceThreshold < 0) {
			throw new IllegalArgumentException("userPriceThreshold must be greater than or equal to zero.");
		}
		
		this.userPriceThreshold = userPriceThreshold;
	}

	/**
	 * Sets this game's description of its producing studio.
	 * 
	 * @precondition studioDescription != null
	 * @postcondition getStudioDescription().equals(studioDescription)
	 * 
	 * @param studioDescription this game's description of its producing studio.
	 */
	public void setStudioDescription(String studioDescription) {
		if (studioDescription == null) {
			throw new IllegalArgumentException("studioDescription cannot be null.");
		}
		
		this.studioDescription = studioDescription;
	}

	/**
	 * Sets this game's steam link.
	 * 
	 * @precondition steamLink != null
	 * @postcondition getSteamLink().equals(steamLink)
	 * 
	 * @param steamLink this game's link to steam web store.
	 */
	public void setSteamLink(String steamLink) {
		if (steamLink == null) {
			throw new IllegalArgumentException("steamLink cannot be null.");
		}
		
		this.steamLink = steamLink;
	}
	
	/**
	 * Sets whether this game is on sale or not.
	 * 
	 * @precondition none
	 * @postcondition isOnSale() == isOnSale
	 * 
	 * @param isOnSale the new value for isOnSale
	 */
	public void setOnSale(boolean isOnSale) {
		this.isOnSale = isOnSale;
	}

	/**
	 * Gets this game's steam store app id.
	 * 
	 * @precondition none
	 * @postcondition none
	 * 
	 * @return the steam store app id.
	 */
	public int getAppId() {
		return this.appId;
	}
	
	/**
	 * Gets the current price of the game.
	 * 
	 * @precondition none
	 * @postcondition none
	 * 
	 * @return the current price.
	 */
	public double getCurrentPrice() {
		return this.currentPrice;
	}

	/**
	 * Gets the initial price of the game.
	 * 
	 * @precondition none
	 * @postcondition none
	 * 
	 * @return the initial price.
	 */
	public double getInitialPrice() {
		return this.initialPrice;
	}

	/**
	 * Gets whether or not this game is on sale.
	 * 
	 * @precondition none
	 * @postcondition none
	 * 
	 * @return true if this game is on sale; false otherwise.
	 */
	public boolean isOnSale() {
		return this.isOnSale;
	}

	/**
	 * Gets the price threshold set by a user for this game.
	 * 
	 * @precondition none
	 * @postcondition none
	 * 
	 * @return this game's price threshold.
	 */
	public double getUserPriceThreshold() {
		return this.userPriceThreshold;
	}

	/**
	 * Gets the title of this game.
	 * 
	 * @precondition none
	 * @postcondition none
	 * 
	 * @return the title.
	 */
	public String getTitle() {
		return this.title;
	}

	/**
	 * Gets this game's description of its producing studio.
	 * 
	 * @precondition none
	 * @postcondition none
	 * 
	 * @return the studio description for this game.
	 */
	public String getStudioDescription() {
		return this.studioDescription;
	}

	/**
	 * Gets this game's steam link.
	 * 
	 * @precondition none
	 * @postcondition none
	 * 
	 * @return this game's steam link.
	 */
	public String getSteamLink() {
		return this.steamLink;
	}
	
	
}
