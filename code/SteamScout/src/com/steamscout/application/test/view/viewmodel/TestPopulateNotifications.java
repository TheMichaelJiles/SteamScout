/**
 * 
 */
package com.steamscout.application.test.view.viewmodel;

import org.junit.jupiter.api.AfterEach;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Test;

import com.steamscout.application.connection.interfaces.NotificationService;
import com.steamscout.application.model.game_data.Game;
import com.steamscout.application.model.notification.Notification;
import com.steamscout.application.model.notification.NotificationList;
import com.steamscout.application.model.user.Credentials;
import com.steamscout.application.model.user.User;
import com.steamscout.application.view.ViewModel;

/**
 * @author Nlight160
 *
 */
public class TestPopulateNotifications {
	
	
	private class PassingPopulateNotifications implements NotificationService {

		@Override
		public NotificationList UpdateNotifications(Credentials credentials) {
			NotificationList notifications = new NotificationList();
			notifications.add(new Notification(new Game(00000, "Test")));
			return notifications;
		}
	}
	
	@AfterEach
	public void tearDown() {
		ViewModel vm = ViewModel.get();
		vm.notificationsProperty().clear();
		vm.userProperty().setValue(null);
	}
	
	
	@Test
	public void TestPopulateNotificationsShouldNotTakeNullService() {
		ViewModel vm = ViewModel.get();
		assertThrows(IllegalArgumentException.class, () -> vm.populateNotifications(null));
	}
	
	@Test
	public void TestPopulateNotificationsAddsNotificationsToProperty() {
		PassingPopulateNotifications service = new PassingPopulateNotifications();
		ViewModel.get().userProperty().set(new User(new Credentials("TestName", "TestPassword")));
		ViewModel.get().populateNotifications(service);
		
		assertFalse(ViewModel.get().notificationsProperty().isEmpty());
	}

}
