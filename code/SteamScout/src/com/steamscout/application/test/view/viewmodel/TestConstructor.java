package com.steamscout.application.test.view.viewmodel;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.steamscout.application.view.ViewModel;

public class TestConstructor {

	@Test
	public void testPostconditions() {
		ViewModel vm = ViewModel.get();
		
		assertAll(() -> assertEquals(null, vm.userProperty().getValue()),
				() -> assertEquals(0, vm.notificationsProperty().getValue().size()),
				() -> assertEquals(0, vm.watchlistProperty().getValue().size()),
				() -> assertEquals(0, vm.searchResultsProperty().getValue().size()),
				() -> assertEquals("", vm.browsePageSearchTermProperty().getValue()),
				() -> assertEquals(null, vm.browsePageSelectedGameProperty().getValue()),
				() -> assertNotEquals(null, vm.getSteamGames()),
				() -> assertEquals("", vm.loginPageUsernameProperty().getValue()),
				() -> assertEquals("", vm.loginPagePasswordProperty().getValue()),
				() -> assertEquals(null, vm.loginPageErrorProperty().getValue()));
	}

}
