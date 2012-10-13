package org.apollo.api.method.impl;

/**
 * An {@link Method} which sends the friends event.
 * @author Steve
 */
public final class SendFriendMethod extends ReceiveFriendMethod {

	/**
	 * The status.
	 */
	private int status;

	/**
	 * Creates the send friend method.
	 * @param sender The sender.
	 * @param friend The friend.
	 * @param status The status.
	 */
	public SendFriendMethod(long sender, long friend, int status) {
		super(sender, friend);
		this.status = status;
	}
	
	/**
	 * Creates the send friend method.
	 * @param sender The sender.
	 * @param friend The friend.
	 * @param status The status.
	 */
	public SendFriendMethod(String sender, String friend, int status) {
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
