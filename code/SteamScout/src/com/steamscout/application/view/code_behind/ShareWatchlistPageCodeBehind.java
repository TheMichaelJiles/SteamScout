package com.steamscout.application.view.code_behind;

import com.steamscout.application.connection.ServerWatchlistFetchService;
import com.steamscout.application.model.game_data.Game;
import com.steamscout.application.view.ViewModel;
import com.steamscout.application.view.game_listcell.GameListCell;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.AnchorPane;

/**
 * This page is used for a user to add games to their watchlist from
 * another user's watchlist.
 * 
 * @author Luke Whaley
 *
 */
public class ShareWatchlistPageCodeBehind {

	@FXML
    private AnchorPane pane;

    @FXML
    private Label displayedUsernameLabel;

    @FXML
    private Button addToWatchlistButton;

    @FXML
    private ListView<Game> watchlistListView;

    @FXML
    private void initialize() {
    	this.watchlistListView.setCellFactory(view -> new GameListCell());
    	this.watchlistListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }
    
    /**
     * Loads the information to display on this page for the specified username.
     * 
     * @precondition none
     * @postcondition none
     * 
     * @param username the username to get the information for.
     */
    public void load(String username) {
    	this.displayedUsernameLabel.textProperty().setValue(username);
    	this.watchlistListView.setItems(FXCollections.observableArrayList(ViewModel.get().fetchWatchlistFor(username, new ServerWatchlistFetchService())));
    }
    
    @FXML
    private void onAddToWatchlistButtonAction(ActionEvent event) {

    }
	
}
