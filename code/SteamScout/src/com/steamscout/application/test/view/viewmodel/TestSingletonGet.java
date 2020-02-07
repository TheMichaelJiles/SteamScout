package com.steamscout.application.test.view.viewmodel;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.steamscout.application.view.ViewModel;

public class TestSingletonGet {

	@Test
	public void testSameObjectReturned() {
		ViewModel vm = ViewModel.get();
		ViewModel secondVm = ViewModel.get();
		
		assertEquals(true, vm.equals(secondVm));
	}

}
