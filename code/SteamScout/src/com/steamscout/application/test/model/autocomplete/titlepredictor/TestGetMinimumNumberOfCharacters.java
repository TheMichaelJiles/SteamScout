package com.steamscout.application.test.model.autocomplete.titlepredictor;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.steamscout.application.model.autocomplete.trie.TitlePredictor;

class TestGetMinimumNumberOfCharacters {

	@Test
	void test() {
		TitlePredictor test = new TitlePredictor(1);
		
		assertEquals(test.getMinimumNumberOfCharacters(), 1);
	}

}
