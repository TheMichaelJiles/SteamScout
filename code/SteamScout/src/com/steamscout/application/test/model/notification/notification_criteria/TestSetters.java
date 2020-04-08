package com.steamscout.application.test.model.notification.notification_criteria;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.steamscout.application.model.notification.NotificationCriteria;

public class TestSetters {

	private NotificationCriteria criteria;
	
	@BeforeEach
	public void setUp() {
		this.criteria = new NotificationCriteria();
	}
	
	@Test
	public void testNotAllowNegativeTargetPrice() {
		assertThrows(IllegalArgumentException.class, () -> this.criteria.setTargetPrice(-0.001));
	}

	@Test
	public void testShouldNotifyWhenBelowTargetPrice() {
		this.criteria.shouldNotifyWhenBelowTargetPrice(true);
		
		assertAll(() -> assertEquals(true, this.criteria.shouldNotifyWhenBelowTargetPrice()),
				() -> assertEquals(false, this.criteria.isDefault()));
	}
	
	@Test
	public void testShouldNotifyOnSale() {
		this.criteria.shouldNotifyOnSale(true);
		
		assertAll(() -> assertEquals(true, this.criteria.shouldNotifyOnSale()),
				() -> assertEquals(false, this.criteria.isDefault()));
	}
	
	@Test
	public void testSetTargetPrice() {
		this.criteria.setTargetPrice(5.99);
		
		assertAll(() -> assertEquals(5.99, this.criteria.getTargetPrice(), 0.0000001),
				() -> assertEquals(false, this.criteria.isDefault()));
	}
}
