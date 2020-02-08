package com.steamscout.application.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

/**
 * Watchlist page code behind that connects to ViewModel
 * @author TheMichaelJiles
 *
 */
public class WatchlistPageCodeBehind {

    @FXML
    private TextField searchBarTextField;

    @FXML
    private ListView<?> watchlistListView;

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
    
    private ViewModel viewmodel;

    @FXML
    void onBrowsePageButtonAction(ActionEvent event) {

    	
    }

    @FXML
    void onLogoutButtonAction(ActionEvent event) {

    }

    @FXML
    void onModifyButtonAction(ActionEvent event) {

    }

    @FXML
    void onNotificationPageButtonAction(ActionEvent event) {

    }

    @FXML
    void onRemoveButtonAction(ActionEvent event) {

    }

    @FXML
    void onSearchButtonAction(ActionEvent event) {

    }

	public void setViewModel(ViewModel viewmodel) {
		this.viewmodel = viewmodel;
	}

}
