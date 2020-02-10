package com.steamscout.application.test.view.viewmodel;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.steamscout.application.view.ViewModel;

public class TestPerformSearch {

	@BeforeEach
	public void setUp() {
		Map<String, Integer> data = new HashMap<String, Integer>();
		data.put("test0", 0);
		data.put("test1", 1);
		data.put("test2", 2);
		ViewModel vm = ViewModel.get();
		vm.getSteamGames().initializeGames(data);
	}
	
	@AfterEach
	public void tearDown() {
		ViewModel.get().getSteamGames().clear();
		ViewModel.get().browsePageSearchTermProperty().setValue("");
		ViewModel.get().searchResultsProperty().clear();
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
