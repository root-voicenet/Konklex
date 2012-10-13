package org.apollo.api.method.impl;

import org.apollo.api.method.Method;
import org.apollo.util.NameUtil;

/**
 * An {@link Method} that checks if a remote-user is online.
 * @author Steve
 */
public class SendFriendMethod extends Method {
	
	/**
	 * The sender.
	 */
	private final long sender;
	
	/**
	 * The friend.
	 */
	private final long friend;

	/**
	 * Creates the send friend method.
	 * @param sender The sender.
	 * @param friend The friend.
	 */
	public SendFriendMethod(String sender, String friend) {
		this(NameUtil.encodeBase37(sender), NameUtil.encodeBase37(friend));
	}
	
	/**
	 * Creates the send friend method.
	 * @param sender The sender.
	 * @param friend The friend.
	 */
	public SendFriendMethod(long sender, long friend) {
		this.sender = sender;
		this.friend = friend;
	}

	/**
	 * Gets the sender.
	 * @return The sender.
	 */
	public long getSender() {
		return sender;
	}

	/**
	 * Gets the friend.
	 * @return The friend.
	 */
	public long getFriend() {
		return friend;
	}

}
