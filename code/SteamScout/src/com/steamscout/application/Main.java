package com.steamscout.application;
	
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

/**
 * Main entry point for the application.
 * 
 * @author Thomas Whaley
 *
 */
public class Main extends Application {
	
	public static final String TITLE = "Steam Scout";
	
	public static final String LOGIN_PAGE_FILENAME = "LoginPage.fxml";
	
	@Override
	public void start(Stage primaryStage) {
		try {
			Path landingPagePath = Paths.get(".", "view", "fxml", LOGIN_PAGE_FILENAME);
			URL landingPageUrl = Main.class.getResource(landingPagePath.toString());
			
			FXMLLoader loader = new FXMLLoader(landingPageUrl);
			Pane landingPane = loader.load();
			
			Scene scene = new Scene(landingPane, 1280, 720);
			
			primaryStage.setTitle(TITLE);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Starts the application.
	 * 
	 * @precondition none
	 * @postcondition none
	 * 
	 * @param args program command-line arguments.
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
