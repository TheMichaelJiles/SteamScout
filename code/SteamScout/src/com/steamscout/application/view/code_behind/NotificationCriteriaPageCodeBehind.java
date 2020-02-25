package com.steamscout.application.view.code_behind;

import java.text.NumberFormat;
import java.util.Locale;

import com.steamscout.application.model.game_data.Game;
import com.steamscout.application.model.notification.NotificationCriteria;
import com.steamscout.application.model.user.User;
import com.steamscout.application.view.ViewModel;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class NotificationCriteriaPageCodeBehind {
	
	private static final String TARGET_PRICE_REGEX = "\\$\\d*\\.\\d{0,2}";
	private static final String TARGET_PRICE_DEFAULT = "$0.00";
	
	@FXML
    private AnchorPane pane;

    @FXML
    private Label gameTitleLabel;

    @FXML
    private Label gameIdLabel;
    
    @FXML
    private Label targetPriceLabel;

    @FXML
    private CheckBox onSaleCheckBox;

    @FXML
    private CheckBox belowThresholdCheckBox;

    @FXML
    private TextField targetPriceTextField;

    @FXML
    private Button submitButton;

    @FXML
    private Button cancelButton;

    @FXML
    private void initialize() {
    	this.setTargetEntryVisible(false);
    	this.setListeners();
    	this.fillFields();
    }
    
    @FXML
    private void onCancelButtonAction(ActionEvent event) {
    	this.exit();
    }

    @FXML
    private void onSubmitButtonAction(ActionEvent event) {
    	boolean onSale = this.onSaleCheckBox.selectedProperty().getValue();
    	boolean belowThreshold = this.belowThresholdCheckBox.selectedProperty().getValue();
    	double targetPrice = 0;
    	try {
    		targetPrice = Double.parseDouble(this.targetPriceTextField.textProperty().getValue().substring(1));
    	} catch (NumberFormatException e) {
    		targetPrice = 0;
    	}
    	
    	ViewModel.get().setSelectedGameNotificationCriteria(onSale, belowThreshold, targetPrice);
    	this.exit();
    }
    
    private void setListeners() {
    	this.belowThresholdCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
    		this.setTargetEntryVisible(newValue);
    		if (!newValue) {
    			this.targetPriceTextField.textProperty().setValue(TARGET_PRICE_DEFAULT);
    		}
    	});
    	this.targetPriceTextField.textProperty().addListener((observable, oldValue, newValue) -> {
    		if (!newValue.matches(TARGET_PRICE_REGEX)) {
    			this.targetPriceTextField.textProperty().setValue(oldValue != null ? oldValue : TARGET_PRICE_DEFAULT);
    		}
    	});
    }
    
    private void fillFields() {
    	Game browsePageGame = ViewModel.get().browsePageSelectedGameProperty().getValue();
    	Game watchlistPageGame = ViewModel.get().watchlistPageSelectedGameProperty().getValue();
    	Game game;
    	if (browsePageGame == null) {
    		game = watchlistPageGame;
    	} else {
    		game = browsePageGame;
    	}
    	this.gameTitleLabel.setText(game.getTitle());
    	this.gameIdLabel.setText(String.valueOf(game.getAppId()));
    	this.targetPriceTextField.textProperty().setValue(TARGET_PRICE_DEFAULT);
    	
    	User currentUser = ViewModel.get().userProperty().getValue();
    	NotificationCriteria criteria = currentUser.getWatchlist().getNotificationCriteria(game);
    	if (criteria != null) {
    		this.onSaleCheckBox.selectedProperty().setValue(criteria.shouldNotifyOnSale());
    		this.belowThresholdCheckBox.selectedProperty().setValue(criteria.shouldNotifyWhenBelowTargetPrice());
    		
    		NumberFormat currency = NumberFormat.getCurrencyInstance(Locale.US);
    		this.targetPriceTextField.textProperty().setValue(currency.format(criteria.getTargetPrice()).replaceAll(",", ""));
    	}
    }
    
    private void setTargetEntryVisible(boolean isVisible) {
    	this.targetPriceLabel.setVisible(isVisible);
    	this.targetPriceTextField.setVisible(isVisible);
    }
    
    private void exit() {
    	Stage currentStage = (Stage) this.pane.getScene().getWindow();
    	currentStage.close();
    }
    
}
