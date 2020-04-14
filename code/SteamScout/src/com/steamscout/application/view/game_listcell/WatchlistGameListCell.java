package com.steamscout.application.view.game_listcell;

import com.steamscout.application.model.game_data.Game;
import com.steamscout.application.view.ViewModel;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

/**
 * A ListCell template for Game objects on the watchlist page.
 * 
 * @author Luke Whaley
 *
 */
public class WatchlistGameListCell extends ListCell<Game> {

	private static final String NOTIFICATION_TEXT = "Needs Criteria!";
	
	private Label titleLabel;
	private Label notificationLabel;
	
	/**
	 * Creates a new GameListCell object.
	 * 
	 * @precondition none
	 * @postcondition getPrefWidth() == 0
	 */
	public WatchlistGameListCell() {
		this.setPrefWidth(0);
		
		this.titleLabel = new Label();
		this.notificationLabel = new Label();
		HBox box = new HBox();
		box.getChildren().addAll(this.titleLabel, this.notificationLabel);
		
		this.notificationLabel.setPadding(new Insets(0, 0, 0, 5));
		this.notificationLabel.setTextFill(Color.DARKRED);
		this.setGraphic(box);
	}
	
	@Override
	protected void updateItem(Game game, boolean empty) {
		super.updateItem(game, empty);
		if (empty) {
			this.setText(null);
		} else {
			this.titleLabel.setText(game.getTitle());
			boolean needsNotificationCriteria = !ViewModel.get().containsNotificationCriteria(game);
			if (needsNotificationCriteria) {
				this.notificationLabel.setText(NOTIFICATION_TEXT);
			} else {
				this.notificationLabel.setText("");
			}
		}
	}
	
}
