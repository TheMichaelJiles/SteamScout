package com.steamscout.application.view.code_behind;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import com.steamscout.application.connection.ServerWatchlistAdditionService;
import com.steamscout.application.model.game_data.Game;

import com.steamscout.application.util.PageConnectionUtility;

import com.steamscout.application.view.UIFilePaths;
import com.steamscout.application.view.ViewModel;
import com.steamscout.application.view.autotextfield.PredictionTextField;
import com.steamscout.application.view.game_listcell.GameListCell;

/**
 * Browsing page code behind that connects to ViewModel
 * @author TheMichaelJiles
 *
 */
public class BrowsingPageCodeBehind {

    private TextField searchBarTextField;

    @FXML
    private AnchorPane pane;
    
    @FXML
    private ListView<Game> gameResultsListView;

    @FXML
    private Button addButton;

    @FXML
    private Button searchButton;
    
    @FXML
    private Button clearSearchButton;
    
    @FXML
    private Label noResultsLabel;
    
    @FXML
    private Label gameAlreadyAddedLabel;
    
    @FXML
    private BorderPane browsingPageBorderPane;
    
    @FXML
    private void initialize() {
    	this.initializeTextField();
    	
    	this.gameResultsListView.setCellFactory(listView -> new GameListCell());
    	this.setUpBindings();
    	this.setUpNavigationBar();
    }
    
    private void initializeTextField() {
    	this.searchBarTextField = new PredictionTextField(term -> ViewModel.get().makeBrowsePagePrediction(term), 10);
    	this.searchBarTextField.setPrefWidth(193);
    	this.searchBarTextField.setPrefHeight(27);
    	this.pane.getChildren().add(this.searchBarTextField);
    	this.searchBarTextField.setLayoutX(36);
    	this.searchBarTextField.setLayoutY(57);
    }

    @FXML
    private void onAddButtonAction(ActionEvent event) {
    	boolean isSuccessfullyAdded = ViewModel.get().addSelectedGameToWatchlist(new ServerWatchlistAdditionService());
    	if (!isSuccessfullyAdded) {
    		this.gameAlreadyAddedLabel.setVisible(true);
    	} else {
    		PageConnectionUtility.openModal(UIFilePaths.NOTIFICATION_CRITERIA_PAGE_FILENAME, this.getCurrentStage(), NotificationCriteriaPageCodeBehind.class);
    	}
    }

    @FXML
    private void onSearchButtonAction(ActionEvent event) {
    	ViewModel.get().performSearch();
    	this.displayNoResultsLabelIfNecessary();
    }
    
    @FXML
    private void onClearSearchButtonAction(ActionEvent event) {
    	ViewModel.get().searchResultsProperty().getValue().clear();
    	this.searchBarTextField.setText(null);
    	this.noResultsLabel.setVisible(false);
    }

	private void displayNoResultsLabelIfNecessary() {
		if (ViewModel.get().searchResultsProperty().getValue().isEmpty()) {
    		this.noResultsLabel.setVisible(true);
    	} else {
    		this.noResultsLabel.setVisible(false);
    	}
	}

    private void setUpBindings() {
    	this.setUpDisableBindings();
    	
    	ViewModel vm = ViewModel.get();
    	vm.browsePageSearchTermProperty().bind(this.searchBarTextField.textProperty());
    	this.gameResultsListView.itemsProperty().bind(vm.searchResultsProperty());
    	vm.browsePageSelectedGameProperty().bind(this.gameResultsListView.getSelectionModel().selectedItemProperty());
    }
    
    private void setUpDisableBindings() {
    	this.searchButton.disableProperty().bind(this.searchBarTextField.textProperty().isEmpty().or(this.searchBarTextField.textProperty().isNull()));
    	this.addButton.disableProperty().bind(this.gameResultsListView.getSelectionModel().selectedItemProperty().isNull());
    }
    
    private Stage getCurrentStage() {
    	return ((Stage) this.searchButton.getScene().getWindow());
    }

    private void setUpNavigationBar() {
    	this.browsingPageBorderPane.setLeft(NavigationBarCodeBehind.getNavigationBarAsPane());
    	this.disableCurrentPageButton();    }

    private void disableCurrentPageButton() {
		VBox vbox = (VBox) this.browsingPageBorderPane.getChildren().get(1);
		for (var button : vbox.getChildren()) {
			if (button.getId().equals(this.browsingPageBorderPane.getId())) {
				button.setDisable(true);
				break;
			}
		}
	}
}
