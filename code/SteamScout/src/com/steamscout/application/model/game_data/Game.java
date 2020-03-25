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
	private String title;
	
	private double currentPrice;
	private double initialPrice;
	private boolean isOnSale;
	
	private String steamLink;
	
	/**
	 * Creates a new Game object that encapsulates information.
	 * 
	 * @precondition title != null
	 * @postcondition getTitle().equals(title) && getAppId() == appId
	 * 				  && getStudioDescription.equals("") && getSteamLink().equals("")
	 * 				  && getImageUrl().equals("") && getCurrentPrice() == 0 &&
	 * 				  getInitialPrice() == 0 && !isOnSale() && getUserPriceThreshold() == 0
	 * 
	 * @param title the title of the game.
	 * @param appId this game's corresponding steam app id.
	 */
	public Game(int appId, String title) {
		if (title == null) {
			throw new IllegalArgumentException("title cannot be null.");
		}
		
		this.title = title;
		this.appId = appId;
		this.steamLink = "https://store.steampowered.com/app/" + appId;
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
	 * Gets a description of this game's fields.
	 * 
	 * @precondition none
	 * @postcondition none
	 * 
	 * @return a description of this game's fields.
	 */
	public String getDescription() {
		return "Title: " + this.title + System.lineSeparator()
			 + "AppId: " + this.appId + System.lineSeparator()
			 + "SteamLink: " + this.steamLink + System.lineSeparator()
			 + "InitialPrice: " + this.initialPrice + System.lineSeparator()
			 + "CurrentPrice: " + this.currentPrice + System.lineSeparator()
			 + "IsOnSale: " + this.isOnSale + System.lineSeparator()
			 + System.lineSeparator();
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
