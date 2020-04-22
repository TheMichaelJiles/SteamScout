package com.steamscout.application.test.model.autocomplete.titlepredictor;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.steamscout.application.model.autocomplete.trie.TitlePredictor;

class TestPredict {

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
	public void testReturnsEmptyOnNullSearchTerm() {
		this.predictor.populate(this.words);
		List<String> results = this.predictor.predict(null);
		assertEquals(true, results.isEmpty());
	}

	@Test
	public void testReturnsEmptyOnShortSearchTerm() {
		this.predictor.populate(this.words);
		List<String> results = this.predictor.predict("");
		assertEquals(true, results.isEmpty());
	}
	
	@Test
	public void testNothingFound() {
		this.predictor.populate(this.words);
		List<String> results = this.predictor.predict("zwesf");
		assertEquals(true, results.isEmpty());
	}

}
