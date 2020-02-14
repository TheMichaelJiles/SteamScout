package com.steamscout.application.test.model.notification.notification_criteria;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.steamscout.application.model.notification.NotificationCriteria;

public class TestConstructor {

	@Test
	public void testPostconditionsOnConstruction() {
		NotificationCriteria criteria = new NotificationCriteria();
		
		assertAll(() -> assertEquals(false, criteria.shouldNotifyOnSale()),
				() -> assertEquals(false, criteria.shouldNotifyWhenBelowTargetPrice()),
				() -> assertEquals(0, criteria.getTargetPrice(), 0.000001));
	}

}
