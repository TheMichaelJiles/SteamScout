package com.steamscout.application.view.code_behind;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.steamscout.application.Main;
import com.steamscout.application.model.game_data.Game;

import com.steamscout.application.util.PageConnectionUtility;

import com.steamscout.application.view.UIFilePaths;
import com.steamscout.application.view.ViewModel;
import com.steamscout.application.view.game_listcell.GameListCell;

/**
 * Browsing page code behind that connects to ViewModel
 * @author TheMichaelJiles
 *
 */
public class BrowsingPageCodeBehind {

    @FXML
    private TextField searchBarTextField;

    @FXML
    private ListView<Game> gameResultsListView;

    @FXML
    private Button addButton;

    @FXML
    private Button searchButton;
    
    @FXML
    private Label noResultsLabel;
    
    @FXML
    private Label gameAlreadyAddedLabel;
    
    @FXML
    private BorderPane browsingPageBorderPane;
    
    @FXML
    private void initialize() {
    	this.gameResultsListView.setCellFactory(listView -> new GameListCell());
    	this.setUpBindings();
    	this.setUpNavigationBar();
    }

    @FXML
    private void onAddButtonAction(ActionEvent event) {
    	boolean isSuccessfullyAdded = ViewModel.get().addSelectedGameToWatchlist();
    	if (!isSuccessfullyAdded) {
    		this.gameAlreadyAddedLabel.setVisible(true);
    	} else {
    		PageConnectionUtility.openModal(UIFilePaths.NOTIFICATION_CRITERIA_PAGE_FILENAME, this.getCurrentStage());
    	}
    }

    @FXML
    private void onSearchButtonAction(ActionEvent event) {
    	ViewModel.get().performSearch();
    	this.displayNoResultsLabelIfNecessary();
    }

	private void displayNoResultsLabelIfNecessary() {
		if (this.gameResultsListView.getItems().isEmpty()) {
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
    	this.searchButton.disableProperty().bind(this.searchBarTextField.textProperty().isEmpty());
    	this.addButton.disableProperty().bind(this.gameResultsListView.getSelectionModel().selectedItemProperty().isNull());
    }
    
    private Stage getCurrentStage() {
    	return ((Stage) this.searchButton.getScene().getWindow());
    }

    private void setUpNavigationBar() {
    	this.browsingPageBorderPane.setLeft(NavigationBarCodeBehind.getNavigationBarAsPane());
    	removeCurrentPageButton();
    }

	private void removeCurrentPageButton() {
		VBox vbox = (VBox) this.browsingPageBorderPane.getChildren().get(1);
    	vbox.getChildren().removeIf(button -> button.getId().equals(this.browsingPageBorderPane.getId()));
	}
}
