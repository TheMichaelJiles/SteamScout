/**
 * 
 */
package com.steamscout.application.connection;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

<<<<<<< HEAD
import com.steamscout.application.connection.interfaces.GameFetchService;

public class ServerGameFetchService implements GameFetchService{
	
	private static final String HOST_PORT_PAIR = "tcp://127.0.0.1:5555";
=======
public class ServerGameFetchService extends ServerService<Map<String, Integer>> {
>>>>>>> branch 'master' of https://github.com/UWG-Software-Engineering/UWG-SE2-Spring20-Group4.git
	
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
