package com.steamscout.application.model.game_data;

import java.util.Map;

import com.steamscout.application.model.api.AppListAPI;

import javafx.concurrent.Task;

/**
 * A Runnable task that is used to poll the steam api
 * to retrieve a map of games and gameids.
 * 
 * @author Thomas Whaley
 *
 */
public class SteamGameLoader extends Task<Map<String, Integer>> {

	@Override
	protected Map<String, Integer> call() throws Exception {
		AppListAPI api = new AppListAPI();
		return api.makeRequest();
	}

}
