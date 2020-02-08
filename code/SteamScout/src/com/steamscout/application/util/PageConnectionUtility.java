package com.steamscout.application.util;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.steamscout.application.Main;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Utility class for changing the scene between pages.
 * 
 * @author Michael Jiles
 *
 */
public class PageConnectionUtility {

	/**
	 * Switches the scene of the passed in stage to the specified filePath. If the
	 * filePath is not found, the user will be displayed an error message that an
	 * internal error has occurred.
	 * 
	 * @precondition filePath must exist
	 * @postcondition the scene of the current stage is swapped to the file that is
	 *                reached by the passed in filePath.
	 * @param filePath the path to the fxml file to which the scene should be
	 *                 switched.
	 * @param stage    the current stage
	 */
	public static void transitionPageTo(String filePath, Stage stage) {
		try {
			Path watchlistPagePath = Paths.get(".", "view", "fxml", filePath);
			URL watchlistPageURL = Main.class.getResource(watchlistPagePath.toString());
			FXMLLoader loader = new FXMLLoader(watchlistPageURL);
			Pane pane = loader.load();
			Scene scene = new Scene(pane);
			// Stage currentStage = (Stage) ((Node)
			// event.getSource()).getScene().getWindow();
			stage.setScene(scene);
		} catch (IOException e) {

		}
	}

}
