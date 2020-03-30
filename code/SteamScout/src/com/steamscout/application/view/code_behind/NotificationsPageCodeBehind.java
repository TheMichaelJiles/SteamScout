package com.steamscout.application.view.code_behind;

import com.steamscout.application.model.notification.Notification;
import com.steamscout.application.util.PageConnectionUtility;
import com.steamscout.application.view.UIFilePaths;
import com.steamscout.application.view.ViewModel;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;

/**
 * Notifications page code behind that connects to ViewModel
 * @author TheMichaelJiles
 *
 */
public class NotificationsPageCodeBehind {

    @FXML
    private ListView<Notification> notificationsListView;
    
    @FXML
    private BorderPane notificationsPageBorderPane;
    
    @FXML
    void initialize() {
    	this.setUpNavigationBar();
    	this.notificationsListView.itemsProperty().bind(ViewModel.get().notificationsProperty());
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
    	return ((Stage) this.notificationsPageBorderPane.getScene().getWindow());
    }
    
	private void setUpNavigationBar() {
    	this.notificationsPageBorderPane.setLeft(NavigationBarCodeBehind.getNavigationBarAsPane());
    	this.disableCurrentPageButton();
    }

	private void disableCurrentPageButton() {
		VBox vbox = (VBox) this.notificationsPageBorderPane.getChildren().get(1);
		for (var button : vbox.getChildren()) {
			if (button.getId().equals(this.notificationsPageBorderPane.getId())) {
				button.setDisable(true);
				break;
			}
		}
	}

}
