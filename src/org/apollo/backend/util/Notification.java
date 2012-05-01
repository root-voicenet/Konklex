package org.apollo.backend.util;

import java.util.ArrayDeque;
import java.util.BitSet;
import java.util.Queue;

/**
 * Sends a noticiation to the frontend.
 * @author Steve
 */
public class Notification {

	/**
	 * Create a new yell class.
	 * @author Steve
	 */
	public class Notify {

		private final BitSet attributes = new BitSet();

		private final String key;

		private final String value;

		/**
		 * Create a new notification.
		 * @param key The key.
		 * @param value The value.
		 */
		public Notify(String key, String value) {
			this.key = key;
			this.value = value;
		}

		/**
		 * Gets the attribute
		 * @param key The key.
		 * @return The key's attribute.
		 */
		public boolean getAttribute(int key) {
			return attributes.get(key);
		}

		/**
		 * Gets the key.
		 * @return The key.
		 */
		public String getKey() {
			return key;
		}

		/**
		 * Gets the value.
		 * @return The value.
		 */
		public String getValue() {
			return value;
		}

		/**
		 * Sets the key's attribute.
		 * @param key The key.
		 * @param value The value.
		 */
		public void setAttribute(int key, boolean value) {
			attributes.set(key, value);
		}
	}

	/**
	 * Types of notications.
	 * @author Steve
	 */
	public enum Type {
		/**
		 * A notification.
		 */
		NOTIFICATION,
		/**
		 * A yell message.
		 */
		MESSAGE
	}

	/**
	 * The instance.
	 */
	private static Notification instance;

	/**
	 * Gets the instance.
	 * @return The instance.
	 */
	public static Notification getInstance() {
		if (instance == null) {
			instance = new Notification();
		}
		return instance;
	}

	/**
	 * The notifications.
	 */
	private final Queue<Notify> notifications = new ArrayDeque<Notify>(2);

	/**
	 * The yells.
	 */
	private final Queue<Notify> yells = new ArrayDeque<Notify>(20);

	/**
	 * Adds a new notification to the queue.
	 * @param key The key.
	 * @param value The value.
	 * @param type The notification type.
	 */
	public void add(String key, String value, Type type) {
		if (type.equals(Type.MESSAGE)) {
			if (yells.size() == 20) {
				yells.poll();
			}
			yells.add(new Notify(key, value));
		} else
			if (type.equals(Type.NOTIFICATION)) {
				if (notifications.size() == 2) {
					notifications.poll();
				}
				notifications.add(new Notify(key, value));
			}
	}

	/**
	 * Gets the array list of the last 20 notifications.
	 * @return The last 20 notifications.
	 */
	public Queue<Notify> getNotifications() {
		return notifications;
	}

	/**
	 * Gets the array list of the last 20 yells.
	 * @return The last 20 yells.
	 */
	public Queue<Notify> getYells() {
		return yells;
	}
}
