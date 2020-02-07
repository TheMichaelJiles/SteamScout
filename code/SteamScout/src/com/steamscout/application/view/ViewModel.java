package com.steamscout.application.view;

import com.steamscout.application.model.user.Credentials;
import com.steamscout.application.model.user.User;

import java.io.IOException;

import com.steamscout.application.model.api.tasks.NotificationCheck;
import com.steamscout.application.model.api.tasks.SteamSearch;
import com.steamscout.application.model.game_data.Game;
import com.steamscout.application.model.game_data.Watchlist;
import com.steamscout.application.model.notification.Notification;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;

/**
 * Acts as the intermediary between the view and model
 * packages in SteamScout.
 * 
 * @author Thomas Whaley
 *
 */
public final class ViewModel {

	private ObjectProperty<User> userProperty;
	private ListProperty<Notification> notificationsProperty;
	private ListProperty<Game> watchlistProperty;
	private ListProperty<Game> searchResultsProperty;
	
	private StringProperty browsePageSearchTermProperty;
	private ObjectProperty<Game> browsePageSelectedGameProperty;
	
	private static ViewModel viewModel;
	
	/**
	 * Gets the singleton global view model.
	 * 
	 * @precondition none
	 * @postcondition none
	 * 
	 * @return the view model.
	 */
	public static ViewModel get() {
		if (viewModel == null) {
			viewModel = new ViewModel();
		}
		
		return viewModel;
	}
	
	/**
	 * Creates a new ViewModel object.
	 * 
	 * @precondition none
	 * @postcondition userProperty().getValue() == null && notificationsProperty().getValue().size() == 0
	 * 				 && watchlistProperty().getValue().size() == 0 && searchResultsProperty.getValue().size() == 0
	 * 				 && browsePageSearchTermProperty().getValue().equals("")
	 */
	private ViewModel() {
		this.initializeProperties();
	}
	
	/**
	 * Adds a single game to the user's watchlist and updates the watchlist
	 * property accordingly.
	 * 
	 * @precondition none
	 * @postcondition if userProperty().getValue() != null, 
	 * 				  then watchlistProperty().getValue().size() == watchlistProperty().getValue().size()@prev + 1
	 */
	public void addGameToWatchlist() {
		User currentUser = this.userProperty.getValue();
		if (currentUser != null) {
			Watchlist userWatchlist = currentUser.getWatchlist();
			userWatchlist.add(this.browsePageSelectedGameProperty.getValue());
			this.watchlistProperty.setValue(FXCollections.observableArrayList(userWatchlist));
		}
	}
	
	/**
	 * Performs a search using the steam api and the value within searchTermProperty(). The results
	 * are then set to the searchResultsProperty().
	 * 
	 * @precondition none
	 * @postcondition none
	 */
	public void performSearch() {
		SteamSearch api = new SteamSearch(this.browsePageSearchTermProperty.getValue());
		try {
			this.searchResultsProperty.setValue(FXCollections.observableArrayList(api.query()));
		} catch (IOException e) {
			// TODO: handle failed search.
		}
	}
	
	/**
	 * Performs a search using the steam api against all games in the user's watchlist. If any
	 * of the games on the watchlist are on sale or have a price lower than the price threshold set on 
	 * the game, then notifications are generated and set to the notificationsProperty().
	 * 
	 * @precondition none
	 * @postcondition none
	 */
	public void loadNotifications() {
		NotificationCheck api = new NotificationCheck(this.userProperty.getValue().getWatchlist());
		try {
			this.notificationsProperty.setValue(FXCollections.observableArrayList(api.query()));
		} catch (IOException e) {
			// TODO: handle failed notification load
		}
	}
	
	/**
	 * Logs in the user.
	 * 
	 * @precondition none
	 * @postcondition userProperty().getValue() != null 
	 *             && watchlistProperty().getValue().size() == userProperty().getValue().getWatchlist().size()
	 * 
	 * NOTE: FOR NOW THERE IS NO USER CREATION OR ACTUAL LOGIN, WE CAN SIMPLY BYPASS THIS STAGE FOR NOW.
	 * EACH TIME THE APP IS STARTED WHEN THEY HIT LOGIN WE CAN CALL THIS METHOD TO INITIALIZE THE SYSTEM.
	 */
	public void loginUser() {
		User newUser = new User(new Credentials("TestUser", "1234"));
		this.userProperty.setValue(newUser);
		this.watchlistProperty.setValue(FXCollections.observableArrayList(newUser.getWatchlist()));
	}
	
	/**
	 * Gets the userProperty for the current user of the system.
	 * 
	 * @precondition none
	 * @postcondition none
	 * 
	 * @return the userProperty for the current user of the system.
	 */
	public ObjectProperty<User> userProperty() {
		return this.userProperty;
	}

	/**
	 * Gets the notificationsProperty. This contains a list of notifications that
	 * can be displayed within the UI.
	 * 
	 * @precondition none
	 * @postcondition none
	 * 
	 * @return the notificationsProperty.
	 */
	public ListProperty<Notification> notificationsProperty() {
		return this.notificationsProperty;
	}

	/**
	 * Gets the watchlistProperty. This contains a list of games that can be
	 * displayed within the UI.
	 * 
	 * @precondition none
	 * @postcondition none
	 * 
	 * @return the watchlistProperty.
	 */
	public ListProperty<Game> watchlistProperty() {
		return this.watchlistProperty;
	}

	/**
	 * Gets the searchResultsProperty. This contains a list of games that can be 
	 * displayed within the UI.
	 * 
	 * @precondition none
	 * @postcondition none
	 * 
	 * @return the searchResultsProperty.
	 */
	public ListProperty<Game> searchResultsProperty() {
		return this.searchResultsProperty;
	}
	
	/**
	 * Gets the searchTermProperty. This is the value that the user enters
	 * into the search field on the search page. This will be used to pull results
	 * from steam.
	 * 
	 * @precondition none
	 * @postcondition none
	 * 
	 * @return the searchTermProperty.
	 */
	public StringProperty browsePageSearchTermProperty() {
		return this.browsePageSearchTermProperty;
	}
	
	/**
	 * Gets the selected game property for the currently selected game on the browse page.
	 * 
	 * @precondition none
	 * @postcondition none
	 * 
	 * @return the selected game property for the currently selected game on the browse page.
	 */
	public ObjectProperty<Game> browsePageSelectedGameProperty() {
		return this.browsePageSelectedGameProperty;
	}

	private void initializeProperties() {
		this.userProperty = new SimpleObjectProperty<User>();
		this.notificationsProperty = new SimpleListProperty<Notification>();
		this.watchlistProperty = new SimpleListProperty<Game>();
		this.searchResultsProperty = new SimpleListProperty<Game>();
		this.browsePageSearchTermProperty = new SimpleStringProperty("");
		this.browsePageSelectedGameProperty = new SimpleObjectProperty<Game>();
	}
}
