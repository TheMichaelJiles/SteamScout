package com.steamscout.application.view;

import java.util.Collection;
import java.util.Map;

import com.steamscout.application.connection.exceptions.InvalidAccountException;
import com.steamscout.application.connection.exceptions.InvalidCredentialsException;
import com.steamscout.application.connection.interfaces.CreateAccountService;
import com.steamscout.application.connection.interfaces.LoginService;
import com.steamscout.application.connection.interfaces.WatchlistAdditionService;
import com.steamscout.application.model.game_data.Game;
import com.steamscout.application.model.game_data.Watchlist;
import com.steamscout.application.model.notification.NotificationCriteria;
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
	public void insertSteamData(Map<String, Integer> steamData) {
		if (steamData == null) {
			throw new IllegalArgumentException("steamData should not be null.");
		}

		this.getSteamGames().initializeGames(steamData);
	}

	@Override
	public boolean addSelectedGameToWatchlist(WatchlistAdditionService additionSystem) {
		if (additionSystem == null) {
			throw new IllegalArgumentException("additionSystem should not be null.");
		}
		User currentUser = this.userProperty().getValue();
		Credentials userCredentials = currentUser.getCredentials();
		Game gameToAdd = this.browsePageSelectedGameProperty().getValue();
		Watchlist newWatchlist = additionSystem.addGameToWatchlist(userCredentials, gameToAdd);
		currentUser.setWatchlist(newWatchlist);
		if (newWatchlist == null) {
			throw new IllegalArgumentException("New watchlist was null");
		}
		this.watchlistProperty().setValue(FXCollections.observableArrayList(currentUser.getWatchlist()));
		return true;
	}
	
	@Override
	public void removeSelectedGameFromWatchlist() {
		this.removeGameFromWatchlist(this.watchlistPageSelectedGameProperty().getValue());
	}
	
	@Override
	public void removeGameFromWatchlist(Game game) {
		if (game == null) {
			throw new IllegalArgumentException("game should not be null.");
		}

		User currentUser = this.userProperty().getValue();
		if (currentUser != null) {
			Watchlist userWatchlist = currentUser.getWatchlist();
			userWatchlist.remove(game);
			this.watchlistProperty().setValue(FXCollections.observableArrayList(userWatchlist));
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
			Collection<Game> searchResults = this.getSteamGames().getMatchingGames(this.browsePageSearchTermProperty().getValue());
			this.searchResultsProperty().setValue(FXCollections.observableArrayList(searchResults));
		} catch (InterruptedException e) {
		}
	}

	@Override
	public boolean loginUser(LoginService loginsystem) {
		if (loginsystem == null) {
			throw new IllegalArgumentException("login system should not be null.");
		}
		
		Credentials loginCredentials = new Credentials(this.loginPageUsernameProperty().getValue(), this.loginPagePasswordProperty().getValue());
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
	public void setSelectedGameNotificationCriteria(boolean onSale, boolean belowThreshold, double targetPrice) {
		Watchlist watchlist = this.userProperty().getValue().getWatchlist();
		
		NotificationCriteria criteria = new NotificationCriteria();
		criteria.shouldNotifyOnSale(onSale);
		criteria.shouldNotifyWhenBelowTargetPrice(belowThreshold);
		criteria.setTargetPrice(targetPrice);
		
		if (watchlistPageSelectedGameProperty().getValue() == null) {
			watchlist.putNotificationCriteria(this.browsePageSelectedGameProperty().getValue(), criteria);
		} else {
			watchlist.putNotificationCriteria(this.watchlistPageSelectedGameProperty().getValue(), criteria);
		}
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
		
		Credentials credentials = new Credentials(this.createAccountPageUsernameProperty().getValue(), this.createAccountPagePasswordProperty().getValue());
		try {
			accountsystem.createAccount(credentials, this.createAccountPageEmailProperty().getValue());
			this.createAccountPageErrorProperty().setValue(null);
			return true;
		} catch (InvalidAccountException e) {
			this.createAccountPageErrorProperty().setValue("Invalid Account");
			return false;
		}
	}
	
	
}
