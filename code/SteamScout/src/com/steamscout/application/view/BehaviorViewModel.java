package com.steamscout.application.view;

import java.util.Collection;
import java.util.Map;

import com.steamscout.application.model.game_data.Game;
import com.steamscout.application.model.game_data.Watchlist;
import com.steamscout.application.model.notification.NotificationCriteria;
import com.steamscout.application.model.user.Credentials;
import com.steamscout.application.model.user.User;
import com.steamscout.application.util.PageConnectionUtility;

import javafx.collections.FXCollections;
import javafx.stage.Stage;

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
	public void addSelectedGameToWatchlist() {
		this.addGameToWatchlist(this.browsePageSelectedGameProperty().getValue());
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
	public void addGameToWatchlist(Game game) {
		if (game == null) {
			throw new IllegalArgumentException("game to add to watchlist should not be null.");
		}

		User currentUser = this.userProperty().getValue();
		if (currentUser != null) {
			Watchlist userWatchlist = currentUser.getWatchlist();
			userWatchlist.add(game);
			this.watchlistProperty().setValue(FXCollections.observableArrayList(userWatchlist));
		}
	}

	@Override
	public void performSearch() {
		try {
			Collection<Game> searchResults = this.getSteamGames().getMatchingGames(this.browsePageSearchTermProperty().getValue());
			this.searchResultsProperty().setValue(FXCollections.observableArrayList(searchResults));
		} catch (InterruptedException e) {
			// TODO: handle failed search. NOTE: This is very unlikely to occur.
		}
	}

	@Override
	public void loginUser() {
		User newUser = new User(new Credentials("TestUser", "1234"));
		this.userProperty().setValue(newUser);
		this.watchlistProperty().setValue(FXCollections.observableArrayList(newUser.getWatchlist()));
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
	
	
}
