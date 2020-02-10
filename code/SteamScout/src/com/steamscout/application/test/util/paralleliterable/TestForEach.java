package com.steamscout.application.test.util.paralleliterable;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.steamscout.application.util.ParallelIterable;

public class TestForEach {

	private ParallelIterable<Integer> numbers;
	private int sum;
	
	@BeforeEach
	public void setUp() {
		Collection<Integer> theNums = new ArrayList<Integer>();
		theNums.add(0);
		theNums.add(1);
		theNums.add(2);
		theNums.add(3);
		this.numbers = new ParallelIterable<Integer>(theNums);
		this.sum = 0;
	}
	
	@Test
	public void testNotAllowNullConsumer() {
		assertThrows(IllegalArgumentException.class, () -> this.numbers.forEach(null));
	}

	@Test
	public void testIterates() throws InterruptedException {
		this.numbers.forEach(number -> this.sum += number);
		assertEquals(6, this.sum);
	}
}
