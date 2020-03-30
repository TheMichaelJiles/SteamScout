package com.steamscout.application.connection;

import org.json.JSONArray;
import org.json.JSONObject;

import com.steamscout.application.connection.exceptions.InvalidAdditionException;
import com.steamscout.application.connection.interfaces.WatchlistAdditionService;
import com.steamscout.application.model.game_data.Game;
import com.steamscout.application.model.game_data.Watchlist;
import com.steamscout.application.model.notification.NotificationCriteria;
import com.steamscout.application.model.user.Credentials;

/**
 * Adds a game to a user's watchlist on the server.
 * 
 * @author Michael Jiles
 *
 */
public class ServerWatchlistAdditionService extends ServerService<Watchlist> implements WatchlistAdditionService {
	
	private Credentials credentials;
	private Game game;
	
	@Override
	public Watchlist addGameToWatchlist(Credentials credentials, Game game) throws InvalidAdditionException {
		this.credentials = credentials;
		this.game = game;
		return this.send();
	}
	
	@Override
	protected Watchlist interpretJsonString(String json) {
		JSONObject root = new JSONObject(json);
		boolean wasGameAdded = root.getBoolean("result");
		if (wasGameAdded) {
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
		} else {
			throw new InvalidAdditionException();
		}
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
		root.put("type", "watchlist_addition");
		root.put("data", data);
		
		return root.toString();
	}

	/**
	 * The credentials for the user having a game added.
	 * 
	 * @precondition none
	 * @postcondition none
	 * 
	 * @param credentials the credentials for the user having a game added.
	 */
	public void setCredentials(Credentials credentials) {
		this.credentials = credentials;
	}

	/**
	 * The game to add.
	 * 
	 * @precondition none
	 * @postcondition none
	 * 
	 * @param game the game to add.
	 */
	public void setGame(Game game) {
		this.game = game;
	}
	
}
