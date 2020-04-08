package com.steamscout.application.view.code_behind;

import com.steamscout.application.connection.ServerLinkWishlistService;
import com.steamscout.application.view.ViewModel;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;

/**
 * This page pops up when the user chooses to link their Steam wishlist
 * with their SteamScout watchlist.
 * 
 * @author Luke Whaley
 *
 */
public class LinkSteamWishlistPageCodeBehind {

	@FXML
    private TextField accountIdTextField;

    @FXML
    private Button linkAccountsButton;
    
    @FXML
    private Label idLabel;
    
    private IntegerProperty idLengthProperty;
    
    @FXML
    private void initialize() {
    	this.idLengthProperty = new SimpleIntegerProperty();
    	this.setIdTooltip();
    	this.idLengthProperty.bind(this.accountIdTextField.textProperty().length());
    	this.linkAccountsButton.disableProperty().bind(this.idLengthProperty.isNotEqualTo(17));
    }
    
    @FXML
    private void onLinkAccountsButtonAction(ActionEvent event) {
    	String accountId = this.accountIdTextField.textProperty().getValue();
    	ViewModel.get().linkWatchlist(new ServerLinkWishlistService(accountId));
    }
    
    private void setIdTooltip() {
    	String tooltipText = "1. Open up your Steam client and choose View, then click Settings" + System.lineSeparator()
    					   + "2. Choose Interface and check the box that reads, \"Display Steam URL address when available\"" + System.lineSeparator()
    					   + "3. Click OK" + System.lineSeparator()
    					   + "4. Now click on your Steam Profile Name and select View Profile" + System.lineSeparator()
    					   + "Your SteamID will be listed in the URL at the top left (it's the really long number at the end.)";
    	Tooltip labeltip = new Tooltip(tooltipText);
    	this.idLabel.setTooltip(labeltip);
    }
}
