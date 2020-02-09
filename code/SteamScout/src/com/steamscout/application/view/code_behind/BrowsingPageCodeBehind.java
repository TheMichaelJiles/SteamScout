package com.steamscout.application.view.code_behind;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import com.steamscout.application.model.game_data.Game;
import com.steamscout.application.view.ViewModel;
import com.steamscout.application.view.browse_game_listcell.BrowseGameListCell;

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
    	this.gameResultsListView.setCellFactory(listView -> new BrowseGameListCell());
    	this.setUpBindings();
    }

    @FXML
    private void onAddButtonAction(ActionEvent event) {

    }

    @FXML
    private void onLogoutButtonAction(ActionEvent event) {

    }

    @FXML
    private void onNotificationPageButtonAction(ActionEvent event) {

    }

    @FXML
    private void onSearchButtonAction(ActionEvent event) {
    	ViewModel.get().performSearch();
    }

    @FXML
    private void onWatchlistPageButtonAction(ActionEvent event) {

    }

    private void setUpBindings() {
    	this.setUpDisableBindings();
    	
    	ViewModel vm = ViewModel.get();
    	vm.browsePageSearchTermProperty().bind(this.searchBarTextField.textProperty());
    	this.gameResultsListView.itemsProperty().bind(vm.searchResultsProperty());
    }
    
    private void setUpDisableBindings() {
    	this.searchButton.disableProperty().bind(this.searchBarTextField.textProperty().isEmpty());
    }
}