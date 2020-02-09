package com.steamscout.application.view.browse_game_listcell;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.steamscout.application.Main;
import com.steamscout.application.model.game_data.Game;
import com.steamscout.application.view.UIFilePaths;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;

/**
 * A ListCell template for Game objects on the browse page.
 * 
 * @author Thomas Whaley
 *
 */
public class BrowseGameListCell extends ListCell<Game> {
	
	private BrowseGameListCellCodeBehind cellController;
	
	/**
	 * Creates a new GameListCell object.
	 * 
	 * @precondition none
	 * @postcondition getPrefWidth() == 0
	 */
	public BrowseGameListCell() {
		this.setPrefWidth(0);
		Path gameListCellPath = Paths.get(".", "view", "browse_game_listcell", UIFilePaths.BROWSE_GAME_LIST_CELL_FILENAME);
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
