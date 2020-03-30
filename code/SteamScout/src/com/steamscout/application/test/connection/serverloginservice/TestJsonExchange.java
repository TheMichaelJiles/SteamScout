package com.steamscout.application.test.connection.serverloginservice;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import com.steamscout.application.connection.ServerLoginService;
import com.steamscout.application.connection.exceptions.InvalidCredentialsException;
import com.steamscout.application.model.user.Credentials;
import com.steamscout.application.model.user.User;
import com.steamscout.application.model.game_data.Game;

public class TestJsonExchange {

	private class TestServerLoginService extends ServerLoginService {
		
		public User interpretJsonString(Credentials credentials, String receivingJson) throws InvalidCredentialsException {
			this.setCredentials(credentials);
			return super.interpretJsonString(receivingJson);
		}
		
		public String getJsonString(Credentials credentials) {
			this.setCredentials(credentials);
			return super.getSendingJsonString();
		}
		
	}
	
	@Test
	public void testThrowsExceptionOnUnsuccessfulLogin() {
		TestServerLoginService service = new TestServerLoginService();
		Credentials credentials = new Credentials("twhal", "1234");
		String receivedJson = "{\"result\": false, \"watchlist\": []}";
		assertThrows(InvalidCredentialsException.class, () -> service.interpretJsonString(credentials, receivedJson));
	}
	
	@Test
	public void testFormsValidUserOnSuccessfulLogin() throws InvalidCredentialsException {
		TestServerLoginService service = new TestServerLoginService();
		Credentials credentials = new Credentials("twhal", "1234");
		String receivedJson = "{\"result\": true, \"watchlist\": [{\"steamid\": 5, \"title\": \"test-game\", \"actualprice\": 39.99, \"initialprice\": 59.99, \"onsale\": true, \"onsale_selected\": true, \"targetprice_selected\": false, \"targetprice_criteria\": 0.0}]}";
		User resultingUser = service.interpretJsonString(credentials, receivedJson);
		
		List<Game> games = new ArrayList<Game>(resultingUser.getWatchlist());
		assertAll(() -> assertEquals("twhal", resultingUser.getCredentials().getUsername()),
				() -> assertEquals("1234", resultingUser.getCredentials().getPassword()),
				() -> assertEquals(1, resultingUser.getWatchlist().size()),
				() -> assertEquals(5, games.get(0).getAppId()),
				() -> assertEquals("test-game", games.get(0).getTitle()),
				() -> assertEquals(39.99, games.get(0).getCurrentPrice(), 0.000001),
				() -> assertEquals(59.99, games.get(0).getInitialPrice(), 0.000001),
				() -> assertEquals(true, games.get(0).isOnSale()),
				() -> assertEquals(true, resultingUser.getWatchlist().getNotificationCriteria(games.get(0)).shouldNotifyOnSale()),
				() -> assertEquals(false, resultingUser.getWatchlist().getNotificationCriteria(games.get(0)).shouldNotifyWhenBelowTargetPrice()),
				() -> assertEquals(0.0, resultingUser.getWatchlist().getNotificationCriteria(games.get(0)).getTargetPrice()));
	}
	
	@Test
	public void testFormsValidJsonToSendToServer() {
		TestServerLoginService service = new TestServerLoginService();
		Credentials credentials = new Credentials("twhal", "1234");
		String json = service.getJsonString(credentials);
		
		JSONObject jsonobj = new JSONObject(json);
		
		assertAll(() -> assertEquals("authenticate", jsonobj.getString("type")),
				() -> assertEquals("twhal", jsonobj.getJSONObject("data").getJSONObject("user").getString("username")),
				() -> assertEquals("1234", jsonobj.getJSONObject("data").getJSONObject("user").getString("password")));
	}

}
