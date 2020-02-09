package com.steamscout.application.view.game_listcell;

import com.steamscout.application.model.game_data.Game;

import javafx.scene.control.ListCell;

/**
 * A ListCell template for Game objects on the browse page.
 * 
 * @author Thomas Whaley
 *
 */
public class GameListCell extends ListCell<Game> {
	
	/**
	 * Creates a new GameListCell object.
	 * 
	 * @precondition none
	 * @postcondition getPrefWidth() == 0
	 */
	public GameListCell() {
		this.setPrefWidth(0);
	}
	
	@Override
	protected void updateItem(Game game, boolean empty) {
		super.updateItem(game, empty);
		if (empty) {
			this.setText(null);
		} else {
			this.setText(game.getTitle());
		}
	}

}
