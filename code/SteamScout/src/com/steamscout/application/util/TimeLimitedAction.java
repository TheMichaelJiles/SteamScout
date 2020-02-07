package com.steamscout.application.util;

import java.util.function.LongConsumer;

import javafx.animation.AnimationTimer;

/**
 * Has the capability to perform a specified action repeatedly
 * on interval after a specified number of milliseconds.
 * 
 * @author Thomas Whaley
 *
 */
public class TimeLimitedAction extends AnimationTimer {

	private LongConsumer action;
	private long timeCounter;
	private long millis;
	
	/**
	 * Creates a new TimeLimitedAction object with the specified action and timeframe.
	 * 
	 * @precondition action != null && millis > 0
	 * @postcondition none
	 * 
	 * @param action the action to perform on interval.
	 * @param millis the number of milliseconds at which the interval is concluded.
	 */
	public TimeLimitedAction(LongConsumer action, long millis) {
		if (action == null) {
			throw new IllegalArgumentException("action should not be null.");
		}
		if (millis <= 0) {
			throw new IllegalArgumentException("millis should be greater than zero.");
		}
		
		this.action = action;
		this.millis = millis;
	}
	
	@Override
	public void start() {
		this.timeCounter = System.currentTimeMillis();
		super.start();
	}

	@Override
	public void handle(long arg0) {
		if (this.hasEnoughTimeElapsed()) {
			this.timeCounter = System.currentTimeMillis();
			this.action.accept(this.timeCounter);
		}
	}
	
	private boolean hasEnoughTimeElapsed() {
		long currentTime = System.currentTimeMillis();
		return currentTime - this.timeCounter >= this.millis;
	}
}
