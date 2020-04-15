package com.steamscout.application.test.model.autocomplete.titlepredictor;

import static org.junit.Assert.assertFalse;
import org.junit.jupiter.api.Test;

import com.steamscout.application.model.autocomplete.trie.TitlePredictor;

class TestConstructor {

	@Test
	void testValidConstructor() {
		TitlePredictor test = new TitlePredictor(1);
		
		assertFalse(test == null);
	}

}
