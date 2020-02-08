package com.steamscout.application.view.code_behind;

import com.steamscout.application.view.ViewModel;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

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
	private TextField passwordTextField;

	@FXML
	private Button createAccountPageButton;

	@FXML
	private Button loginButton;

	@FXML
	private void onCreateAccountPageButtonAction(ActionEvent event) {

	}

	@FXML
	private void onLoginButtonAction(ActionEvent event) {
		ViewModel.get().loginUser();
	}

}
