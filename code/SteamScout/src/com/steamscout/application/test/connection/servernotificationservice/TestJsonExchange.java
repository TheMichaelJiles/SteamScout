package com.steamscout.application.test.connection.servernotificationservice;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.json.JSONObject;
import org.junit.Test;

import com.steamscout.application.connection.ServerNotificationService;
import com.steamscout.application.model.notification.NotificationList;
import com.steamscout.application.model.user.Credentials;

public class TestJsonExchange {

	private class TestServerNotificationService extends ServerNotificationService {
		
		public NotificationList interpretJsonString(String receivingJson) {
			return super.interpretJsonString(receivingJson);
		}

		public String getJsonString() {
			super.setCredentials(new Credentials("twhal", "1234"));
			return super.getSendingJsonString();
		}

	}

	@Test
	public void testNotificationCreatedWhenGivenValidJson() {
		TestServerNotificationService testService = new TestServerNotificationService();
		String jsonString = "{\"notifications\": [{\"steamid\": 5, \"title\": \"test-game\", \"actualprice\": 39.99, \"initialprice\": 59.99, \"onsale\": true}]}";
		NotificationList notifications = testService.interpretJsonString(jsonString);
		
		assertEquals(notifications.size(), 1);

	}
	
	@Test
	public void testFormsValidJsonToSendToServer() {
		TestServerNotificationService service = new TestServerNotificationService();
		String json = service.getJsonString();
		JSONObject jsonobj = new JSONObject(json);
		
		assertAll(() -> assertEquals("check_notifications", jsonobj.getString("type")),
				() -> assertEquals("twhal", jsonobj.getJSONObject("data").getJSONObject("user").getString("username")),
				() -> assertEquals("1234", jsonobj.getJSONObject("data").getJSONObject("user").getString("password")));
	}
}
