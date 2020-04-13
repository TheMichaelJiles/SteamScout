package com.steamscout.application.model.autocomplete.trie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * Tree Data Structure that is for fast auto-predict
 * operations.
 * 
 * @author Luke Whaley
 *
 */
public class Trie {

	public static final int MINIMUM_CHAR_LIMIT = 1;
	
	private Node root;
	private int minimumNumberOfCharacters;
	
	/**
	 * Constructs a new trie with the specified minimum number of characters.
	 * This minimum is the minimum number of characters required by a given search term
	 * to the predict(String) method for it to return anything other than a non-empty list.
	 * 
	 * @precondition minimumNumberOfCharacters >= Trie.MINIMUM_CHAR_LIMIT
	 * @postcondition none
	 * 
	 * @param minimumNumberOfCharacters the minimum number of characters that must be given to
	 * the predict(String) method before it will return anything other than a non-empty list.
	 */
	public Trie(int minimumNumberOfCharacters) {
		if (minimumNumberOfCharacters < MINIMUM_CHAR_LIMIT) {
			throw new IllegalArgumentException("minimum number of characters must be at least " + MINIMUM_CHAR_LIMIT);
		}
		
		this.clear();
		this.minimumNumberOfCharacters = minimumNumberOfCharacters;
	}
	
	/**
	 * Clears this trie of all populations.
	 * 
	 * @precondition none
	 * @postcondition predict will return empty list regardless of argument.
	 */
	public void clear() {
		this.root = new Node();
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
		return this.minimumNumberOfCharacters;
	}
	
	/**
	 * Gets all words stored by this trie that start with the given
	 * term. If the given term is null, has length less than the minimum
	 * number of characters, or no words were found in the trie, then the
	 * returned list will be empty.
	 * 
	 * @precondition term != null && term.strip().length() >= getMinimumOfCharacters()
	 * @postcondition none
	 * 
	 * @param term the search prefix.
	 * @return list of all words stored by this trie that start with the given term.
	 */
	public List<String> predict(String term) {
		List<String> results = new ArrayList<String>();
		if (term == null) {
			return results;
		}
		String formattedTerm = term.strip().toLowerCase();
		if (formattedTerm.length() < this.minimumNumberOfCharacters) {
			return results;
		}
		
		Queue<Character> characters = this.toCharacterQueue(formattedTerm);
		Node lastCharacterNode = this.getLastCharacterNode(this.root, characters);
		if (lastCharacterNode != null) {
			List<String> fragments = this.getChildrenFragments(lastCharacterNode);
			for (String fragment : fragments) {
				results.add(formattedTerm.substring(0, formattedTerm.length() - 1) + fragment);
			}
		}
		return results;
	}
	
	private Node getLastCharacterNode(Node currentNode, Queue<Character> characters) {
		if (characters.isEmpty()) {
			return currentNode;
		}
		
		Character nextCharacter = characters.remove();
		if (!currentNode.children.containsKey(nextCharacter)) {
			return null;
		}
		Node nextNode = currentNode.children.get(nextCharacter);
		return this.getLastCharacterNode(nextNode, characters);
	}
	
	private List<String> getChildrenFragments(Node currentNode) {
		List<String> fragments = new ArrayList<String>();
		if (currentNode.value == null) {
			fragments.add("");
			return fragments;
		}
		
		for (Node child : currentNode.children.values()) {
			List<String> childFragments = this.getChildrenFragments(child);
			for (String fragment : childFragments) {
				fragments.add(currentNode.value + fragment);
			}
		}
		return fragments;
	}
	
	/**
	 * Populates this trie with the specified words. Null values
	 * are ignored. Each word is stripped of leading and trailing
	 * whitespace as well as converted to lower case.
	 * 
	 * @precondition words != null
	 * @postcondition if word in words and word >= getMinimumNumberOfCharacters(),
	 * 				  then predict(word).contains(word)
	 * @param words the words to populate this trie with.
	 */
	public void populate(Iterable<String> words) {
		if (words == null) {
			throw new IllegalArgumentException("words should not be null.");
		}
		
		for (String word : words) {
			this.populateWord(word);
		}
	}
	
	/**
	 * Adds a singular word to this trie. Null value is ignored.
	 * Word is stripped of leading and trailing whitespace as well
	 * as converted to lower case.
	 * 
	 * @precondition none
	 * @postcondition if word >= getMinimumNumberOfCharacters(), then
	 * 				  predict(word).contains(word)
	 * @param word the word to add to the trie.
	 */
	public void populateWord(String word) {
		this.add(word);
	}
	
	private void add(String word) {
		if (word == null) {
			return;
		}
		
		Queue<Character> characters = this.toCharacterQueue(word.toLowerCase().strip());
		this.addCharacterNodes(this.root, characters);
	}
	
	private void addCharacterNodes(Node currentNode, Queue<Character> characters) {
		if (characters.isEmpty()) {
			currentNode.children.put(null, new Node());
			return;
		}
		
		Character nextCharacter = characters.remove();
		if (!currentNode.children.containsKey(nextCharacter)) {
			currentNode.children.put(nextCharacter, new Node(nextCharacter));
		}
		Node nextNode = currentNode.children.get(nextCharacter);
		this.addCharacterNodes(nextNode, characters);
	}
	
	/**
	 * Removes the specified word from this trie if it exists.
	 * 
	 * @precondition none
	 * @postcondition !predict(word).contains(word)
	 * 
	 * @param word the word to remove from this trie.
	 */
	public void remove(String word) {
		if (word == null) {
			return;
		}
		
		Queue<Character> characters = this.toCharacterQueue(word.strip().toLowerCase());
		this.remove(this.root, characters);
	}
	
	private void remove(Node currentNode, Queue<Character> characters) {
		if (characters.isEmpty()) {
			currentNode.children.remove(null);
			return;
		}
		
		Character nextCharacter = characters.remove();
		if (currentNode.children.containsKey(nextCharacter)) {
			Node nextNode = currentNode.children.get(nextCharacter);
			this.remove(nextNode, characters);
			if (nextNode.children.size() == 0) {
				currentNode.children.remove(nextNode.value);
			}
		}
	}
	
	private Queue<Character> toCharacterQueue(String word) {
		Queue<Character> characterQueue = new LinkedList<Character>();
		for (char character : word.toCharArray()) {
			characterQueue.add(character);
		}
		return characterQueue;
	}
	
	private final class Node {
		
		private Character value;
		private Map<Character, Node> children;
		
		private Node(Character value) {
			this.value = value;
			this.children = new HashMap<Character, Node>();
		}
		
		private Node() {
			this(null);
		}
	}
}
