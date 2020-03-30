package com.steamscout.application.view.code_behind;

import com.steamscout.application.util.PageConnectionUtility;
import com.steamscout.application.view.UIFilePaths;
import com.steamscout.application.view.ViewModel;
import com.steamscout.application.connection.ServerLoginService;
import com.steamscout.application.connection.ServerWatchlistFetchService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Login page code behind that connects to ViewModel
 * 
 * @author TheMichaelJiles
 *
 */
public class LoginPageCodeBehind {

	@FXML
	private TextField usernameTextField;

	@FXML
	private PasswordField passwordTextField;

	@FXML
	private Button createAccountPageButton;

	@FXML
	private Button loginButton;
	
	@FXML
	private Label errorLabel;

	@FXML
	private void initialize() {
		ViewModel.get().loginPageUsernameProperty().bind(this.usernameTextField.textProperty());
		ViewModel.get().loginPagePasswordProperty().bind(this.passwordTextField.textProperty());
		this.errorLabel.textProperty().bind(ViewModel.get().loginPageErrorProperty());
		
		this.loginButton.disableProperty().bind((this.usernameTextField.textProperty().isNull().or(this.usernameTextField.textProperty().isEmpty())).
				or(this.passwordTextField.textProperty().isNull().or(this.passwordTextField.textProperty().isEmpty())));
	}

	@FXML
	private void onCreateAccountPageButtonAction(ActionEvent event) {
		PageConnectionUtility.transitionPageTo(UIFilePaths.CREATE_ACCOUNT_PAGE_FILENAME, this.getCurrentStage());
	}

	@FXML
	private void onLoginButtonAction(ActionEvent event) {
		boolean isSuccessful = ViewModel.get().loginUser(new ServerLoginService());
		if (isSuccessful) {
			ViewModel.get().loadWatchlist(new ServerWatchlistFetchService());
			PageConnectionUtility.transitionPageTo(UIFilePaths.WATCHLIST_PAGE_FILENAME, this.getCurrentStage());
		}
		
		this.usernameTextField.textProperty().setValue("");
		this.passwordTextField.textProperty().setValue("");
	}
	
	private Stage getCurrentStage() {
		return ((Stage) this.loginButton.getScene().getWindow());
	}

}
