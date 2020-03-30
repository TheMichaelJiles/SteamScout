package com.steamscout.application.connection.interfaces;

import com.steamscout.application.model.notification.NotificationList;
import com.steamscout.application.model.user.Credentials;

/**
 * Defines behavior for the notification service
 * 
 * @author Nathan Lightholder
 */
public interface NotificationService {
	
	/**
	 * Attempts to check the server for notifications to alert the user of
	 * games that meet sale criteria on their watchlist
	 * 
	 * @precondition credentials != null
	 * @return a list of notifications that meet sale criteria
	 * 
	 * @param credentials, The credentials of the user receiving the notifications
	 */
	NotificationList UpdateNotifications(Credentials credentials);

}
