package com.steamscout.application.view.game_listcell;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.steamscout.application.Main;
import com.steamscout.application.model.game_data.Game;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;

/**
 * A ListCell template for Game objects.
 * 
 * @author Thomas Whaley
 *
 */
public class GameListCell extends ListCell<Game> {

	private static final String GAME_LIST_CELL_FILENAME = "GameListCell.fxml";
	
	private GameListCellCodeBehind cellController;
	
	/**
	 * Creates a new GameListCell object.
	 * 
	 * @precondition none
	 * @postcondition getPrefWidth() == 0
	 */
	public GameListCell() {
		this.setPrefWidth(0);
		Path gameListCellPath = Paths.get(".", "view", "game_listcell", GAME_LIST_CELL_FILENAME);
		URL gameListCellUrl = Main.class.getResource(gameListCellPath.toString());
		FXMLLoader loader = new FXMLLoader(gameListCellUrl);
		try {
			loader.load();
			this.cellController = loader.getController();
		} catch (IOException e) {
			throw new NullPointerException("List Cell could not be instantiated");
		}
	}
	
	@Override
	protected void updateItem(Game game, boolean empty) {
		super.updateItem(game, empty);
		if (empty) {
			this.setGraphic(null);
		} else {
			this.cellController.initializeContent(game);
			this.setGraphic(this.cellController.getView());
		}
	}

}
