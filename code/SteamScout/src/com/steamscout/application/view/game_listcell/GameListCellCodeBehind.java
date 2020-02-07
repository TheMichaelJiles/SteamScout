package com.steamscout.application.view.game_listcell;

import java.awt.Desktop;
import java.awt.Desktop.Action;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.NumberFormat;
import java.util.Locale;

import com.steamscout.application.model.game_data.Game;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * The code behind for a particular GameListCell.fxml
 * 
 * @author Thomas Whaley
 *
 */
public class GameListCellCodeBehind {

	@FXML
    private AnchorPane pane;

    @FXML
    private ImageView gameImageView;

    @FXML
    private Label gameTitleLabel;

    @FXML
    private Label gameAppIdLabel;

    @FXML
    private Label gameCurrentPriceLabel;

    @FXML
    private Label gameOnSaleLabel;

    @FXML
    private Label gameSteamLinkLabel;
    
    @FXML
    private Button addToWatchlistButton;
    
    private Game sourceGame;
    
    @FXML
    private void onAddToWatchlistButtonAction(ActionEvent event) {
    	// TODO: add the source game to watchlist.
    }
    
    @FXML
    private void onGameSteamLinkLabelClicked(MouseEvent event) {
    	Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
    	if (desktop != null && desktop.isSupported(Action.BROWSE)) {
    		try {
				desktop.browse(new URI(this.sourceGame.getSteamLink()));
			} catch (IOException | URISyntaxException e) {
				e.printStackTrace();
			}
    	}
    }

    @FXML
    private void onGameSteamLinkLabelEnter(MouseEvent event) {
    	this.gameSteamLinkLabel.setStyle("-fx-cursor: hand");
    }
    
    /**
     * Initializes the contents of this list cell to the data within the specified
     * game object.
     * 
     * @precondition none
     * @postcondition none
     * 
     * @param game the game to derive data from.
     */
    public void initializeContent(Game game) {
    	Image gameImage = new Image(game.getImageUrl());
    	
    	this.gameImageView.setImage(gameImage);
    	this.gameTitleLabel.setText(game.getTitle());
    	this.gameAppIdLabel.setText(String.valueOf(game.getAppId()));
    	this.gameCurrentPriceLabel.setText(NumberFormat.getCurrencyInstance(Locale.US).format(game.getCurrentPrice()));
    	this.gameOnSaleLabel.setVisible(game.isOnSale());
    	
    	this.sourceGame = game;
    }
    
    /**
     * Gets the view associated with this list cell.
     * 
     * @precondition none
     * @postcondition none
     * 
     * @return the view associated with this list cell.
     */
    public Node getView() {
    	return this.pane;
    }
}
