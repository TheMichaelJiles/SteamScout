package com.steamscout.application.view.code_behind;

import com.steamscout.application.model.notification.Notification;
import com.steamscout.application.util.PageConnectionUtility;
import com.steamscout.application.view.UIFilePaths;
import com.steamscout.application.view.ViewModel;
import com.steamscout.application.view.game_listcell.NotificationListCell;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
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
    private Label noNotificationsLabel;
    
    @FXML
    void initialize() {
    	this.setUpNavigationBar();
    	this.notificationsListView.setCellFactory(factory -> new NotificationListCell());
    	this.notificationsListView.itemsProperty().bind(ViewModel.get().notificationsProperty());
    	this.handleNoNotificationsWarning();
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
    private void onWatchlistPageButtonAction(ActionEvent event) {
    	PageConnectionUtility.transitionPageTo(UIFilePaths.WATCHLIST_PAGE_FILENAME, this.getCurrentStage(), WatchlistPageCodeBehind.class);
    }
    
    private Stage getCurrentStage() {
    	return ((Stage) this.notificationsPageBorderPane.getScene().getWindow());
    }
    
	private void setUpNavigationBar() {
    	this.notificationsPageBorderPane.setLeft(NavigationBarCodeBehind.getNavigationBarAsPane());
    	this.disableCurrentPageButton();
    }
	
	private void handleNoNotificationsWarning() {
		if (ViewModel.get().notificationsProperty().isEmpty()) {
			this.notificationsListView.setDisable(true);
			this.noNotificationsLabel.setVisible(true);
		} else {
			this.notificationsListView.setDisable(false);
			this.noNotificationsLabel.setVisible(false);
		}
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
