package com.steamscout.application.connection;

import org.json.JSONArray;
import org.json.JSONObject;

import com.steamscout.application.connection.interfaces.WatchlistFetchService;
import com.steamscout.application.model.game_data.Game;
import com.steamscout.application.model.game_data.Watchlist;
import com.steamscout.application.model.notification.NotificationCriteria;

public class ServerWatchlistFetchService extends ServerService<Watchlist> implements WatchlistFetchService {
	
	private String username;
	
	@Override
	public Watchlist fetchWatchlist(String username) {
		this.username = username;
		return this.send();
	}

	@Override
	protected Watchlist interpretJsonString(String receivingJson) {
		JSONObject root = new JSONObject(receivingJson);
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
		user.put("username", this.username);
		
		JSONObject data = new JSONObject();
		data.put("user", user);
		
		JSONObject root = new JSONObject();
		root.put("type", "fetch_watchlist");
		root.put("data", data);
		
		return root.toString();
	}

	/**
	 * The username of the desired watchlist.
	 * 
	 * @precondition none
	 * @postcondition none
	 * 
	 * @param username the username of the desired watchlist.
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	
}
