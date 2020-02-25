package com.steamscout.application.util;

/**
 * Controls how many threads are used in
 * the ParallelIterable class.
 * 
 * @author Thomas Whaley
 *
 */
public enum IterationSpeed {

	/**
	 * Causes the ParallelIterable to use at most 2 threads.
	 */
	LOW,
	
	/**
	 * Causes the ParallelIterable to use at most 4 threads.
	 */
	MEDIUM, 
	
	/**
	 * Causes the ParallelIterable to use at most 8 threads.
	 */
	HIGH,
	
	/**
	 * Causes the ParallelIterable to use at most only one additional thread.
	 * This will most likely not increase performance.
	 */
	MINIMAL;
}
