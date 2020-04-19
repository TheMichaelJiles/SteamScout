package com.steamscout.application.test.view.viewmodel;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.steamscout.application.view.ViewModel;

public class TestFetchWatchlistFor {

	@Test
	public void testNullUsernameException() {
		assertThrows(IllegalArgumentException.class, () -> ViewModel.get().fetchWatchlistFor(null, null));
	}

}
