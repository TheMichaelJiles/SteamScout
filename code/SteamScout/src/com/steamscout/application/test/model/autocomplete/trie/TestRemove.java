package com.steamscout.application.test.model.autocomplete.trie;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.steamscout.application.model.autocomplete.trie.Trie;

public class TestRemove {

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
	public void testRemovesNothingOnNull() {
		this.trie.populate(this.words);
		this.trie.remove(null);
		
		List<String> cresults = this.trie.predict("c ");
		List<String> dresults = this.trie.predict(" D");
		List<String> tresults = this.trie.predict("t    ");
		
		assertAll(() -> assertEquals(true, cresults.contains("chicken")),
				() -> assertEquals(true, dresults.contains("donkey")),
				() -> assertEquals(true, dresults.contains("dolphin")),
				() -> assertEquals(true, dresults.contains("dolphindiver")),
				() -> assertEquals(true, tresults.contains("turkey")),
				() -> assertEquals(true, tresults.contains("turkey muffin")));
	}
	
	@Test
	public void testRemovesNothingIfNotContained() {
		this.trie.populate(this.words);
		this.trie.remove("cdtzap");
		
		List<String> cresults = this.trie.predict("c ");
		List<String> dresults = this.trie.predict(" D");
		List<String> tresults = this.trie.predict("t    ");
		
		assertAll(() -> assertEquals(true, cresults.contains("chicken")),
				() -> assertEquals(true, dresults.contains("donkey")),
				() -> assertEquals(true, dresults.contains("dolphin")),
				() -> assertEquals(true, dresults.contains("dolphindiver")),
				() -> assertEquals(true, tresults.contains("turkey")),
				() -> assertEquals(true, tresults.contains("turkey muffin")));
	}

	@Test
	public void testRemovesCorrectly() {
		this.trie.populate(this.words);
		this.trie.remove("dolphindiver");
		
		List<String> cresults = this.trie.predict("c ");
		List<String> dresults = this.trie.predict(" D");
		List<String> tresults = this.trie.predict("t    ");
		
		assertAll(() -> assertEquals(true, cresults.contains("chicken")),
				() -> assertEquals(true, dresults.contains("donkey")),
				() -> assertEquals(true, dresults.contains("dolphin")),
				() -> assertEquals(false, dresults.contains("dolphindiver")),
				() -> assertEquals(true, tresults.contains("turkey")),
				() -> assertEquals(true, tresults.contains("turkey muffin")));
	}
	
	@Test
	public void testRemovesMultipleCorrectly() {
		this.trie.populate(this.words);
		this.trie.remove("dolphindiver");
		this.trie.remove("donkey");
		
		List<String> cresults = this.trie.predict("c ");
		List<String> dresults = this.trie.predict(" D");
		List<String> tresults = this.trie.predict("t    ");
		
		assertAll(() -> assertEquals(true, cresults.contains("chicken")),
				() -> assertEquals(false, dresults.contains("donkey")),
				() -> assertEquals(true, dresults.contains("dolphin")),
				() -> assertEquals(false, dresults.contains("dolphindiver")),
				() -> assertEquals(true, tresults.contains("turkey")),
				() -> assertEquals(true, tresults.contains("turkey muffin")));
	}
}
