/**
 * 
 */
package com.steamscout.application.model.notification;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * NotificationList functions as a collection for Notifications
 * 
 * @author Nathan Lightholder
 *
 */
public class NotificationList implements Collection<Notification> {

	private List<Notification> notifications;

	/**
	 * Creates a new NotificationList object.
	 * 
	 * @precondition none
	 * @postcondition size() == 0
	 */
	public NotificationList() {
		this.notifications = new ArrayList<Notification>();
	}

	@Override
	public void clear() {
		this.notifications.clear();
	}

	@Override
	public Iterator<Notification> iterator() {
		return this.notifications.iterator();
	}

	@Override
	public int size() {
		return this.notifications.size();
	}

	@Override
	public boolean add(Notification notification) {
		if (notification == null) {
			throw new IllegalArgumentException("notification should not be null.");
		}

		if (!this.contains(notification)) {
			return this.notifications.add(notification);
		}

		return false;
	}

	@Override
	public boolean addAll(Collection<? extends Notification> notifications) {
		if (notifications == null) {
			throw new IllegalArgumentException("notifications should not be null.");
		}
		for (Notification currentNotification : notifications) {
			this.add(currentNotification);
		}

		return true;
	}

	@Override
	public boolean contains(Object notification) {
		return this.notifications.contains(notification);
	}

	@Override
	public boolean containsAll(Collection<?> notifications) {
		return this.notifications.containsAll(notifications);
	}

	@Override
	public boolean isEmpty() {
		return this.notifications.isEmpty();
	}

	@Override
	public boolean remove(Object notification) {
		return this.notifications.remove(notification);
	}

	@Override
	public boolean removeAll(Collection<?> notifications) {
		return this.notifications.removeAll(notifications);
	}

	@Override
	public boolean retainAll(Collection<?> notifications) {
		return this.notifications.retainAll(notifications);
	}

	@Override
	public Object[] toArray() {
		return this.notifications.toArray();
	}

	@Override
	public <T> T[] toArray(T[] arg0) {
		return this.notifications.toArray(arg0);
	}
}
