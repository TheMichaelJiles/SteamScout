package com.steamscout.application.util;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.steamscout.application.Main;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
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
			Path pagePath = Paths.get(".", "view", "fxml", filePath);
			URL pageUrl = Main.class.getResource(pagePath.toString());
			FXMLLoader loader = new FXMLLoader(pageUrl);
			Pane pane = loader.load();
			Scene scene = new Scene(pane);
			stage.setScene(scene);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Creates a new Stage as a child of the passed in Stage. The new stage is a modal
	 * stage that will prevent interacting with the application while the stage is open.
	 * 
	 * @precondition filePath must exist
	 * @postcondition a new Stage is created and shown.
	 * 
	 * @param filePath the path to the fxml file to which the Stage's pane will be based on.
	 * @param stage the current stage.
	 */
	public static void openModal(String filePath, Stage stage) {
		try {
			Path pagePath = Paths.get(".", "view", "fxml", filePath);
			URL pageUrl = Main.class.getResource(pagePath.toString());
			FXMLLoader loader = new FXMLLoader(pageUrl);
			Pane pane = loader.load();
			Scene scene = new Scene(pane);
			
			Stage newStage = new Stage();
			newStage.setScene(scene);
			newStage.initModality(Modality.WINDOW_MODAL);
			newStage.initOwner(stage);
			newStage.setResizable(false);
			newStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
