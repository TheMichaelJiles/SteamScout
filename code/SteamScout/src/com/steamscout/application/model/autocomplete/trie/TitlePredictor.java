package com.steamscout.application.model.autocomplete.trie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Wraps the Trie class that holds the auto predict data for
 * the steam scout search. Ensures the names are properly capitalized.
 * 
 * @author Nathan Lightholder and Luke Whaley
 */
public class TitlePredictor {
	
	public static final int MINIMUM_SEARCH_CHARACTERS = 3;
	
	private Map<String, String> wordMap; 
	private Trie trie;
	
	/**
	 * Constructs a new Title Predictor. Sets the minimum number of search characters. 
	 * This minimum is the minimum number of characters required by a given search term
	 * to the predict(String) method for it to return anything other than a non-empty list.
	 * Ensures proper case of words in the search.
	 * 
	 * @precondition none
	 * @postcondition none
	 */
	public TitlePredictor(int minimumNumberOfCharacters) {
		this.wordMap = new HashMap<String, String>();
		this.trie = new Trie(minimumNumberOfCharacters);
	}
	
	/**
	 * Clears this trie and map of all populations.
	 * 
	 * @precondition none
	 * @postcondition predict will return empty list regardless of argument.
	 */
	public void clear() {
		this.trie.clear();
		this.wordMap.clear();
	}
	
	/**
	 * Gets the minimum number of characters that must be given to the predict(String)
	 * method before it will return anything other than a non-empty list.
	 * 
	 * @precondition none
	 * @postcondition none
	 * 
	 * @return the minimum number of characters required for a prediction.
	 */
	public int getMinimumNumberOfCharacters() {
		return this.trie.getMinimumNumberOfCharacters();
	}
	
	/**
	 * Gets all words stored by this trie that start with the given
	 * term. If the given term is null, has length less than the minimum
	 * number of characters, or no words were found in the trie, then the
	 * returned list will be empty. Then maps the lower case word to the
	 * word with proper case.
	 * 
	 * @precondition none
	 * @postcondition none
	 * 
	 * @param term the search prefix.
	 * @return list of all words stored by this map that start with the given term
	 * 		   with the proper capitalization.
	 */
	public List<String> predict(String term) {
		List<String> properCase = new ArrayList<String>();
		List<String> lowerCase = this.trie.predict(term);
		
		for (String word : lowerCase) {
			properCase.add(this.wordMap.get(word));
		}
		
		return properCase;
	}
	
	/**
	 * Populates this trie and map with the specified words. Null values
	 * are ignored. Each word is stripped of leading and trailing
	 * whitespace as well as converted to lower case.
	 * 
	 * @precondition words != null
	 * @postcondition if word in words and word >= getMinimumNumberOfCharacters(),
	 * 				  then predict(word).contains(word)
	 * @param words the words to populate this trie and map with.
	 */
	public void populate(Iterable<String> words) {
		for (String word : words) {
			this.populateWord(word);
		}	
	}
	
	/**
	 * Adds a singular word to the trie and map. Null value is ignored.
	 * Word is stripped of leading and trailing whitespace as well
	 * as converted to lower case.
	 * 
	 * @precondition word != null
	 * @postcondition if word >= getMinimumNumberOfCharacters(), then
	 * 				  predict(word).contains(word)
	 * @param word the word to add to the trie and map.
	 */
	public void populateWord(String word) {
		this.trie.populateWord(word);
		
		if (word != null) {
			this.wordMap.put(word.toLowerCase().strip(), word.strip());
		}
		
	}
	
	/**
	 * Removes the specified word from this trie and map if it exists.
	 * 
	 * @precondition word != null
	 * @postcondition !predict(word).contains(word)
	 * 
	 * @param word the word to remove from this trie and map.
	 */
	public void remove(String word) {
		if (word != null) {
			this.trie.remove(word);
			this.wordMap.remove(word.toLowerCase().strip());
		}
	}

}
