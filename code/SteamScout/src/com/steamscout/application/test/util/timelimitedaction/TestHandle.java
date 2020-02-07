package com.steamscout.application.test.util.timelimitedaction;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.steamscout.application.util.TimeLimitedAction;

public class TestHandle {
	
	private boolean indicator;
	
	@BeforeEach
	public void setUp() {
		this.indicator = false;
	}
	
	@Test
	public void testAfterTime() {
		TimeLimitedAction action = new TimeLimitedAction(timer -> {
			this.indicator = true;
		}, 1);
		action.handle(0);
		
		assertEquals(true, this.indicator);
	}

	@Test
	public void testBeforeTime() {
		TimeLimitedAction action = new TimeLimitedAction(timer -> {
			this.indicator = true;
		}, Long.MAX_VALUE);
		action.handle(0);
		
		assertEquals(false, this.indicator);
	}
}
