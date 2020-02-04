package com.steamscout.application.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONArray;
import org.json.JSONObject;

/** 
 * THIS CLASS IS ONLY HERE SO THAT WE CAN USE THE CODE
 * TO CREATE A CLEANER, MORE ORGANIZED CLASS STRUCTURE FOR STEAMSCOUT.
 * 
 * 
 * @author Thomas Whaley
 *
 */
public class DeleteMeApiTestClass {

	public static final String GAME_CODES = "http://api.steampowered.com/ISteamApps/GetAppList/v0001/";
	
	public Map<String, Integer> getMatches() {
		Map<String, Integer> matches = new HashMap<String, Integer>();
		
		HttpClient client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(GAME_CODES);
		HttpResponse response = null;
		String result = null;
		
		try {
			response = client.execute(request);
			HttpEntity entity = response.getEntity();
			
			if (entity != null) {
				InputStream is = entity.getContent();
				BufferedReader reader = new BufferedReader(new InputStreamReader(is));
				StringBuilder sb = new StringBuilder();
				String line = null;
				while ((line = reader.readLine()) != null) {
					sb.append(line + System.lineSeparator());
				}
				is.close();
				result = sb.toString();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		JSONObject json = new JSONObject(result);
		JSONObject applist = json.getJSONObject("applist");
		JSONObject apps = applist.getJSONObject("apps");
		JSONArray array = apps.getJSONArray("app");
		for (int i = 0; i < array.length(); i++) {
			JSONObject currentObject = array.getJSONObject(i);
			matches.put(currentObject.getString("name"), currentObject.getInt("appid"));
		}
		
		return matches;
	}
	
}
