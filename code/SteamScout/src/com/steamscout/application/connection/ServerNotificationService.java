package com.steamscout.application.connection;

import org.json.JSONArray;
import org.json.JSONObject;

import com.steamscout.application.connection.interfaces.NotificationService;
import com.steamscout.application.model.game_data.Game;
import com.steamscout.application.model.notification.Notification;
import com.steamscout.application.model.notification.NotificationList;
import com.steamscout.application.model.user.Credentials;


public class ServerNotificationService extends ServerService<NotificationList> implements NotificationService {
	
	private Credentials credentials;
	
	@Override
	public NotificationList UpdateNotifications(Credentials credentials) {
		this.credentials = credentials;
		return this.send();
	}

	@Override
	protected NotificationList interpretJsonString(String json) {
		JSONObject root = new JSONObject(json);
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

	@Override
	protected String getSendingJsonString() {
		JSONObject user = new JSONObject();
		user.put("username", this.credentials.getUsername());
		user.put("password", this.credentials.getPassword());

		JSONObject data = new JSONObject();
		data.put("user", user);

		JSONObject root = new JSONObject();
		root.put("type", "check_notifications");
		root.put("data", data);

		return root.toString();
	}
	
	protected void setCredentials(Credentials credentials) {
		this.credentials = credentials;
	}

}
