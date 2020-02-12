package com.steamscout.application.view.code_behind;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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
    private Button watchlistPageButton;

    @FXML
    private Button addButton;

    @FXML
    private Button notificationPageButton;

    @FXML
    private Button logoutButton;

    @FXML
    private Button searchButton;
    
    @FXML
    private void initialize() {
    	this.gameResultsListView.setCellFactory(listView -> new GameListCell());
    	this.setUpBindings();
    }

    @FXML
    private void onAddButtonAction(ActionEvent event) {
    	ViewModel.get().addSelectedGameToWatchlist();
    }

    @FXML
    private void onLogoutButtonAction(ActionEvent event) {
    	PageConnectionUtility.transitionPageTo(UIFilePaths.LOGIN_PAGE_FILENAME, this.getCurrentStage());
    }

    @FXML
    private void onNotificationPageButtonAction(ActionEvent event) {
    	PageConnectionUtility.transitionPageTo(UIFilePaths.NOTIFICATIONS_PAGE_FILENAME, this.getCurrentStage());
    }

    @FXML
    private void onSearchButtonAction(ActionEvent event) {
    	ViewModel.get().performSearch();
    }

    @FXML
    private void onWatchlistPageButtonAction(ActionEvent event) {
    	PageConnectionUtility.transitionPageTo(UIFilePaths.WATCHLIST_PAGE_FILENAME, this.getCurrentStage());
    }
    
    private Stage getCurrentStage() {
    	return (Stage) this.addButton.getScene().getWindow();
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
}
