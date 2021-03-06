package com.steamscout.application.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

/**
 * This class is designed to perform operations on
 * a given collection by parallelizing the process. 
 * At most, the iteration will utilize up to 8 threads.
 * This class is recommended when a user needs to perform
 * an operation on a large set of data. It is up to the user
 * to ensure proper synchronization of critical sections within
 * the forEach consumer.
 * 
 * @author Thomas Whaley
 *
 * @param <T> the type of element in the collection.
 */
public class ParallelIterable<T> {

	private List<List<T>> divisions;
	
	/**
	 * Creates a new ParallelIterable object using the specified collection.
	 * 
	 * @precondition collection != null && speed != null
	 * @postcondition none
	 * 
	 * @param collection the collection in which this parallelized iterable gets its data.
	 * @param speed correlates proportionally with the number of threads utilized.
	 */
	public ParallelIterable(Collection<T> collection, IterationSpeed speed) {
		if (collection == null) {
			throw new IllegalArgumentException("collection should not be null.");
		}
		if (speed == null) {
			throw new IllegalArgumentException("speed should not be null.");
		}
		
		this.divisions = ListUtil.split(new ArrayList<T>(collection), this.convertIterationSpeedToInt(speed));
	}
	
	/**
	 * Performs the specified operation on each element that was passed to this
	 * ParallelIterable's constructor. The operation is guaranteed to be performed
	 * on each element. This method splits the iteration into at most 8 threads to speed
	 * up the process.
	 * 
	 * @precondition operation != null
	 * @postcondition none
	 * 
	 * @param operation the operation to perform on each element.
	 * @throws InterruptedException if one of the threads is interrupted.
	 */
	public void forEach(Consumer<T> operation) throws InterruptedException {
		if (operation == null) {
			throw new IllegalArgumentException("operation should not be null.");
		}
		
		Thread[] threads = new Thread[this.divisions.size()];
		for (int i = 0; i < threads.length; i++) {
			List<T> currentList = this.divisions.get(i);
			threads[i] = new Thread(() -> {
				for (T element : currentList) {
					operation.accept(element);	
				}
			});
			threads[i].start();
		}
		for (Thread thread : threads) {
			thread.join();
		}
	}
	
	private int convertIterationSpeedToInt(IterationSpeed speed) {
		switch (speed) {
			case LOW:
				return 1;
			case MEDIUM:
				return 2;
			case HIGH:
				return 3;
			default:
				return 0;
		}
	}
}
