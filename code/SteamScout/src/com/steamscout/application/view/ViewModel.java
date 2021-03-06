package com.steamscout.application.view;

import com.steamscout.application.model.user.User;

import java.util.List;

import com.steamscout.application.connection.interfaces.CreateAccountService;
import com.steamscout.application.connection.interfaces.GameFetchService;
import com.steamscout.application.connection.interfaces.LinkWishlistService;
import com.steamscout.application.connection.interfaces.LoginService;
import com.steamscout.application.connection.interfaces.NotificationService;
import com.steamscout.application.connection.interfaces.WatchlistAdditionService;
import com.steamscout.application.connection.interfaces.WatchlistFetchService;
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
	 * Determines whether the specified game has had notification criteria set or not.
	 * 
	 * @precondition game != null
	 * @postcondition none
	 * 
	 * @param game the game to check for notification criteria.
	 * @return true if the game has had notification criteria set; false otherwise.
	 */
	public abstract boolean containsNotificationCriteria(Game game);
	
	/**
	 * Gets a list of all steam games that are relevant to the specified text.
	 * 
	 * @precondition none
	 * @postcondition none
	 * 
	 * @param text the text to make the prediction from.
	 * @return a list of all steam games that are relevant to the specified text.
	 */
	public abstract List<String> makeBrowsePagePrediction(String text);
	
	/**
	 * Gets a list of all watchlist games that are relevant to the specified text.
	 * 
	 * @precondition none
	 * @postcondition none
	 * 
	 * @param text the text to make the prediction from.
	 * @return a list of all watchlist games that are relevant to the specified text.
	 */
	public abstract List<String> makeWatchlistPagePrediction(String text);
	
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
	 * @param service the data to give the SteamData object.
	 */
	public abstract void insertSteamData(GameFetchService service);

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
	 * @param modificationService the interface used to make the modification.
	 * @param onSale         notify when on sale.
	 * @param belowThreshold notify when below threshold.
	 * @param targetPrice    threshold target price.
	 */
	public abstract void setSelectedGameNotificationCriteria(WatchlistModificationService modificationService,
			boolean onSale, boolean belowThreshold, double targetPrice);

	
	/**
	 * Sets the watchlist for the current user using the 
	 * specified WatchlistFetchService.
	 * 
	 * @precondition watchlistSystem != null
	 * @postcondition userProperty().getValue().getWatchlist().equals(
	 * 				  watchlistSystem.fetchWatchlist(userProperty().getValue().getCredentials().getUsername()))
	 * @param watchlistSystem the system used to get the new watchlist.
	 */
	public abstract void loadWatchlist(WatchlistFetchService watchlistSystem);
	
	
	/**
	 * Sets the watchlist for the user with the specified username using the 
	 * specified WatchlistFetchService.
	 * 
	 * @precondition watchlistSystem != null && username != null
	 * @postcondition none
	 * 
	 * @param username the name of the account to get the watchlist for.
	 * @param watchlistSystem the system used to get the new watchlist.
	 * @return the specified username's watchlist.
	 */
	public abstract Watchlist fetchWatchlistFor(String username, WatchlistFetchService watchlistSystem);
	
	/**
	 * Links a Steam wishlist with the currently logged in user's watchlist.
	 * 
	 * @precondition linkingSystem != null
	 * @postcondition userProperty().getValue().getWatchlist().size() >= 
	 * 				  userProperty().getValue().getWatchlist().size()@prev
	 * 
	 * @param linkingSystem the system used to perform the watchlist linking.
	 */
	public abstract void linkWatchlist(LinkWishlistService linkingSystem);
	
	/**
	 * Adds the currently selected game on the browse page to the user's watchlist
	 * and updates the watchlist property accordingly.
	 * 
	 * @precondition none
	 * @postcondition if userProperty().getValue() != null, then
	 *                watchlistProperty().getValue().size() ==
	 *                watchlistProperty().getValue().size()@prev + 1
	 * @param additionSystem the interface used to make the addition.
	 * @return true if the game was succecssfully added; false otherwise.
	 */
	public abstract boolean addSelectedGameToWatchlist(WatchlistAdditionService additionSystem);

	/**
	 * Adds the specified game to the user's watchlist and updates the watchlist property 
	 * accordingly.
	 * 
	 * @precondition none
	 * @postcondition if userProperty().getValue() != null, then
	 *                watchlistProperty().getValue().size() ==
	 *                watchlistProperty().getValue().size()@prev + 1
	 * @param game the game to add.
	 * @param service the interface used to make the addition persistent.
	 * @return true if the addition was successful; false otherwise.
	 */
	public abstract boolean addGameToWatchlist(Game game, WatchlistAdditionService service);
	
	/**
	 * Removes the game selected in the watchlist page listview and watchlist
	 * 
	 * @param removalService the server connection used to remove the game
	 * 
	 * @precondition none
	 * @postcondition watchlistProperty().getValue().size() ==
	 *                watchlistProperty().getValue().size()@prev - 1
	 */
	public abstract void removeSelectedGameFromWatchlist(WatchlistRemovalService removalService);

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
	 * @return true if the game was successfully added; false otherwise.
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
	 * Populates the notification property with updated notifications
	 * 
	 * @precondition notificationService != null
	 * @postcondition notificationsProperty is set to the updated notifications
	 * 
	 * @param notificationSystem system responsible for handling server response
	 * 		  regarding notifications
	 */
	public abstract void populateNotifications(NotificationService notificationSystem);

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

	/**
	 * Initializes all properties to their default values.
	 * 
	 * @precondition none
	 * @postcondition userProperty().getValue() == null &&
	 * 				  notificationsProperty().getValue().size() == 0 &&
	 * 				  watchlistProperty().getValue().size() == 0 &&
	 * 				  searchResultsProperty().getValue().size() == 0 &&
	 * 				  browsePageSearchTermProperty().getValue().equals("") &&
	 * 				  browsePageSelectedGameProperty().getValue() == null &&
	 * 				  watchlistPageSelectedGameProperty().getValue() == null &&
	 * 				  loginPageUsernameProperty().getValue().equals("") &&
	 * 				  loginPagePasswordProperty().getValue().equals("") &&
	 * 				  loginPageErrorProperty().getValue() == null &&
	 * 				  createAccountPageUsernameProperty().getValue().equals("") &&
	 * 				  createAccountPagePasswordProperty().getValue().equals("") &&
	 * 				  createAccountPageEmailProperty().getValue().equals("") &&
	 * 				  createAccountPageErrorProperty().getValue() == null
	 */
	public void initializeProperties() {
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
