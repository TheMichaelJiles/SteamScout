package com.steamscout.application.view;

import com.steamscout.application.model.user.User;

import java.util.Map;

import com.steamscout.application.connection.interfaces.CreateAccountService;
import com.steamscout.application.connection.interfaces.LoginService;
import com.steamscout.application.connection.interfaces.WatchlistAdditionService;
import com.steamscout.application.connection.interfaces.WatchlistModificationService;
import com.steamscout.application.connection.interfaces.WatchlistRemovalService;
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
 * @author Thomas Whaley, Nathan Lightholder
 *
 */
public abstract class ViewModel {

	private ObjectProperty<User> userProperty;
	private ListProperty<Notification> notificationsProperty;
	private ListProperty<Game> watchlistProperty;
	private ObjectProperty<Game> watchlistPageSelectedGameProperty;
	private ListProperty<Game> searchResultsProperty;

	private StringProperty browsePageSearchTermProperty;
	private ObjectProperty<Game> browsePageSelectedGameProperty;

	private StringProperty loginPageUsernameProperty;
	private StringProperty loginPagePasswordProperty;
	private StringProperty loginPageErrorProperty;

	private StringProperty createAccountPageUsernameProperty;
	private StringProperty createAccountPagePasswordProperty;
	private StringProperty createAccountPageEmailProperty;
	private StringProperty createAccountPageErrorProperty;

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
			viewModel = new BehaviorViewModel();
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
	protected ViewModel() {
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
	public abstract void insertSteamData(Map<String, Integer> steamData);

	/**
	 * Performs a search on the current user's watchlist for games that match the
	 * given search term. Updates the watchlistProperty with the results.
	 * 
	 * @precondition searchTerm != null
	 * @postcondition watchlistProperty().getValue().containsAll(
	 *                userProperty().getValue().getWatchlist().getMatchingGames(searchTerm))
	 * 
	 * @param searchTerm the term to search the watchlist with.
	 */
	public abstract void performWatchlistSearch(String searchTerm);

	/**
	 * Resets the watchlistProperty to contain all items in the current users
	 * watchlist.
	 * 
	 * @precondition none
	 * @postcondition watchlistProperty().getValue().containsAll(userProperty().getValue().getWatchlist())
	 */
	public abstract void resetWatchlistProperty();

	/**
	 * Sets the notification criteria for the currently selected game on the
	 * watchlist page.
	 * 
	 * @precondition userProperty().getValue() != null
	 * @postcondition none
	 * 
	 * @param onSale         notify when on sale.
	 * @param belowThreshold notify when below threshold.
	 * @param targetPrice    threshold target price.
	 */
	public abstract void setSelectedGameNotificationCriteria(WatchlistModificationService modificationService,
			boolean onSale, boolean belowThreshold, double targetPrice);

	/**
	 * Adds the currently selected game on the browse page to the user's watchlist
	 * and updates the watchlist property accordingly.
	 * 
	 * @precondition none
	 * @postcondition if userProperty().getValue() != null, then
	 *                watchlistProperty().getValue().size() ==
	 *                watchlistProperty().getValue().size()@prev + 1
	 */
	public abstract boolean addSelectedGameToWatchlist(WatchlistAdditionService additionSystem);

	/**
	 * Removes the game selected in the watchlist page listview and watchlist
	 * 
	 * @precondition none
	 * @postcondition watchlistProperty().getValue().size() ==
	 *                watchlistProperty().getValue().size()@prev - 1
	 */
	public abstract void removeSelectedGameFromWatchlist();

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
	public abstract boolean addGameToWatchlist(Game game);

	/**
	 * Removes the game specified from the watchlist
	 * 
	 * @precondition game != null
	 * @postcondition if userProperty().getValue() != null, then
	 *                watchlistProperty().getValue().size() ==
	 *                watchlistProperty().getValue().size()@prev - 1
	 * 
	 * @param removalService the connection to the server for watchlist removal
	 * @param game           the game to be removed from the watchlist
	 */
	public abstract void removeGameFromWatchlist(WatchlistRemovalService removalService, Game game);

	/**
	 * Performs a search using the steam api and the value within
	 * searchTermProperty(). The results are then set to the
	 * searchResultsProperty().
	 * 
	 * @precondition none
	 * @postcondition none
	 */
	public abstract void performSearch();

	/**
	 * Logs in the user.
	 * 
	 * @precondition loginsystem != null
	 * @postcondition userProperty().getValue() != null &&
	 *                watchlistProperty().getValue().size() ==
	 *                userProperty().getValue().getWatchlist().size()
	 * 
	 * @param loginsystem the system used to login the user.
	 * @return whether or not the login was successful.
	 */
	public abstract boolean loginUser(LoginService loginsystem);

	/**
	 * Creates an account.
	 * 
	 * @precondition accountsystem != null
	 * @postcondition if !createUserAccount(CreateAccountService), then
	 *                createAccountPageErrorProperty().getValue().equals("Invalid
	 *                Account")
	 * 
	 * @param accountsystem the system used to create the account.
	 * @return true if the account was created, false otherwise.
	 */
	public abstract boolean createUserAccount(CreateAccountService accountsystem);

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
	 * Gets the watclistPage selected game property. Which holds the selected item
	 * in the list view.
	 * 
	 * @precondition none
	 * @postcondition none
	 * 
	 * @return watchlistPageSelectedGameProperty
	 */
	public ObjectProperty<Game> watchlistPageSelectedGameProperty() {
		return this.watchlistPageSelectedGameProperty;
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
	 * Gets the login page's username property. This is the value that the user
	 * enters into the username text field on the login page.
	 * 
	 * @precondition none
	 * @postcondition none
	 * 
	 * @return the login page's username property.
	 */
	public StringProperty loginPageUsernameProperty() {
		return this.loginPageUsernameProperty;
	}

	/**
	 * Gets the login page's password property. This is the value that the user
	 * enters into the password text field on the login page.
	 * 
	 * @precondition none
	 * @postcondition none
	 * 
	 * @return the login page's password property.
	 */
	public StringProperty loginPagePasswordProperty() {
		return this.loginPagePasswordProperty;
	}

	/**
	 * Gets the login page's error property. This is the property that is set when
	 * the user tries to log in with invalid credentials. This should be displayed
	 * to the user upon invalid login.
	 * 
	 * @precondition none
	 * @postcondition none
	 * 
	 * @return the login page's error property.
	 */
	public StringProperty loginPageErrorProperty() {
		return this.loginPageErrorProperty;
	}

	/**
	 * Gets the create account page's username property. This is the property that
	 * is set when the user enters text in the username textfield on the create
	 * account page.
	 * 
	 * @precondition none
	 * @postcondition none
	 * 
	 * @return the create account page's username property
	 */
	public StringProperty createAccountPageUsernameProperty() {
		return this.createAccountPageUsernameProperty;
	}

	/**
	 * Gets the create account page's password property. This is the property that
	 * is set when the user enters text in the password textfield on the create
	 * account page.
	 * 
	 * @precondition none
	 * @postcondition none
	 * 
	 * @return the create account page's password property.
	 */
	public StringProperty createAccountPagePasswordProperty() {
		return this.createAccountPagePasswordProperty;
	}

	/**
	 * Gets the create account page's email property. This is the property that is
	 * set when the user enters text in the email text field on the create account
	 * page.
	 * 
	 * @precondition none
	 * @postcondition none
	 * 
	 * @return the create account page's email property.
	 */
	public StringProperty createAccountPageEmailProperty() {
		return this.createAccountPageEmailProperty;
	}

	/**
	 * Gets the create account page's error property. This is the property that is
	 * set when the user tries to create an invalid account.
	 * 
	 * @precondition none
	 * @postcondition none
	 * 
	 * @return the create account page's error property.
	 */
	public StringProperty createAccountPageErrorProperty() {
		return this.createAccountPageErrorProperty;
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

	/**
	 * Sets the steam games manager.
	 * 
	 * @precondition none
	 * @postcondition getSteamGames().equals(steamGames)
	 * 
	 * @param steamGames the steam games manager.
	 */
	public void setSteamGames(SteamGames steamGames) {
		this.steamGames = steamGames;
	}

	private void initializeProperties() {
		this.userProperty = new SimpleObjectProperty<User>();
		this.notificationsProperty = new SimpleListProperty<Notification>(FXCollections.emptyObservableList());
		this.watchlistProperty = new SimpleListProperty<Game>(FXCollections.emptyObservableList());
		this.searchResultsProperty = new SimpleListProperty<Game>(FXCollections.emptyObservableList());
		this.browsePageSearchTermProperty = new SimpleStringProperty("");
		this.browsePageSelectedGameProperty = new SimpleObjectProperty<Game>();
		this.watchlistPageSelectedGameProperty = new SimpleObjectProperty<Game>();
		this.loginPageUsernameProperty = new SimpleStringProperty("");
		this.loginPagePasswordProperty = new SimpleStringProperty("");
		this.loginPageErrorProperty = new SimpleStringProperty();
		this.createAccountPageUsernameProperty = new SimpleStringProperty("");
		this.createAccountPagePasswordProperty = new SimpleStringProperty("");
		this.createAccountPageEmailProperty = new SimpleStringProperty("");
		this.createAccountPageErrorProperty = new SimpleStringProperty();
	}
}
