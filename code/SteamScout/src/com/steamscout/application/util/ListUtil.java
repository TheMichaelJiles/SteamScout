package com.steamscout.application.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains utility methods for list operations.
 * 
 * @author Thomas Whaley
 *
 */
public class ListUtil {

	/**
	 * Takes the specified list and splits it in half the specfied number of times.
	 * This is best explained with an example, Given the list = [0, 1, 2, 3, 4, 5, 6, 7].
	 * split(list, 0) == [[0, 1, 2, 3, 4, 5, 6, 7]], split(list, 1) == [[0, 1, 2, 3], [4, 5, 6, 7]] and
	 * split(list, 2) == [[0, 1], [2, 3], [4, 5], [6, 7]]. So with numberOfSplits == 1, this method splits
	 * the input list in half once. With numberOfSPlits == 2, this method splits the list in half once, then
	 * splits each half once. This pattern repeats with larger value of numberOfSplits.
	 * 
	 * @precondition list != null && numberOfSplits >= 0
	 * @postcondition split(list, n).size() <= 2 ^ numberOfSplits
	 * 
	 * @param <T> the value of elements in list
	 * @param list the list to split.
	 * @param numberOfSplits the number of times to split.
	 * @return a list of sublists.
	 */
	public static <T> List<List<T>> split(List<T> list, int numberOfSplits) {
		if (list == null) {
			throw new IllegalArgumentException("list should not be null.");
		}
		if (numberOfSplits < 0) {
			throw new IllegalArgumentException("numberOfSplits should be greater than or equal to zero.");
		}
		
		List<List<T>> sublists  = new ArrayList<List<T>>();
		if (numberOfSplits == 0) {
			if (!list.isEmpty()) {
				sublists.add(list);	
			}
		} else {
			List<List<T>> halves = splitInHalf(list);
			for (List<T> half : halves) {
				sublists.addAll(split(half, numberOfSplits - 1));
			}
		}
		
		return sublists;
	}
	
	private static <T> List<List<T>> splitInHalf(List<T> list) {
		List<List<T>> split  = new ArrayList<List<T>>();
		int currentSize = list.size();
		
		List<T> firstHalf = new ArrayList<T>(list.subList(0, (currentSize + 1) / 2));
		List<T> secondHalf = new ArrayList<T>(list.subList((currentSize + 1) / 2, currentSize));
		split.add(firstHalf);
		split.add(secondHalf);
		
		return split;
	}
}
