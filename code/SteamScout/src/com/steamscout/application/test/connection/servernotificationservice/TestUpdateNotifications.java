package com.steamscout.application.test.connection.servernotificationservice;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.steamscout.application.connection.ServerNotificationService;
import com.steamscout.application.model.notification.NotificationList;

public class TestUpdateNotifications {

	private class TestServerNotificationService extends ServerNotificationService {

		@Override
		protected NotificationList send() {
			return new NotificationList();
		}
		
	}
	
	@Test
	public void testUpdate() {
		TestServerNotificationService service = new TestServerNotificationService();
		NotificationList result = service.updateNotifications(null);
		assertEquals(0, result.size());
	}

}
