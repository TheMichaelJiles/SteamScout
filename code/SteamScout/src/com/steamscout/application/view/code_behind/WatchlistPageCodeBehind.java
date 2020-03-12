package com.steamscout.application.view.code_behind;

import com.steamscout.application.model.game_data.Game;
import com.steamscout.application.util.PageConnectionUtility;
import com.steamscout.application.view.UIFilePaths;
import com.steamscout.application.view.ViewModel;
import com.steamscout.application.view.game_listcell.GameListCell;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Watchlist page code behind that connects to ViewModel
 * @author TheMichaelJiles
 *
 */
public class WatchlistPageCodeBehind {

    @FXML
    private TextField searchBarTextField;

    @FXML
    private ListView<Game> watchlistListView;

    @FXML
    private Button removeButton;

    @FXML
    private Button modifyButton;

    @FXML
    private Button searchButton;
    
    @FXML
    private Button clearSearchButton;

    @FXML
    private BorderPane watchlistPageBorderPane;
    
    @FXML
    private Label noResultsLabel;


    @FXML
    private void initialize() {
    	this.watchlistListView.setCellFactory(listview -> new GameListCell());
    	this.setUpBindings();
    	this.setUpListeners();
    	this.setUpNavigationBar();
    }
    
    @FXML
    private void onClearSearchButtonAction(ActionEvent event) {
    	ViewModel.get().resetWatchlistProperty();
    	this.searchBarTextField.textProperty().setValue(null);
    	this.hideNoResultsLabel();
    }
    
    @FXML
    private void onBrowsePageButtonAction(ActionEvent event) {
    	PageConnectionUtility.transitionPageTo(UIFilePaths.BROWSING_PAGE_FILENAME, this.getCurrentStage());
    }

    @FXML
    private void onLogoutButtonAction(ActionEvent event) {
    	PageConnectionUtility.transitionPageTo(UIFilePaths.LOGIN_PAGE_FILENAME, this.getCurrentStage());
    }

    @FXML
    private void onModifyButtonAction(ActionEvent event) {
    	PageConnectionUtility.openModal(UIFilePaths.NOTIFICATION_CRITERIA_PAGE_FILENAME, this.getCurrentStage());
    }

    @FXML
    private void onNotificationPageButtonAction(ActionEvent event) {
    	PageConnectionUtility.transitionPageTo(UIFilePaths.NOTIFICATIONS_PAGE_FILENAME, this.getCurrentStage());
    }

    @FXML
    private void onRemoveButtonAction(ActionEvent event) {
    	ViewModel.get().removeSelectedGameFromWatchlist();
    }

    @FXML
    private void onSearchButtonAction(ActionEvent event) {
    	ViewModel.get().performWatchlistSearch(this.searchBarTextField.textProperty().getValue());
    	this.displayNoResultsLabelIfNecessary();
    	
    }
    
    private Stage getCurrentStage() {
    	return ((Stage) this.searchButton.getScene().getWindow());
    }
    
    private void displayNoResultsLabelIfNecessary() {
		if (this.watchlistListView.getItems().isEmpty()) {
    		this.noResultsLabel.setVisible(true);
    	} else {
    		this.hideNoResultsLabel();
    	}
	}
    
    private void hideNoResultsLabel() {
    	this.noResultsLabel.setVisible(false);
    }
    
    private void setUpListeners() {
    	this.watchlistListView.setOnMouseClicked(event -> {
    		if (this.watchlistListView.getSelectionModel().getSelectedItem() != null && event.getClickCount() == 2) {
    			PageConnectionUtility.openModal(UIFilePaths.NOTIFICATION_CRITERIA_PAGE_FILENAME, this.getCurrentStage());
    		}
    	});
    }
    
    private void setUpBindings() {
    	this.setUpDisableBindings();
    	
    	ViewModel vm = ViewModel.get();
    	this.watchlistListView.itemsProperty().bind(vm.watchlistProperty());
    	vm.watchlistPageSelectedGameProperty().bind(this.watchlistListView.getSelectionModel().selectedItemProperty());
    }
    
    private void setUpDisableBindings() {
    	this.removeButton.disableProperty().bind(this.watchlistListView.getSelectionModel().selectedItemProperty().isNull());
    	this.modifyButton.disableProperty().bind(this.watchlistListView.getSelectionModel().selectedItemProperty().isNull());
    	this.searchButton.disableProperty().bind(this.searchBarTextField.textProperty().isNull().or(this.searchBarTextField.textProperty().isEmpty()));
    }
    
    private void setUpNavigationBar() {
    	this.watchlistPageBorderPane.setLeft(NavigationBarCodeBehind.getNavigationBarAsPane());
    	this.disableCurrentPageButton();
    }

	private void disableCurrentPageButton() {
		VBox vbox = (VBox) this.watchlistPageBorderPane.getChildren().get(1);
		for (var button : vbox.getChildren()) {
			if (button.getId().equals(this.watchlistPageBorderPane.getId())) {
				button.setDisable(true);
				break;
			}
		}
	}
}
