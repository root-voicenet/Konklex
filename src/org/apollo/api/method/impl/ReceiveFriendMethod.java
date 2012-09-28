package org.apollo.api.method.impl;

import org.apollo.api.method.Method;

/**
 * An {@link Method} that gets the status of a remote friend check.
 * @author Steve
 */
public final class ReceiveFriendMethod extends SendFriendMethod {

	/**
	 * The status.
	 */
	private final int status;

	/**
	 * Creates the receive friend method.
	 * @param sender The sender.
	 * @param friend The friend.
	 * @param status The status.
	 */
	public ReceiveFriendMethod(String sender, String friend, int status) {
		super(sender, friend);
		this.status = status;
	}
	
	/**
	 * Creates the receive friend method.
	 * @param sender The sender.
	 * @param friend The friend.
	 * @param status The status.
	 */
	public ReceiveFriendMethod(long sender, long friend, int status) {
		super(sender, friend);
		this.status = status;
	}

	/**
	 * Gets the status.
	 * @return The status.
	 */
	public int getStatus() {
		return status;
	}

}
