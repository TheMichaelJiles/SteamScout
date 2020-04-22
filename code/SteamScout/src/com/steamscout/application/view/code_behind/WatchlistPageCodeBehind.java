package com.steamscout.application.view.code_behind;

import com.steamscout.application.connection.ServerWatchlistRemovalService;
import com.steamscout.application.model.game_data.Game;
import com.steamscout.application.util.PageConnectionUtility;
import com.steamscout.application.view.UIFilePaths;
import com.steamscout.application.view.ViewModel;
import com.steamscout.application.view.autotextfield.PredictionTextField;
import com.steamscout.application.view.game_listcell.WatchlistGameListCell;

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

/**
 * Watchlist page code behind that connects to ViewModel
 * @author TheMichaelJiles
 *
 */
public class WatchlistPageCodeBehind {

    private TextField searchBarTextField;

    @FXML
    private AnchorPane pane;
    
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
    private Button linkSteamWishlistButton;
    
    @FXML
    private Button importButton;

    @FXML
    private BorderPane watchlistPageBorderPane;
    
    @FXML
    private Label noResultsLabel;


    @FXML
    private void initialize() {
    	this.initializeTextField();
    	
    	this.watchlistListView.setCellFactory(listview -> new WatchlistGameListCell());
    	this.setUpBindings();
    	this.setUpListeners();
    	this.setUpNavigationBar();
    }
    
    private void initializeTextField() {
    	this.searchBarTextField = new PredictionTextField(term -> ViewModel.get().makeWatchlistPagePrediction(term), 10);
    	this.searchBarTextField.setPrefWidth(210);
    	this.searchBarTextField.setPrefHeight(27);
    	this.pane.getChildren().add(this.searchBarTextField);
    	this.searchBarTextField.setLayoutX(32);
    	this.searchBarTextField.setLayoutY(62);
    }
    
    @FXML
    private void onLinkSteamWishlistButtonAction(ActionEvent event) {
    	PageConnectionUtility.openModal(UIFilePaths.LINK_STEAM_WISHLIST_PAGE, this.getCurrentStage(), LinkSteamWishlistPageCodeBehind.class);
    }
    
    @FXML
    private void onImportButtonAction(ActionEvent event) {
    	PageConnectionUtility.openModal(UIFilePaths.SHARE_WATCHLIST_USERNAME_PAGE, this.getCurrentStage(), ShareWatchlistUsernamePageCodeBehind.class);
    }
    
    @FXML
    private void onClearSearchButtonAction(ActionEvent event) {
    	ViewModel.get().resetWatchlistProperty();
    	this.searchBarTextField.textProperty().setValue(null);
    	this.hideNoResultsLabel();
    }
    
    @FXML
    private void onBrowsePageButtonAction(ActionEvent event) {
    	PageConnectionUtility.transitionPageTo(UIFilePaths.BROWSING_PAGE_FILENAME, this.getCurrentStage(), BrowsingPageCodeBehind.class);
    }

    @FXML
    private void onLogoutButtonAction(ActionEvent event) {
    	PageConnectionUtility.transitionPageTo(UIFilePaths.LOGIN_PAGE_FILENAME, this.getCurrentStage(), LoginPageCodeBehind.class);
    }

    @FXML
    private void onModifyButtonAction(ActionEvent event) {
    	PageConnectionUtility.openModal(UIFilePaths.NOTIFICATION_CRITERIA_PAGE_FILENAME, this.getCurrentStage(), NotificationCriteriaPageCodeBehind.class);
    }

    @FXML
    private void onNotificationPageButtonAction(ActionEvent event) {
    	PageConnectionUtility.transitionPageTo(UIFilePaths.NOTIFICATIONS_PAGE_FILENAME, this.getCurrentStage(), NotificationsPageCodeBehind.class);
    }

    @FXML
    private void onRemoveButtonAction(ActionEvent event) {
    	ViewModel.get().removeSelectedGameFromWatchlist(new ServerWatchlistRemovalService());
    	this.watchlistListView.getSelectionModel().select(0);
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
		if (ViewModel.get().watchlistProperty().getValue().isEmpty()) {
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
    			PageConnectionUtility.openModal(UIFilePaths.NOTIFICATION_CRITERIA_PAGE_FILENAME, this.getCurrentStage(), NotificationCriteriaPageCodeBehind.class);
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
