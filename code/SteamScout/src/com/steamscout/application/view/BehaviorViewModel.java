package com.steamscout.application.view;

import java.util.Collection;
import java.util.Map;

import com.steamscout.application.model.game_data.Game;
import com.steamscout.application.model.game_data.Watchlist;
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
			throw new IllegalArgumentException("game should not be null.");
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
	
	
}
