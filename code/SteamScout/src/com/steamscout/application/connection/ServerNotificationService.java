/**
 * 
 */
package com.steamscout.application.connection;

import org.json.JSONArray;
import org.json.JSONObject;
import org.zeromq.SocketType;
import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Context;
import org.zeromq.ZMQ.Socket;

import com.steamscout.application.connection.interfaces.NotificationService;
import com.steamscout.application.model.game_data.Game;
import com.steamscout.application.model.notification.Notification;
import com.steamscout.application.model.notification.NotificationList;
import com.steamscout.application.model.user.Credentials;


public class ServerNotificationService implements NotificationService {
	
private static final String HOST_PORT_PAIR = "tcp://127.0.0.1:5555";
	
	@Override
	public NotificationList UpdateNotifications(Credentials credentials) {
		try (Context context = ZMQ.context(1); Socket socket = context.socket(SocketType.REQ)) {
			socket.connect(HOST_PORT_PAIR);
			System.out.println("Initiating Notification Service");
			
			String sendingJson = this.getJsonString(credentials);
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
	
	protected NotificationList interpretJsonString(Credentials credentials, String receivingJson) {
		JSONObject root = new JSONObject(receivingJson);
		JSONArray notificationData = root.getJSONArray("notifications");
		NotificationList notifications = new NotificationList();
		for (int i = 0; i < notificationData.length(); i++) {
			JSONObject data = notificationData.getJSONObject(i);

			Game game = new Game(data.getInt("steamid"), data.getString("title"));
			game.setCurrentPrice(data.getDouble("actualprice"));
			game.setInitialPrice(data.getDouble("initialprice"));
			game.setOnSale(data.getBoolean("onsale"));
			
			Notification notification = new Notification(game);
			notifications.add(notification);
		}
		return notifications;
	}
	
	protected String getJsonString(Credentials credentials) {
		JSONObject user = new JSONObject();
		user.put("username", credentials.getUsername());
		user.put("password", credentials.getPassword());

		JSONObject data = new JSONObject();
		data.put("user", user);

		JSONObject root = new JSONObject();
		root.put("type", "check_notifications");
		root.put("data", data);

		return root.toString();
	}

}
