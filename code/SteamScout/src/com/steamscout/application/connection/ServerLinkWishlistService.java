package com.steamscout.application.connection;

import org.json.JSONArray;
import org.json.JSONObject;

import com.steamscout.application.connection.interfaces.LinkWishlistService;
import com.steamscout.application.model.game_data.Game;
import com.steamscout.application.model.game_data.Watchlist;
import com.steamscout.application.model.notification.NotificationCriteria;

/**
 * This service contacts the server to perform the link steam wishlist service.
 * This will cause an api call on the server to get the games on the wishlist
 * and persist them with the games currently in the user's watchlist.
 * 
 * @author Luke Whaley
 *
 */
public class ServerLinkWishlistService extends ServerService<Watchlist> implements LinkWishlistService {

	private String username;
	private String accountId;
	
	/**
	 * Creates a new ServerLinkWishlistService with the specified username.
	 * 
	 * @precondition username != null
	 * @postcondition none
	 * 
	 * @param accountId the Steam account id associated with the desired wishlist.
	 */
	public ServerLinkWishlistService(String accountId) {
		this.setAccountId(accountId);
	}
	
	@Override
	public Watchlist linkSteamWishlist(String username) {
		this.setUsername(username);
		return this.send();
	}

	@Override
	protected Watchlist interpretJsonString(String json) {
		JSONObject root = new JSONObject(json);
		Watchlist watchlist = null;
		boolean isSuccessful = root.getBoolean("result");
		if (isSuccessful) {
			JSONArray watchlistData = root.getJSONArray("watchlist");
			watchlist = new Watchlist();
			for (int i = 0; i < watchlistData.length(); i++) {
				JSONObject gameData = watchlistData.getJSONObject(i);
				
				Game game = new Game(gameData.getInt("steamid"), gameData.getString("title"));
				game.setCurrentPrice(gameData.getDouble("actualprice"));
				game.setInitialPrice(gameData.getDouble("initialprice"));
				game.setOnSale(gameData.getBoolean("onsale"));
					
				NotificationCriteria criteria = new NotificationCriteria();
				criteria.setTargetPrice(gameData.getDouble("targetprice_criteria"));
				criteria.shouldNotifyOnSale(gameData.getBoolean("onsale_selected"));
				criteria.shouldNotifyWhenBelowTargetPrice(gameData.getBoolean("targetprice_selected"));
					
				watchlist.add(game);
				watchlist.putNotificationCriteria(game, criteria);
			}
		}
		return watchlist;
	}

	@Override
	protected String getSendingJsonString() {
		JSONObject user = new JSONObject();
		user.put("username", this.username);
		user.put("steamid", this.accountId);

		JSONObject data = new JSONObject();
		data.put("user", user);

		JSONObject root = new JSONObject();
		root.put("type", "link_steam");
		root.put("data", data);

		return root.toString();
	}

	/**
	 * Sets the steam account id that is used to pull the Steam
	 * wishlist.
	 * 
	 * @precondition accountId != null
	 * @postcondition none
	 * 
	 * @param accountId the Steam account used to get the wishlist.
	 */
	public void setAccountId(String accountId) {
		if (accountId == null) {
			throw new IllegalArgumentException("account id should not be null.");
		}
		this.accountId = accountId;
	}
	
	/**
	 * Sets the username of the currently logged in user.
	 * 
	 * @precondition username != null
	 * @postcondition none
	 * 
	 * @param username the username of the currently logged in user.
	 */
	public void setUsername(String username) {
		if (username == null) {
			throw new IllegalArgumentException("username should not be null.");
		}
		this.username = username;
	}
}
