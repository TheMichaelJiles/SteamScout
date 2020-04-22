package com.steamscout.application.view.code_behind;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;

import com.steamscout.application.model.notification.Notification;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class NotificationListCellCodeBehind {

    @FXML
    private AnchorPane pane;

    @FXML
    private Label gameTitleLabel;

    @FXML
    private Label gameCurrentPriceLabel;

    @FXML
    private Label gameOnSaleLabel;

    @FXML
    private Button viewOnSteamButton;
    
    private String steamLink;
    
    @FXML
    void initialize() {
    	
    }

    @FXML
    void onViewOnSteamButtonAction(ActionEvent event) {
    	if (System.getProperty("os.name").toLowerCase().startsWith("win")) {
    		this.openWindows();
    	} else {
    		this.openLinux();
    	}
    }
    
    private void openWindows() {
    	Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
            try {
                desktop.browse(new URI(this.steamLink));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    private void openLinux() {
    	Runtime runtime = Runtime.getRuntime();
        try {
            runtime.exec("xdg-open " + this.steamLink);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void updateLabels(Notification notification) {
    	this.gameTitleLabel.setText(notification.getTitle());
    	this.gameOnSaleLabel.setVisible(notification.getPriceReduction() > 0);
    	this.gameCurrentPriceLabel.setText(Double.toString(notification.getCurrentPrice()));
    	this.steamLink = notification.getSteamLink();
    }

}
