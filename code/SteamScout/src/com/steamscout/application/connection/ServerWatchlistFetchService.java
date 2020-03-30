package com.steamscout.application.connection;

import org.json.JSONArray;
import org.json.JSONObject;
import org.zeromq.SocketType;
import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Context;
import org.zeromq.ZMQ.Socket;

import com.steamscout.application.connection.interfaces.WatchlistFetchService;
import com.steamscout.application.model.game_data.Game;
import com.steamscout.application.model.game_data.Watchlist;
import com.steamscout.application.model.notification.NotificationCriteria;

public class ServerWatchlistFetchService implements WatchlistFetchService {

	private static final String HOST_PORT_PAIR = "tcp://127.0.0.1:5555";
	
	@Override
	public Watchlist fetchWatchlist(String username) {
		try (Context context = ZMQ.context(1);
				Socket socket = context.socket(SocketType.REQ)) {
			socket.connect(HOST_PORT_PAIR);
			System.out.println("Initiating Server Login Service");
			
			String sendingJson = this.getJsonString(username);
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

	protected Watchlist interpretJsonString(String receivingJson) {
		JSONObject root = new JSONObject(receivingJson);
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
	}
	
	protected String getJsonString(String username) {
		JSONObject user = new JSONObject();
		user.put("username", username);
		
		JSONObject data = new JSONObject();
		data.put("user", user);
		
		JSONObject root = new JSONObject();
		root.put("type", "fetch_watchlist");
		root.put("data", data);
		
		return root.toString();
	}
}
