package com.steamscout.application.view.code_behind;

import com.steamscout.application.model.game_data.Game;
import com.steamscout.application.util.PageConnectionUtility;
import com.steamscout.application.view.UIFilePaths;
import com.steamscout.application.view.ViewModel;
import com.steamscout.application.view.game_listcell.GameListCell;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
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
    private Button browsePageButton;

    @FXML
    private Button removeButton;

    @FXML
    private Button notificationPageButton;

    @FXML
    private Button logoutPageButton;

    @FXML
    private Button modifyButton;

    @FXML
    private Button searchButton;

    @FXML
    private void initialize() {
    	this.watchlistListView.setCellFactory(listview -> new GameListCell());
    	this.setUpBindings();
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

    }
    
    private Stage getCurrentStage() {
    	return ((Stage) this.searchButton.getScene().getWindow());
    }
    
    private void setUpBindings() {
    	this.setUpDisableBindings();
    	
    	ViewModel vm = ViewModel.get();
    	this.watchlistListView.itemsProperty().bind(vm.watchlistProperty());
    	vm.watchlistPageSelectedGameProperty().bind(this.watchlistListView.getSelectionModel().selectedItemProperty());
    }
    
    private void setUpDisableBindings() {
    	this.removeButton.disableProperty().bind(this.watchlistListView.getSelectionModel().selectedItemProperty().isNull());
    }
}
