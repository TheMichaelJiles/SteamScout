package com.steamscout.application.test.model.autocomplete.trie;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.steamscout.application.model.autocomplete.trie.Trie;

public class TestConstructor {

	@Test
	public void testNotAllowInvalidMinimum() {
		assertThrows(IllegalArgumentException.class, () -> new Trie(0));
	}
	
	@Test
	public void testValidConstruction() {
		Trie trie = new Trie(1);
		assertEquals(1, trie.getMinimumNumberOfCharacters());
	}

}
