package com.steamscout.application.test.view.viewmodel;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.steamscout.application.model.game_data.Game;
import com.steamscout.application.model.game_data.SteamGames;
import com.steamscout.application.view.ViewModel;

public class TestPerformSearch {

	private class TestSteamGames extends SteamGames {

		@Override
		public Collection<Game> getMatchingGames(String term) throws InterruptedException {
			throw new InterruptedException();
		}
		
	}
	
	private Map<String, Integer> data;
	
	@BeforeEach
	public void setUp() {
		this.data = new HashMap<String, Integer>();
		this.data.put("test0", 0);
		this.data.put("test1", 1);
		this.data.put("test2", 2);
		ViewModel vm = ViewModel.get();
		vm.setSteamGames(new SteamGames());
		vm.getSteamGames().initializeGames(data);
	}
	
	@AfterEach
	public void tearDown() {
		ViewModel.get().getSteamGames().clear();
		ViewModel.get().browsePageSearchTermProperty().setValue("");
		ViewModel.get().searchResultsProperty().clear();
	}
	
	@Test
	public void testOnInterrupted() {
		ViewModel.get().setSteamGames(new TestSteamGames());
		ViewModel.get().performSearch();
	}
	
	@Test
	public void testFoundResults() {
		ViewModel vm = ViewModel.get();
		vm.browsePageSearchTermProperty().setValue("test");
		vm.performSearch();
		
		assertEquals(3, vm.searchResultsProperty().getValue().size());
	}

	@Test
	public void testNoFoundResults() {
		ViewModel vm = ViewModel.get();
		vm.browsePageSearchTermProperty().setValue("zf");
		vm.performSearch();
		
		assertEquals(0, vm.searchResultsProperty().getValue().size());
	}
}
