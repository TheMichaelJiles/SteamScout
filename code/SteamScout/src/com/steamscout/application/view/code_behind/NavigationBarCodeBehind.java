package com.steamscout.application.view.code_behind;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.steamscout.application.Main;
import com.steamscout.application.util.PageConnectionUtility;
import com.steamscout.application.view.UIFilePaths;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Code behind for dynamic persistent navigation bar.
 * 
 * @author Michael Jiles
 *
 */
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
	private VBox navigationBarVBox;

	/**
	 * Loads the NavigationBar.fxml file and returns it as a Pane.
	 * 
	 * @precondition: none
	 * @postcondition: none
	 * @return The NavigationBar.fxml file as a Pane.
	 */
	public static Pane getNavigationBarAsPane() {
		Pane navigationBar = null;
		try {
			Path pagePath = Paths.get(".", "view", "fxml", UIFilePaths.NAVIGATION_BAR_FILENAME);
			URL pageUrl = Main.class.getResource(pagePath.toString());
			FXMLLoader loader = new FXMLLoader(pageUrl);
			navigationBar = loader.load();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return navigationBar;
	}
	
	private Stage getCurrentStage() {
		return (Stage) this.navigationBarVBox.getScene().getWindow();
	}

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
	

}
