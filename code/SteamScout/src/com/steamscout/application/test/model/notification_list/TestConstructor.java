package com.steamscout.application.test.model.notification_list;

import static org.junit.Assert.assertTrue;
import org.junit.jupiter.api.Test;

import com.steamscout.application.model.notification.NotificationList;

public class TestConstructor {
	
	@Test
	public void testConstructorCreatesList() {
		NotificationList list = new NotificationList();
		
		assertTrue(list.isEmpty());
	}

}
