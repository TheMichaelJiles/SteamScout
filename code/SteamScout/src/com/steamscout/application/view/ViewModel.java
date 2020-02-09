package com.steamscout.application.view;

import com.steamscout.application.model.user.Credentials;
import com.steamscout.application.model.user.User;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

import com.steamscout.application.model.api.tasks.NotificationChecker;
import com.steamscout.application.model.game_data.Game;
import com.steamscout.application.model.game_data.SteamGames;
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
 * Acts as the intermediary between the view and model packages in SteamScout.
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

	private SteamGames steamGames;

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
	 * @postcondition userProperty().getValue() == null &&
	 *                notificationsProperty().getValue().size() == 0 &&
	 *                watchlistProperty().getValue().size() == 0 &&
	 *                searchResultsProperty.getValue().size() == 0 &&
	 *                browsePageSearchTermProperty().getValue().equals("") &&
	 *                browsePageSelectedGameProperty().getValue() == null &&
	 *                getSteamGames() != null
	 */
	private ViewModel() {
		this.initializeProperties();

		this.steamGames = new SteamGames();
	}

	/**
	 * Inserts the specified data into the this view model for use by the SteamGames
	 * object. The purpose of this is to guarantee we only have to make the api call
	 * for getting names and ids once - on application startup during loading
	 * screen. This method should only be called once. Successive calls to this
	 * method will result in IllegalArgumentExceptions -> see
	 * com.application.model.game_data.SteamGames
	 * 
	 * @precondition steamData != null
	 * @postcondition !getSteamGames().getTitles().isEmpty() &&
	 *                !getSteamGames().getIds().isEmpty()
	 * 
	 * @param steamData the data to give the SteamData object.
	 */
	public void insertSteamData(Map<String, Integer> steamData) {
		if (steamData == null) {
			throw new IllegalArgumentException("steamData should not be null.");
		}

		this.steamGames.initializeGames(steamData);
	}

	/**
	 * Adds the currently selected game on the browse page to the user's watchlist
	 * and updates the watchlist property accordingly.
	 * 
	 * @precondition none
	 * @postcondition if userProperty().getValue() != null, then
	 *                watchlistProperty().getValue().size() ==
	 *                watchlistProperty().getValue().size()@prev + 1
	 */
	public void addSelectedGameToWatchlist() {
		this.addGameToWatchlist(this.browsePageSelectedGameProperty.getValue());
	}

	/**
	 * Adds the specified game to the user's watchlist and updates the watchlist
	 * property accordingly.
	 * 
	 * @precondition game != null
	 * @postcondition if userProperty().getValue() != null, then
	 *                watchlistProperty().getValue().size() ==
	 *                watchlistProperty().getValue().size()@prev + 1
	 * 
	 * @param game the game to add to the user's watchlist.
	 */
	public void addGameToWatchlist(Game game) {
		if (game == null) {
			throw new IllegalArgumentException("game should not be null.");
		}

		User currentUser = this.userProperty.getValue();
		if (currentUser != null) {
			Watchlist userWatchlist = currentUser.getWatchlist();
			userWatchlist.add(game);
			this.watchlistProperty.setValue(FXCollections.observableArrayList(userWatchlist));
		}
	}

	/**
	 * Performs a search using the steam api and the value within
	 * searchTermProperty(). The results are then set to the
	 * searchResultsProperty().
	 * 
	 * @precondition none
	 * @postcondition none
	 */
	public void performSearch() {
		try {
			Collection<Game> searchResults = this.steamGames.getMatchingGames(this.browsePageSearchTermProperty.getValue());
			this.searchResultsProperty.setValue(FXCollections.observableArrayList(searchResults));
		} catch (InterruptedException e) {
			// TODO: handle failed search. NOTE: This is very unlikely to occur.
		}
	}

	/**
	 * Performs a search using the steam api against all games in the user's
	 * watchlist. If any of the games on the watchlist are on sale or have a price
	 * lower than the price threshold set on the game, then notifications are
	 * generated and set to the notificationsProperty().
	 * 
	 * NOTE: WE NEED TO DISCUSS THIS. THIS API CALL MUST BE HIGHLY CONTROLLED.
	 * 
	 * @precondition none
	 * @postcondition none
	 */
	private void loadNotifications() {
		try {
			Collection<Notification> notifications = NotificationChecker
					.query(this.userProperty.getValue().getWatchlist());
			this.notificationsProperty.setValue(FXCollections.observableArrayList(notifications));
		} catch (IOException e) {
			// TODO: handle failed notification load
		}
	}

	/**
	 * Logs in the user.
	 * 
	 * @precondition none
	 * @postcondition userProperty().getValue() != null &&
	 *                watchlistProperty().getValue().size() ==
	 *                userProperty().getValue().getWatchlist().size()
	 * 
	 *                NOTE: FOR NOW THERE IS NO USER CREATION OR ACTUAL LOGIN, WE
	 *                CAN SIMPLY BYPASS THIS STAGE FOR NOW. EACH TIME THE APP IS
	 *                STARTED WHEN THEY HIT LOGIN WE CAN CALL THIS METHOD TO
	 *                INITIALIZE THE SYSTEM.
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
	 * Gets the searchTermProperty. This is the value that the user enters into the
	 * search field on the search page. This will be used to pull results from
	 * steam.
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
	 * Gets the selected game property for the currently selected game on the browse
	 * page.
	 * 
	 * @precondition none
	 * @postcondition none
	 * 
	 * @return the selected game property for the currently selected game on the
	 *         browse page.
	 */
	public ObjectProperty<Game> browsePageSelectedGameProperty() {
		return this.browsePageSelectedGameProperty;
	}

	/**
	 * Gets the steam games manager.
	 * 
	 * @precondition none
	 * @postcondition none
	 * 
	 * @return the steam games manager.
	 */
	public SteamGames getSteamGames() {
		return this.steamGames;
	}

	private void initializeProperties() {
		this.userProperty = new SimpleObjectProperty<User>();
		this.notificationsProperty = new SimpleListProperty<Notification>(FXCollections.emptyObservableList());
		this.watchlistProperty = new SimpleListProperty<Game>(FXCollections.emptyObservableList());
		this.searchResultsProperty = new SimpleListProperty<Game>(FXCollections.emptyObservableList());
		this.browsePageSearchTermProperty = new SimpleStringProperty("");
		this.browsePageSelectedGameProperty = new SimpleObjectProperty<Game>();
	}
}
