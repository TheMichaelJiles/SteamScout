package com.steamscout.application.test.util.paralleliterable;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.steamscout.application.util.ParallelIterable;

public class TestConstructor {

	@Test
	public void testNotAllowNullCollection() {
		assertThrows(IllegalArgumentException.class, () -> new ParallelIterable<Integer>(null));
	}

}
