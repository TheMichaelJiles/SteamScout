package com.steamscout.application.test.model.autocomplete.trie;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.steamscout.application.model.autocomplete.trie.Trie;

public class TestPredict {

	private Collection<String> words;
	private Trie trie;
	
	@BeforeEach
	public void setUp() {
		this.trie = new Trie(Trie.MINIMUM_CHAR_LIMIT);
		this.words = new ArrayList<String>();
		this.words.add("Chicken");
		this.words.add("DONKEY");
		this.words.add("turkey");
		this.words.add("Turkey Muffin");
		this.words.add("    dolphin");
		this.words.add("doLphinDiver");
	}
	
	@Test
	public void testReturnsEmptyOnNullSearchTerm() {
		this.trie.populate(this.words);
		List<String> results = this.trie.predict(null);
		assertEquals(true, results.isEmpty());
	}

	@Test
	public void testReturnsEmptyOnShortSearchTerm() {
		this.trie.populate(this.words);
		List<String> results = this.trie.predict("");
		assertEquals(true, results.isEmpty());
	}
	
	@Test
	public void testNothingFound() {
		this.trie.populate(this.words);
		List<String> results = this.trie.predict("zwesf");
		assertEquals(true, results.isEmpty());
	}
}
