package com.steamscout.application.test.util.listutil;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.steamscout.application.util.ListUtil;

public class TestSplit {

	private List<Integer> list;
	
	@BeforeEach
	public void setUp() {
		this.list = new ArrayList<Integer>();
		this.list.add(0);
		this.list.add(1);
		this.list.add(2);
		this.list.add(3);
		this.list.add(4);
		this.list.add(5);
		this.list.add(6);
		this.list.add(7);
	}
	
	@Test
	public void testNotAllowNullList() {
		assertThrows(IllegalArgumentException.class, () -> ListUtil.split(null, 1));
	}

	@Test
	public void testNotAllowNegativeNumberOfSplits() {
		assertThrows(IllegalArgumentException.class, () -> ListUtil.split(this.list, -1));
	}
	
	@Test
	public void testZeroSplits() {
		List<List<Integer>> result = ListUtil.split(this.list, 0);
		
		assertAll(() -> assertEquals(1, result.size()),
				() -> assertEquals(true, result.get(0).containsAll(this.list)));
	}
	
	@Test
	public void testOneSplits() {
		List<List<Integer>> result = ListUtil.split(this.list, 1);
		List<Integer> firstHalf = result.get(0);
		List<Integer> secondHalf = result.get(1);
		assertAll(() -> assertEquals(2, result.size()),
				() -> assertEquals(4, firstHalf.size()),
				() -> assertEquals(0, firstHalf.get(0)),
				() -> assertEquals(1, firstHalf.get(1)),
				() -> assertEquals(2, firstHalf.get(2)),
				() -> assertEquals(3, firstHalf.get(3)),
				() -> assertEquals(4, secondHalf.size()),
				() -> assertEquals(4, secondHalf.get(0)),
				() -> assertEquals(5, secondHalf.get(1)),
				() -> assertEquals(6, secondHalf.get(2)),
				() -> assertEquals(7, secondHalf.get(3)));
	}
	
	@Test
	public void testTwoSplits() {
		List<List<Integer>> result = ListUtil.split(this.list, 2);
		List<Integer> firstQuarter = result.get(0);
		List<Integer> secondQuarter = result.get(1);
		List<Integer> thirdQuarter = result.get(2);
		List<Integer> fourthQuarter = result.get(3);
		
		assertAll(() -> assertEquals(4, result.size()),
				() -> assertEquals(2, firstQuarter.size()),
				() -> assertEquals(2, secondQuarter.size()),
				() -> assertEquals(2, thirdQuarter.size()),
				() -> assertEquals(2, fourthQuarter.size()));
	}
}
