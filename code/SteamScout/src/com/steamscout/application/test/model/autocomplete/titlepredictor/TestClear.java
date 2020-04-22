package com.steamscout.application.test.model.autocomplete.titlepredictor;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.steamscout.application.model.autocomplete.trie.TitlePredictor;

class TestClear {

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
	public void testClearRemovesAll() {
		this.predictor.populate(this.words);
		this.predictor.clear();
		
		List<String> cresults = this.predictor.predict("c ");
		List<String> dresults = this.predictor.predict(" D");
		List<String> tresults = this.predictor.predict("t    ");
		
		assertAll(() -> assertEquals(false, cresults.contains("Chicken")),
				() -> assertEquals(false, dresults.contains("DONKEY")),
				() -> assertEquals(false, dresults.contains("dolphin")),
				() -> assertEquals(false, dresults.contains("doLphinDiver")),
				() -> assertEquals(false, tresults.contains("turkey")),
				() -> assertEquals(false, tresults.contains("Turkey Muffin")));
	}

}
