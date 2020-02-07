package com.steamscout.application.test.util.timelimitedaction;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.steamscout.application.util.TimeLimitedAction;

public class TestConstructor {

	@Test
	public void testNotAllowNullConsumer() {
		assertThrows(IllegalArgumentException.class, () -> new TimeLimitedAction(null, 3));
	}

	@Test
	public void testNotAllowZeroMillis() {
		assertThrows(IllegalArgumentException.class, () -> new TimeLimitedAction(time -> {
			
		}, 0));
	}
	
	@Test
	public void testNotAllowNegativeMillis() {
		assertThrows(IllegalArgumentException.class, () -> new TimeLimitedAction(time -> {
			
		}, -1));
	}
}
