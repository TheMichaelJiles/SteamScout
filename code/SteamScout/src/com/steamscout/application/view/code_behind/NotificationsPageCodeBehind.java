package com.steamscout.application.view.code_behind;

import com.steamscout.application.util.PageConnectionUtility;
import com.steamscout.application.view.UIFilePaths;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

/**
 * Notifications page code behind that connects to ViewModel
 * @author TheMichaelJiles
 *
 */
public class NotificationsPageCodeBehind {

    @FXML
    private ListView<?> notificationsListView;

    @FXML
    private Button watchlistPageButton;

    @FXML
    private Button browsePageButton;

    @FXML
    private Button logoutButton;
    
    @FXML
    void initialize() {
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
    private void onWatchlistPageButtonAction(ActionEvent event) {
    	PageConnectionUtility.transitionPageTo(UIFilePaths.WATCHLIST_PAGE_FILENAME, this.getCurrentStage());
    }
    
    private Stage getCurrentStage() {
    	return ((Stage) this.browsePageButton.getScene().getWindow());
    }

}
