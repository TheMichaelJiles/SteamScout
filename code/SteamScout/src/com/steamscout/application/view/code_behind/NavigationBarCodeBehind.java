package com.steamscout.application.view.code_behind;

import com.steamscout.application.util.PageConnectionUtility;
import com.steamscout.application.view.UIFilePaths;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class NavigationBarCodeBehind {

    @FXML
    private Button watchlistPageButton;

    @FXML
    private Button notificationsButton;

    @FXML
    private Button browseButton;

    @FXML
    private Button logoutButton;

    @FXML
    private void onBrowseButtonAction(ActionEvent event) {
    	PageConnectionUtility.transitionPageTo(UIFilePaths.BROWSING_PAGE_FILENAME, this.getCurrentStage());
    }

    @FXML
    private void onLogoutButtonAction(ActionEvent event) {
    	PageConnectionUtility.transitionPageTo(UIFilePaths.LOGIN_PAGE_FILENAME, this.getCurrentStage());
    }

    @FXML
    private void onNotificationsButtonAction(ActionEvent event) {
    	PageConnectionUtility.transitionPageTo(UIFilePaths.NOTIFICATIONS_PAGE_FILENAME, this.getCurrentStage());
    }

    @FXML
    private void onWatchlistPageButtonAction(ActionEvent event) {
    	PageConnectionUtility.transitionPageTo(UIFilePaths.WATCHLIST_PAGE_FILENAME, this.getCurrentStage());
    }
    
    public Stage getCurrentStage() {
    	return (Stage) this.browseButton.getScene().getWindow();
    }

}
