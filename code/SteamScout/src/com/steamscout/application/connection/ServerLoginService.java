package com.steamscout.application.connection;

import com.steamscout.application.connection.exceptions.InvalidCredentialsException;
import com.steamscout.application.connection.interfaces.LoginService;
import com.steamscout.application.model.user.Credentials;
import com.steamscout.application.model.user.User;
import com.steamscout.application.model.game_data.Game;
import com.steamscout.application.model.game_data.Watchlist;
import com.steamscout.application.model.notification.NotificationCriteria;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * This service logs in to the server on behalf of a user.
 * 
 * @author Luke Whaley
 *
 */
public class ServerLoginService extends ServerService<User> implements LoginService {
	
	private Credentials credentials;	
	
	@Override
	public User login(Credentials credentials) throws InvalidCredentialsException {
		this.credentials = credentials;
		return this.send();
	}

	@Override
	protected User interpretJsonString(String json) {
		JSONObject root = new JSONObject(json);
		boolean isLoginSuccessful = root.getBoolean("result");
		if (isLoginSuccessful) {
			JSONArray watchlistData = root.getJSONArray("watchlist");
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
			return new User(this.credentials, watchlist);
		} else {
			throw new InvalidCredentialsException(this.credentials);
		}
	}

	@Override
	protected String getSendingJsonString() {
		JSONObject user = new JSONObject();
		user.put("username", this.credentials.getUsername());
		user.put("password", this.credentials.getPassword());
		
		JSONObject data = new JSONObject();
		data.put("user", user);
		
		JSONObject root = new JSONObject();
		root.put("type", "authenticate");
		root.put("data", data);
		
		return root.toString();
	}

	/**
	 * The credentials for the user logging in.
	 * 
	 * @precondition none
	 * @postcondition none
	 * 
	 * @param credentials the credentials for the user logging in.
	 */
	public void setCredentials(Credentials credentials) {
		this.credentials = credentials;
	}
	
}
