package com.steamscout.application.view.autotextfield;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * Prediction text field that includes a context
 * menu that appears showing predictions.
 * 
 * @author Luke Whaley
 *
 */
public class PredictionTextField extends TextField {

	public static final int MINIMUM_NUMBER_MENU_ENTRIES = 1;
	
	private ContextMenu menu;
	private Function<String, List<String>> getPredictions;
	private int numberOfMenuEntries;
	
	/**
	 * Creates a new prediction text field with the specified prediction function
	 * and number of menu entries. The prediction function is used by this text
	 * field to get predictions when textProperty() is changed. The number of menu
	 * entries is the maximum number of entries shown in the context menu.
	 * 
	 * @precondition getPredictions != null && numberOfMenuEntries >= PredictionTextField.MINIMUM_NUMBER_MENU_ENTRIES
	 * @postcondition none
	 * 
	 * @param getPredictions the function that gets the predictions.
	 * @param numberOfMenuEntries the maximum number of predictions to show in the context menu.
	 */
	public PredictionTextField(Function<String, List<String>> getPredictions, int numberOfMenuEntries) {
		super();
		if (getPredictions == null) {
			throw new IllegalArgumentException("getPredictions consumer should not be null.");
		}
		if (numberOfMenuEntries < MINIMUM_NUMBER_MENU_ENTRIES) {
			throw new IllegalArgumentException("number of menu entries must be at least " + MINIMUM_NUMBER_MENU_ENTRIES);
		}
		this.menu = new ContextMenu();
		this.getPredictions = getPredictions;
		this.numberOfMenuEntries = numberOfMenuEntries;
		this.setListener();
	}
	
	private void setListener() {
		this.textProperty().addListener((observable, oldValue, newValue) -> {
			List<String> results = this.getPredictions.apply(newValue);
			Collections.sort(results);
			if (!results.isEmpty()) {
				this.populatePredictions(results);
			    if (!this.menu.isShowing()) {
			    	this.menu.show(PredictionTextField.this, Side.BOTTOM, 0, 0);
			    }
			} else {
				this.menu.hide();
			}
		});
	}
	
	private void populatePredictions(List<String> results) {
		List<CustomMenuItem> items = new ArrayList<CustomMenuItem>();
		int count = Math.min(results.size(), this.numberOfMenuEntries);
		for (int i = 0; i < count; i++) {
			String result = results.get(i);
			Label itemLabel = new Label(result);
			CustomMenuItem menuItem = new CustomMenuItem(itemLabel, true);
			
			menuItem.setOnAction(event -> {
				this.textProperty().setValue(result);
				this.positionCaret(result.length());
			});
			
			items.add(menuItem);
		}
		
		this.menu.getItems().clear();
		this.menu.getItems().addAll(items);
	}
}
