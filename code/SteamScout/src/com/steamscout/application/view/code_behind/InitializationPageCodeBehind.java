package com.steamscout.application.view.code_behind;

import java.util.LinkedList;
import java.util.Queue;

import com.steamscout.application.util.PageConnectionUtility;
import com.steamscout.application.util.TimeLimitedAction;
import com.steamscout.application.view.UIFilePaths;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * A loading page that loads necessary data before the
 * application starts.
 * 
 * @author Thomas Whaley
 *
 */
public class InitializationPageCodeBehind {

	@FXML
    private AnchorPane pane;

    @FXML
    private Label loadingLabel;
    
    private Queue<String> animationText;
    private TimeLimitedAction animation;
    
    private static final int ANIMATION_INTERVAL_MS = 500;
    
    /**
     * Creates a new InitializationPageCodeBehind object.
     * 
     * @precondition none
     * @postcondition none
     */
    public InitializationPageCodeBehind() {
    	this.setupAnimation();
    }
    
    @FXML
    private void initialize() {
    	this.animation.start();
    	this.startLoading();
    }
	
    private void transitionToApplication() {
    	Stage currentStage = (Stage) this.pane.getScene().getWindow();
    	PageConnectionUtility.transitionPageTo(UIFilePaths.LOGIN_PAGE_FILENAME, currentStage);
    }
    
    private void startLoading() {
    }
    
    private void setupAnimation() {
    	this.animationText = new LinkedList<String>();
    	this.animationText.add("");
    	this.animationText.add(".");
    	this.animationText.add("..");
    	this.animationText.add("...");
    	
    	this.animation = new TimeLimitedAction(timer -> {
    		String currentText = this.animationText.remove();
    		this.loadingLabel.setText(currentText);
    		this.animationText.add(currentText);
    	}, ANIMATION_INTERVAL_MS);
    }
}
