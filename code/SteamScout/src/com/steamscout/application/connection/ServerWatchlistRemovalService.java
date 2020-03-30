package com.steamscout.application.connection;

import org.json.JSONArray;
import org.json.JSONObject;

import com.steamscout.application.connection.interfaces.WatchlistRemovalService;
import com.steamscout.application.model.game_data.Game;
import com.steamscout.application.model.game_data.Watchlist;
import com.steamscout.application.model.notification.NotificationCriteria;
import com.steamscout.application.model.user.Credentials;
import com.steamscout.application.model.user.User;

public class ServerWatchlistRemovalService extends ServerService<Watchlist> implements WatchlistRemovalService {

	private Credentials credentials;
	private Game game;
	
	@Override
	public Watchlist removeGameFromWatchlist(User currentUser, Game game) {
		this.credentials = currentUser.getCredentials();
		this.game = game;
		return this.send();
	}

	@Override
	protected Watchlist interpretJsonString(String json) {
		JSONObject root = new JSONObject(json);
		JSONArray watchlistData = root.getJSONArray("games_on_watchlist");
		Watchlist watchlist = new Watchlist();
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
		return watchlist;
	}

	@Override
	protected String getSendingJsonString() {
		JSONObject user = new JSONObject();
		user.put("username", this.credentials.getUsername());
		user.put("password", this.credentials.getPassword());

		JSONObject gameToAdd = new JSONObject();
		gameToAdd.put("data", this.game.getAppId());

		JSONObject data = new JSONObject();
		data.put("user", user);
		data.put("steamid", this.game.getAppId());

		JSONObject root = new JSONObject();
		root.put("type", "watchlist_removal");
		root.put("data", data);

		return root.toString();
	}

	/**
	 * The credentials for the user having the game removed.
	 * 
	 * @precondition none
	 * @postcondition none
	 * 
	 * @param credentials the credentials for the user having the game removed.
	 */
	public void setCredentials(Credentials credentials) {
		this.credentials = credentials;
	}

	/**
	 * Sets the game for which this service will attempt to remove.
	 * 
	 * @precondition none
	 * @postcondition none
	 * 
	 * @param game the game for this service to remove.
	 */
	public void setGame(Game game) {
		this.game = game;
	}

}
