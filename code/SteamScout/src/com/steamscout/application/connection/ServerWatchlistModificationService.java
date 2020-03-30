package com.steamscout.application.connection;

import org.json.JSONArray;
import org.json.JSONObject;

import com.steamscout.application.connection.interfaces.WatchlistModificationService;
import com.steamscout.application.model.game_data.Game;
import com.steamscout.application.model.game_data.Watchlist;
import com.steamscout.application.model.notification.NotificationCriteria;
import com.steamscout.application.model.user.Credentials;
import com.steamscout.application.model.user.User;

public class ServerWatchlistModificationService extends ServerService<Watchlist> implements WatchlistModificationService {
	
	private Credentials credentials;
	private Game game;
	private NotificationCriteria criteria;

	@Override
	public Watchlist modifyGameOnWatchlist(User user, Game game,
			NotificationCriteria criteria) {
		this.credentials = user.getCredentials();
		this.game = game;
		this.criteria = criteria;
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
			return null;
		}
	}

	@Override
	protected String getSendingJsonString() {
		JSONObject user = new JSONObject();
		user.put("username", this.credentials.getUsername());
		user.put("password", this.credentials.getPassword());

		JSONObject gameToModify = new JSONObject();
		gameToModify.put("steamid", this.game.getAppId());
		gameToModify.put("onsaleselected", this.criteria.shouldNotifyOnSale());
		gameToModify.put("targetprice", this.criteria.getTargetPrice());
		gameToModify.put("targetpriceselected", this.criteria.shouldNotifyWhenBelowTargetPrice());

		JSONObject data = new JSONObject();
		data.put("user", user);
		data.put("game", gameToModify);

		JSONObject root = new JSONObject();
		root.put("type", "watchlist_modification");
		root.put("data", data);

		return root.toString();
	}

	/**
	 * The credentials for the user having criteria modified.
	 * 
	 * @precondition none
	 * @postcondition none
	 * 
	 * @param credentials the credentials for the user having the criteria modified.
	 */
	public void setCredentials(Credentials credentials) {
		this.credentials = credentials;
	}

	/**
	 * The game to change the criteria of.
	 * 
	 * @precondition none
	 * @postcondition none
	 * 
	 * @param game the game to change the criteria of.
	 */
	public void setGame(Game game) {
		this.game = game;
	}

	/**
	 * The new criteria that will be modified.
	 * 
	 * @precondition none
	 * @postcondition none
	 * 
	 * @param criteria the new criteria that will be modified.
	 */
	public void setCriteria(NotificationCriteria criteria) {
		this.criteria = criteria;
	}

}
