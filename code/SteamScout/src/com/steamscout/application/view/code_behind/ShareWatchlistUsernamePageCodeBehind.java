package com.steamscout.application.view.code_behind;

import com.steamscout.application.util.PageConnectionUtility;
import com.steamscout.application.view.UIFilePaths;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * When the user wants to fetch watchlist information for another
 * user to add to their own watchlist, this page opens first so that they
 * can fill in the username information.
 * 
 * @author Luke Whaley
 *
 */
public class ShareWatchlistUsernamePageCodeBehind {

	@FXML
    private AnchorPane pane;

    @FXML
    private TextField usernameTextField;

    @FXML
    private Button fetchButton;

    @FXML
    private void initialize() {
    	this.fetchButton.disableProperty().bind(this.usernameTextField.textProperty().isNull().or(this.usernameTextField.textProperty().isEmpty()));
    }
    
    @FXML
    private void onFetchButtonAction(ActionEvent event) {
    	Stage currentStage = (Stage) this.pane.getScene().getWindow();
    	ShareWatchlistPageCodeBehind controller = PageConnectionUtility.transitionPageTo(UIFilePaths.SHARE_WATCHLIST_PAGE, currentStage, ShareWatchlistPageCodeBehind.class);
    	controller.load(this.usernameTextField.textProperty().getValue());
    }
	                                                                                                                            
}
