package com.steamscout.application.model.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Contains functionality to poll the steam api for a list of games
 * and their corresponding app ids.
 * 
 * @author Thomas Whaley
 *
 */
public class AppListAPI extends APIRequest {

	private static final String APP_LIST_API_URL = "http://api.steampowered.com/ISteamApps/GetAppList/v0001/";
	
	/**
	 * Creates a new AppListAPI object.
	 * 
	 * @precondition none
	 * @postcondition none
	 */
	public AppListAPI() {
		super(APP_LIST_API_URL);
	}
	
	@Override
	public Map<Integer, String> makeRequest() throws IOException {
		Map<Integer, String> games = new HashMap<Integer, String>();
			
		JSONObject json = this.poll();
		JSONObject applist = json.getJSONObject("applist");
		JSONObject apps = applist.getJSONObject("apps");
		JSONArray array = apps.getJSONArray("app");
		for (int i = 0; i < array.length(); i++) {
			JSONObject currentObject = array.getJSONObject(i);
			games.put(currentObject.getInt("appid"), currentObject.getString("name"));
		}
			
		return games;
	}
	
	
}
