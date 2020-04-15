package com.steamscout.application.test.model.autocomplete.titlepredictor;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.steamscout.application.model.autocomplete.trie.TitlePredictor;

class TestRemove {

	private Collection<String> words;
	private TitlePredictor predictor;
	
	@BeforeEach
	public void setUp() {
		this.predictor = new TitlePredictor(1);
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
		this.predictor.populate(this.words);
		this.predictor.remove(null);
		
		List<String> cresults = this.predictor.predict("c ");
		List<String> dresults = this.predictor.predict(" D");
		List<String> tresults = this.predictor.predict("t    ");
		
		assertAll(() -> assertEquals(true, cresults.contains("Chicken")),
				() -> assertEquals(true, dresults.contains("DONKEY")),
				() -> assertEquals(true, dresults.contains("dolphin")),
				() -> assertEquals(true, dresults.contains("doLphinDiver")),
				() -> assertEquals(true, tresults.contains("turkey")),
				() -> assertEquals(true, tresults.contains("Turkey Muffin")));
	}
	
	@Test
	public void testRemovesNothingIfNotContained() {
		this.predictor.populate(this.words);
		this.predictor.remove("cdtzap");
		
		List<String> cresults = this.predictor.predict("c ");
		List<String> dresults = this.predictor.predict(" D");
		List<String> tresults = this.predictor.predict("t    ");
		
		assertAll(() -> assertEquals(true, cresults.contains("Chicken")),
				() -> assertEquals(true, dresults.contains("DONKEY")),
				() -> assertEquals(true, dresults.contains("dolphin")),
				() -> assertEquals(true, dresults.contains("doLphinDiver")),
				() -> assertEquals(true, tresults.contains("turkey")),
				() -> assertEquals(true, tresults.contains("Turkey Muffin")));
	}

	@Test
	public void testRemovesCorrectly() {
		this.predictor.populate(this.words);
		this.predictor.remove("doLphinDiver");
		
		List<String> cresults = this.predictor.predict("c ");
		List<String> dresults = this.predictor.predict(" D");
		List<String> tresults = this.predictor.predict("t    ");
		
		assertAll(() -> assertEquals(true, cresults.contains("Chicken")),
				() -> assertEquals(true, dresults.contains("DONKEY")),
				() -> assertEquals(true, dresults.contains("dolphin")),
				() -> assertEquals(false, dresults.contains("doLphinDiver")),
				() -> assertEquals(true, tresults.contains("turkey")),
				() -> assertEquals(true, tresults.contains("Turkey Muffin")));
	}
	
	@Test
	public void testRemovesMultipleCorrectly() {
		this.predictor.populate(this.words);
		this.predictor.remove("dolphindiver");
		this.predictor.remove("donkey");
		
		List<String> cresults = this.predictor.predict("c ");
		List<String> dresults = this.predictor.predict(" D");
		List<String> tresults = this.predictor.predict("t    ");
		
		assertAll(() -> assertEquals(true, cresults.contains("Chicken")),
				() -> assertEquals(false, dresults.contains("DONKEY")),
				() -> assertEquals(true, dresults.contains("dolphin")),
				() -> assertEquals(false, dresults.contains("doLphinDiver")),
				() -> assertEquals(true, tresults.contains("turkey")),
				() -> assertEquals(true, tresults.contains("Turkey Muffin")));
	}

}
