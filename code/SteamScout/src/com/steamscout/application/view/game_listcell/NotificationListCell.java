package com.steamscout.application.view.game_listcell;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.steamscout.application.Main;
import com.steamscout.application.model.notification.Notification;
import com.steamscout.application.view.UIFilePaths;
import com.steamscout.application.view.code_behind.NotificationListCellCodeBehind;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.layout.Pane;

/**
 * Class outlining a custom list cell for Notification page
 * @author TheMichaelJiles
 *
 */
public class NotificationListCell extends ListCell<Notification> {
	
	private NotificationListCellCodeBehind controller;
	private Pane notificationPane;
	
	/**
	 * Constructs a new NotificationListCell object
	 * @precondition none
	 * @postcondition this.controller is set to a NotificationListCellCodeBehind, this.notificationPane is set to NotificationListCell.fxml
	 * 				  this.graphic == this.notificationPane
	 *
	 */
	public NotificationListCell() {
		try {
			Path notificationListCellPath = Paths.get(".", "view", "fxml", UIFilePaths.NOTIFICATION_LIST_CELL_FILENAME);
			URL notificationListCellURL = Main.class.getResource(notificationListCellPath.toString());
			
			FXMLLoader loader = new FXMLLoader(notificationListCellURL);
			this.notificationPane = loader.load();
			this.controller = loader.getController();
			this.setGraphic(this.notificationPane);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void updateItem(Notification notification, boolean empty) {
		super.updateItem(notification, empty);
		if (empty) {
			this.setGraphic(null);
		} else {
			this.controller.updateLabels(notification);
			this.setGraphic(notificationPane);
		}
	}

}
