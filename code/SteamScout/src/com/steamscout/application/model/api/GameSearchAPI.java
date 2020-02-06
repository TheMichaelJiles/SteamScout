package com.steamscout.application.model.api;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;

import com.steamscout.application.model.api.exceptions.GameNotFoundException;
import com.steamscout.application.model.game_data.Game;

/**
 * This api class takes a steam game id and returns a 
 * game object with data pulled from the steam api for that game.
 * 
 * @author Thomas Whaley
 *
 */
public class GameSearchAPI extends APIRequest {

	private static final String GAME_SEARCH_API_URL = "https://store.steampowered.com/api/appdetails?appids=";
	private static final String GAME_SEARCH_API_URL_POSTFIX = "&cc=us&l=en";
	
	private int appId;
	
	/**
	 * Creates a new GameSearchAPI object with the specified appId.
	 * 
	 * @precondition none
	 * @postcondition none
	 * 
	 * @param appId the id of the game to search.
	 */
	public GameSearchAPI(int appId) {
		super(GAME_SEARCH_API_URL + appId + GAME_SEARCH_API_URL_POSTFIX);
		this.appId = appId;
	}

	@Override
	public Game makeRequest() throws IOException {
		JSONObject json = this.pollApi();
		JSONObject root = json.getJSONObject(String.valueOf(this.appId));
		if (!root.getBoolean("success")) {
			throw new GameNotFoundException(this.appId);
		}
		JSONObject data = root.getJSONObject("data");
		if (!data.getString("type").equals("game")) {
			throw new GameNotFoundException(this.appId);
		}
		JSONObject priceOverview = data.getJSONObject("price_overview");
		JSONArray developers = data.getJSONArray("developers");
		
		StringBuilder builder = new StringBuilder();
		developers.forEach(dev -> builder.append(dev));
		String gameStudioDescription = builder.toString();
		String gameTitle = data.getString("name");
		int gameAppId = data.getInt("steam_appid");
		String steamLink = "https://store.steampowered.com/app/" + gameAppId;
		double gameCurrentPrice = priceOverview.getInt("final") / 100.0;
		double gameInitialPrice = priceOverview.getInt("initial") / 100.0;
		boolean gameIsOnSale = priceOverview.getInt("discount_percent") > 0;
		
		Game game = new Game(gameAppId, gameTitle);
		game.setStudioDescription(gameStudioDescription);
		game.setSteamLink(steamLink);
		game.setCurrentPrice(gameCurrentPrice);
		game.setInitialPrice(gameInitialPrice);
		game.setOnSale(gameIsOnSale);
		
		return game;
	}
}
