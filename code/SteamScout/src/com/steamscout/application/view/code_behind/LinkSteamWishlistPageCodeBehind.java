package com.steamscout.application.view.code_behind;

import com.steamscout.application.connection.ServerLinkWishlistService;
import com.steamscout.application.view.ViewModel;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

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
    private void onLinkAccountsButtonAction(ActionEvent event) {
    	String accountId = this.accountIdTextField.textProperty().getValue();
    	ViewModel.get().linkWatchlist(new ServerLinkWishlistService(accountId));
    }
}
