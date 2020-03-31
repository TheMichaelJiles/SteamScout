package com.steamscout.application.view;

import java.util.Collection;

import com.steamscout.application.connection.exceptions.InvalidAccountException;
import com.steamscout.application.connection.exceptions.InvalidAdditionException;
import com.steamscout.application.connection.exceptions.InvalidCredentialsException;
import com.steamscout.application.connection.interfaces.CreateAccountService;
import com.steamscout.application.connection.interfaces.GameFetchService;
import com.steamscout.application.connection.interfaces.LoginService;
import com.steamscout.application.connection.interfaces.NotificationService;
import com.steamscout.application.connection.interfaces.WatchlistAdditionService;
import com.steamscout.application.connection.interfaces.WatchlistFetchService;
import com.steamscout.application.connection.interfaces.WatchlistModificationService;
import com.steamscout.application.connection.interfaces.WatchlistRemovalService;
import com.steamscout.application.model.game_data.Game;
import com.steamscout.application.model.game_data.Watchlist;
import com.steamscout.application.model.notification.Notification;
import com.steamscout.application.model.notification.NotificationCriteria;
import com.steamscout.application.model.notification.NotificationList;
import com.steamscout.application.model.user.Credentials;
import com.steamscout.application.model.user.User;

import javafx.collections.FXCollections;

/**
 * Contains the behavior of the view model as it interacts with the model.
 * 
 * @author Thomas Whaley, Nathan Lightholder
 *
 */
public class BehaviorViewModel extends ViewModel {

	protected BehaviorViewModel() {
		super();
	}

	@Override
	public void insertSteamData(GameFetchService service) {
		if (service == null) {
			throw new IllegalArgumentException("Game Fetch service should not be null.");
		}

		this.getSteamGames().initializeGames(service.fetchGames()); 
	}

	@Override
	public boolean addSelectedGameToWatchlist(WatchlistAdditionService additionSystem) {
		if (additionSystem == null) {
			throw new IllegalArgumentException("additionSystem should not be null.");
		}
		User currentUser = this.userProperty().getValue();
		Credentials userCredentials = currentUser.getCredentials();
		Game gameToAdd = this.browsePageSelectedGameProperty().getValue();
		try {
			Watchlist newWatchlist = additionSystem.addGameToWatchlist(userCredentials, gameToAdd);
			currentUser.setWatchlist(newWatchlist);
			if (newWatchlist == null) {
				throw new IllegalArgumentException("New watchlist was null");
			}
			this.watchlistProperty().setValue(FXCollections.observableArrayList(currentUser.getWatchlist()));
			return true;
		} catch (InvalidAdditionException e) {
			System.err.print(e.getMessage());
			return false;
		}
	}

	@Override
	public void removeSelectedGameFromWatchlist(WatchlistRemovalService removalService) {
		this.removeGameFromWatchlist(removalService, this.watchlistPageSelectedGameProperty().getValue());
	}

	@Override
	public void removeGameFromWatchlist(WatchlistRemovalService removalService, Game game) {
		if (game == null) {
			throw new IllegalArgumentException("game should not be null.");
		}
		User currentUser = this.userProperty().getValue();
		if (currentUser != null) {
			removalService.removeGameFromWatchlist(currentUser, game);
			this.watchlistProperty().setValue(FXCollections.observableArrayList(currentUser.getWatchlist()));
		}
	}

	@Override
	public boolean addGameToWatchlist(Game game) {
		if (game == null) {
			throw new IllegalArgumentException("game to add to watchlist should not be null.");
		}

		boolean isSuccessfullyAdded = false;
		User currentUser = this.userProperty().getValue();
		if (currentUser != null) {
			Watchlist userWatchlist = currentUser.getWatchlist();
			isSuccessfullyAdded = userWatchlist.add(game);
			this.watchlistProperty().setValue(FXCollections.observableArrayList(userWatchlist));
		}
		return isSuccessfullyAdded;

	}

	@Override
	public void performSearch() {
		try {
			Collection<Game> searchResults = this.getSteamGames()
					.getMatchingGames(this.browsePageSearchTermProperty().getValue());
			this.searchResultsProperty().setValue(FXCollections.observableArrayList(searchResults));
		} catch (InterruptedException e) {
		}
	}

	@Override
	public boolean loginUser(LoginService loginsystem) {
		if (loginsystem == null) {
			throw new IllegalArgumentException("login system should not be null.");
		}

		Credentials loginCredentials = new Credentials(this.loginPageUsernameProperty().getValue(),
				this.loginPagePasswordProperty().getValue());
		try {
			User loggedInUser = loginsystem.login(loginCredentials);
			this.userProperty().setValue(loggedInUser);
			this.watchlistProperty().setValue(FXCollections.observableArrayList(loggedInUser.getWatchlist()));
			this.loginPageErrorProperty().setValue(null);
			return true;
		} catch (InvalidCredentialsException e) {
			this.loginPageErrorProperty().setValue("Invalid Credentials");
			return false;
		}
	}

	@Override
	public void setSelectedGameNotificationCriteria(WatchlistModificationService modificationService, boolean onSale,
			boolean belowThreshold, double targetPrice) {
		NotificationCriteria criteria = new NotificationCriteria();
		User currentUser = this.userProperty().getValue();
		criteria.shouldNotifyOnSale(onSale);
		criteria.shouldNotifyWhenBelowTargetPrice(belowThreshold);
		criteria.setTargetPrice(targetPrice);
		Game gameToModify;
		if (watchlistPageSelectedGameProperty().getValue() == null) {
			gameToModify = this.browsePageSelectedGameProperty().getValue();
		} else {
			gameToModify = this.watchlistPageSelectedGameProperty().getValue();
		}
		Watchlist newWatchlist = modificationService.modifyGameOnWatchlist(currentUser, gameToModify, criteria);
		currentUser.setWatchlist(newWatchlist);

	}

	@Override
	public void performWatchlistSearch(String searchTerm) {
		Watchlist watchlist = this.userProperty().getValue().getWatchlist();
		Collection<Game> matches = watchlist.getMatchingGames(searchTerm);

		this.watchlistProperty().setValue(FXCollections.observableArrayList(matches));
	}

	@Override
	public void resetWatchlistProperty() {
		Watchlist watchlist = this.userProperty().getValue().getWatchlist();
		this.watchlistProperty().setValue(FXCollections.observableArrayList(watchlist));
	}

	@Override
	public boolean createUserAccount(CreateAccountService accountsystem) {
		if (accountsystem == null) {
			throw new IllegalArgumentException("account system should not be null.");
		}

		Credentials credentials = new Credentials(this.createAccountPageUsernameProperty().getValue(),
				this.createAccountPagePasswordProperty().getValue());
		try {
			accountsystem.createAccount(credentials, this.createAccountPageEmailProperty().getValue());
			this.createAccountPageErrorProperty().setValue(null);
			return true;
		} catch (InvalidAccountException e) {
			this.createAccountPageErrorProperty().setValue("Invalid Account");
			return false;
		}
	}

	@Override
	public void populateNotifications(NotificationService notificationSystem) {
		if (notificationSystem == null) {
			throw new IllegalArgumentException("notification system should not be null.");
		}
		User currentUser = this.userProperty().getValue();
		Credentials userCredentials = currentUser.getCredentials();

		NotificationList notifications = notificationSystem.updateNotifications(userCredentials);
		this.notificationsProperty().setValue(FXCollections.observableArrayList(notifications));
	}

	@Override
	public void loadWatchlist(WatchlistFetchService watchlistSystem) {
		if (watchlistSystem == null) {
			throw new IllegalArgumentException("watchlist system should not be null");
		}

		String username = this.userProperty().getValue().getCredentials().getUsername();
		this.userProperty().getValue().setWatchlist(watchlistSystem.fetchWatchlist(username));
		this.resetWatchlistProperty();
	}

}
