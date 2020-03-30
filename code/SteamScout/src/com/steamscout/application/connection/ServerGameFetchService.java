/**
 * 
 */
package com.steamscout.application.connection;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.zeromq.SocketType;
import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Context;
import org.zeromq.ZMQ.Socket;

public class ServerGameFetchService {
	
	private static final String HOST_PORT_PAIR = "tcp://127.0.0.1:5555";
	
	public Map<String, Integer> FetchGames() {
		try (Context context = ZMQ.context(1); 
				Socket socket = context.socket(SocketType.REQ)) {
			socket.connect(HOST_PORT_PAIR);
			System.out.println("Initiating Game Fetch Service");
			
			String sendingJson = this.getJsonString();
			socket.send(sendingJson.getBytes(ZMQ.CHARSET), 0);
			System.out.println("Sent the following json");
			System.out.println(new JSONObject(sendingJson).toString(4));
			
			byte[] serverResponseBytes = socket.recv(0);
			String receivingJson = new String(serverResponseBytes, ZMQ.CHARSET);
			System.out.println("Received the following json");
			System.out.println(new JSONObject(receivingJson).toString(4));
			
			return this.interpretJsonString(receivingJson);
		}
	}
	
	protected Map<String, Integer> interpretJsonString(String receivingJson) {
		JSONObject root = new JSONObject(receivingJson);
		JSONArray gameData = root.getJSONArray("notifications");
		Map<String, Integer> steamGames = new HashMap<String, Integer>();
		for (int i = 0; i < gameData.length(); i++) {
			JSONObject data = gameData.getJSONObject(i);
			
			String gameTitle = data.getString("title");
			int gameId = data.getInt("steamid");
			
			steamGames.put(gameTitle, gameId);
		}
		return steamGames;
	}
	
	protected String getJsonString() {
		JSONObject root = new JSONObject();
		root.put("type", "fetch_games");

		return root.toString();
		
	}

}
