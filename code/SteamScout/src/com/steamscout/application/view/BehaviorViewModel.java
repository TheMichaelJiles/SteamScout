package com.steamscout.application.view;

import java.util.Collection;
import java.util.List;

import com.steamscout.application.connection.exceptions.InvalidAccountException;
import com.steamscout.application.connection.exceptions.InvalidAdditionException;
import com.steamscout.application.connection.exceptions.InvalidCredentialsException;
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
import com.steamscout.application.model.game_data.Watchlist;
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
		Game gameToAdd = this.browsePageSelectedGameProperty().getValue();
		return this.addGameToWatchlist(gameToAdd, additionSystem);
	}

	@Override
	public void removeSelectedGameFromWatchlist(WatchlistRemovalService removalService) {
		this.removeGameFromWatchlist(removalService, this.watchlistPageSelectedGameProperty().getValue());
	}

	@Override
	public void removeGameFromWatchlist(WatchlistRemovalService removalService, Game game) {
		if (game == null) {
			throw new IllegalArgumentException("game shouldn't be null.");
		}
		User currentUser = this.userProperty().getValue();
		if (currentUser != null) {
			currentUser.setWatchlist(removalService.removeGameFromWatchlist(currentUser, game));
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
		this.resetWatchlistProperty();
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
		String username = this.userProperty().getValue().getCredentials().getUsername();
		this.userProperty().getValue().setWatchlist(this.fetchWatchlistFor(username, watchlistSystem));
		this.resetWatchlistProperty();
	}
	
	@Override
	public Watchlist fetchWatchlistFor(String username, WatchlistFetchService watchlistSystem) {
		if (username == null) {
			throw new IllegalArgumentException("username should not be null here");
		}
		if (watchlistSystem == null) {
			throw new IllegalArgumentException("watchlist system should not be null");
		}
		
		return watchlistSystem.fetchWatchlist(username);
	}

	@Override
	public void linkWatchlist(LinkWishlistService linkingSystem) {
		if (linkingSystem == null) {
			throw new IllegalArgumentException("linking system should not be null.");
		}
		
		String username = this.userProperty().getValue().getCredentials().getUsername();
		Watchlist result = linkingSystem.linkSteamWishlist(username);
		if (result != null) {
			this.userProperty().getValue().setWatchlist(result);
			this.resetWatchlistProperty();
		}
	}

	@Override
	public boolean containsNotificationCriteria(Game game) {
		if (game == null) {
			throw new IllegalArgumentException("game should not be null.");
		}
		
		boolean containsCriteria = false;
		Watchlist currentWatchlist = this.userProperty().getValue().getWatchlist();
		NotificationCriteria criteria = currentWatchlist.getNotificationCriteria(game);
		if (criteria != null) {
			containsCriteria = !criteria.isDefault();
		}
		return containsCriteria;
	}

	@Override
	public List<String> makeBrowsePagePrediction(String text) {
		return this.getSteamGames().makePrediction(text);
	}

	@Override
	public List<String> makeWatchlistPagePrediction(String text) {
		return this.userProperty().getValue().getWatchlist().makePrediction(text);
	}

	@Override
	public boolean addGameToWatchlist(Game game, WatchlistAdditionService service) {
		User currentUser = this.userProperty().getValue();
		Credentials userCredentials = currentUser.getCredentials();
		try {
			Watchlist newWatchlist = service.addGameToWatchlist(userCredentials, game);
			currentUser.setWatchlist(newWatchlist);
			if (newWatchlist == null) {
				throw new IllegalArgumentException("New watchlist was null");
			}
			this.watchlistProperty().setValue(FXCollections.observableArrayList(currentUser.getWatchlist()));
			return true;
		} catch (InvalidAdditionException e) {
			return false;
		}
	}

}
