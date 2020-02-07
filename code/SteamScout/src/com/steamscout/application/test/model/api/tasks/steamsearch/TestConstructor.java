package com.steamscout.application.test.model.api.tasks.steamsearch;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.steamscout.application.model.api.tasks.SteamSearch;

public class TestConstructor {

	@Test
	public void testNotAllowNullTerm() {
		assertThrows(IllegalArgumentException.class, () -> new SteamSearch(null));
	}

}
