/**
 * 
 */
package com.steamscout.application.connection;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class ServerGameFetchService extends ServerService<Map<String, Integer>> {
	
	public Map<String, Integer> FetchGames() {
		return this.send();
	}

	@Override
	protected Map<String, Integer> interpretJsonString(String receivingJson) {
		JSONObject root = new JSONObject(receivingJson);
		JSONArray gameData = root.getJSONArray("games");
		Map<String, Integer> steamGames = new HashMap<String, Integer>();
		for (int i = 0; i < gameData.length(); i++) {
			JSONObject data = gameData.getJSONObject(i);
			
			String gameTitle = data.getString("title");
			int gameId = data.getInt("steamid");
			
			steamGames.put(gameTitle, gameId);
		}
		return steamGames;
	}

	@Override
	protected String getSendingJsonString() {
		JSONObject root = new JSONObject();
		root.put("type", "fetch_games");

		return root.toString();
	}

}
