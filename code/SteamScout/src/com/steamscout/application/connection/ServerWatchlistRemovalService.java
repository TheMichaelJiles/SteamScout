package com.steamscout.application.connection;

import org.json.JSONArray;
import org.json.JSONObject;
import org.zeromq.SocketType;
import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Context;
import org.zeromq.ZMQ.Socket;

import com.steamscout.application.connection.interfaces.WatchlistRemovalService;
import com.steamscout.application.model.game_data.Game;
import com.steamscout.application.model.game_data.Watchlist;
import com.steamscout.application.model.notification.NotificationCriteria;
import com.steamscout.application.model.user.Credentials;
import com.steamscout.application.model.user.User;

public class ServerWatchlistRemovalService implements WatchlistRemovalService {

	private static final String HOST_PORT_PAIR = "tcp://127.0.0.1:5555";

	@Override
	public Watchlist removeGameFromWatchlist(User currentUser, Game game) {
		try (Context context = ZMQ.context(1); Socket socket = context.socket(SocketType.REQ)) {
			socket.connect(HOST_PORT_PAIR);
			System.out.println("Initiating Watchlist Addition Service");
			
			Credentials credentials = currentUser.getCredentials();
			String sendingJson = this.getJsonString(credentials, game);
			socket.send(sendingJson.getBytes(ZMQ.CHARSET), 0);
			System.out.println("Sent the following json");
			System.out.println(new JSONObject(sendingJson).toString(4));

			byte[] serverResponseBytes = socket.recv(0);
			String receivingJson = new String(serverResponseBytes, ZMQ.CHARSET);
			System.out.println("Received the following json");
			System.out.println(new JSONObject(receivingJson).toString(4));

			return this.interpretJsonString(credentials, receivingJson);
		}
	}

	protected Watchlist interpretJsonString(Credentials credentials, String receivingJson) {
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

	protected String getJsonString(Credentials credentials, Game game) {
		JSONObject user = new JSONObject();
		user.put("username", credentials.getUsername());
		user.put("password", credentials.getPassword());

		JSONObject gameToAdd = new JSONObject();
		gameToAdd.put("data", game.getAppId());

		JSONObject data = new JSONObject();
		data.put("user", user);
		data.put("steamid", game.getAppId());

		JSONObject root = new JSONObject();
		root.put("type", "watchlist_removal");
		root.put("data", data);

		return root.toString();
	}

}
