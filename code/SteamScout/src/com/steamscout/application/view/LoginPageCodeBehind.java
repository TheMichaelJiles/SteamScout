package com.steamscout.application.view;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.steamscout.application.Main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Login page code behind that connects to ViewModel
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
    
    private ViewModel viewmodel;

    @FXML
    void onCreateAccountPageButtonAction(ActionEvent event) {

    }

    @FXML
    void onLoginButtonAction(ActionEvent event) {
    	this.viewmodel.loginUser();
    	
    }
    
    public void setViewModel(ViewModel viewmodel) {
    	this.viewmodel = viewmodel;
    }

}
