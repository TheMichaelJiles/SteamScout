package com.steamscout.application.view.code_behind;

import com.steamscout.application.connection.ServerCreateAccountService;
import com.steamscout.application.util.PageConnectionUtility;
import com.steamscout.application.view.UIFilePaths;
import com.steamscout.application.view.ViewModel;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Create account page code behind that connects to ViewModel
 * @author TheMichaelJiles
 *
 */
public class CreateAccountPageCodeBehind {

    @FXML
    private TextField usernameTextField;

    @FXML
    private TextField passwordTextField;
    
    @FXML
    private TextField emailTextField;

    @FXML
    private Button createButton;
    
    @FXML
    private Button backButton;
    
    @FXML
    private Label errorLabel;

    @FXML
    private void initialize() {
    	ViewModel.get().createAccountPageUsernameProperty().bind(this.usernameTextField.textProperty());
    	ViewModel.get().createAccountPagePasswordProperty().bind(this.passwordTextField.textProperty());
    	ViewModel.get().createAccountPageEmailProperty().bind(this.emailTextField.textProperty());
    	this.errorLabel.textProperty().bind(ViewModel.get().createAccountPageErrorProperty());
    	
    	this.createButton.disableProperty().bind((this.usernameTextField.textProperty().isNull().or(this.usernameTextField.textProperty().isEmpty())).
    			or(this.passwordTextField.textProperty().isNull().or(this.passwordTextField.textProperty().isEmpty())).
    			or(this.emailTextField.textProperty().isNull().or(this.emailTextField.textProperty().isEmpty())));
    }
    
    @FXML
    private void onCreateButtonAction(ActionEvent event) {
    	boolean isCreated = ViewModel.get().createUserAccount(new ServerCreateAccountService());
    	if (isCreated) {
    		PageConnectionUtility.transitionPageTo(UIFilePaths.LOGIN_PAGE_FILENAME, (Stage) this.createButton.getScene().getWindow(), LoginPageCodeBehind.class);
    	}
    	
    	this.usernameTextField.textProperty().setValue("");
    	this.passwordTextField.textProperty().setValue("");
    	this.emailTextField.textProperty().setValue("");
    }

    @FXML
    private void onBackButtonAction(ActionEvent event) {
    	PageConnectionUtility.transitionPageTo(UIFilePaths.LOGIN_PAGE_FILENAME, (Stage) this.createButton.getScene().getWindow(), LoginPageCodeBehind.class);
    }
}
