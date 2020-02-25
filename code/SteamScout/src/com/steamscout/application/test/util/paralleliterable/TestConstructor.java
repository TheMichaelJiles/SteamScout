package com.steamscout.application.test.util.paralleliterable;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import com.steamscout.application.util.IterationSpeed;
import com.steamscout.application.util.ParallelIterable;

public class TestConstructor {

	@Test
	public void testNotAllowNullCollection() {
		assertThrows(IllegalArgumentException.class, () -> new ParallelIterable<Integer>(null, IterationSpeed.LOW));
	}

	@Test
	public void testNotAllowNullSpeed() {
		assertThrows(IllegalArgumentException.class, () -> new ParallelIterable<Integer>(new ArrayList<Integer>(), null));
	}
}
