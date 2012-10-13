package org.apollo.api.method.impl;

import org.apollo.api.method.Method;
import org.apollo.util.NameUtil;
import org.apollo.util.TextUtil;

/**
 * An {@link Method} for sending private chat messages across the network.
 * @author Steve
 */
public final class PrivateChatMethod extends Method {

	/**
	 * The friend.
	 */
	private final long friend;
	
	/**
	 * The sender.
	 */
	private final long sender;

	/**
	 * The compressed message.
	 */
	private final byte[] message;

	/**
	 * The sender rights.
	 */
	private final int rights;

	/**
	 * Create a new private chat event.
	 * 
	 * @param friend
	 *            The friend to send this message too.
	 * @param sender
	 *            The sender.
	 * @param rights
	 *            The sender rights.
	 * @param message
	 *            The decoded message.
	 */
	public PrivateChatMethod(long friend, long sender, int rights, byte[] message) {
		this.friend = friend;
		this.sender = sender;
		this.rights = rights;
		this.message = message;
	}
	
	/**
	 * Create a new private chat event.
	 * 
	 * @param friend
	 *            The friend to send this message too.
	 * @param sender
	 *            The sender.
	 * @param rights
	 *            The sender rights.
	 * @param message
	 *            The decoded message.
	 */
	public PrivateChatMethod(String friend, String sender, int rights, byte[] message) {
		this(NameUtil.encodeBase37(friend), NameUtil.encodeBase37(sender), rights, message);
	}
	
	/**
	 * Create a new private chat event.
	 * 
	 * @param friend
	 *            The friend to send this message too.
	 * @param sender
	 *            The sender.
	 * @param rights
	 *            The sender rights.
	 * @param message
	 *            The decoded message.
	 */
	public PrivateChatMethod(long friend, long sender, int rights, String message) {
		message = TextUtil.filterInvalidCharacters(message);
		message = TextUtil.capitalize(message);
		byte[] temp = new byte[message.length()];
		TextUtil.compress(message, temp);
		this.friend = friend;
		this.sender = sender;
		this.rights = rights;
		this.message = temp;
	}
	
	/**
	 * Create a new private chat event.
	 * 
	 * @param friend
	 *            The friend to send this message too.
	 * @param sender
	 *            The sender.
	 * @param rights
	 *            The sender rights.
	 * @param message
	 *            The decoded message.
	 */
	public PrivateChatMethod(String friend, String sender, int rights, String message) {
		message = TextUtil.filterInvalidCharacters(message);
		message = TextUtil.capitalize(message);
		byte[] temp = new byte[message.length()];
		TextUtil.compress(message, temp);
		this.friend = NameUtil.encodeBase37(friend);
		this.sender = NameUtil.encodeBase37(sender);
		this.rights = rights;
		this.message = temp;
	}

	/**
	 * Gets the friend.
	 * 
	 * @return The friend.
	 */
	public long getFriend() {
		return friend;
	}

	/**
	 * Gets the message.
	 * 
	 * @return The message.
	 */
	public byte[] getMessage() {
		return message;
	}

	/**
	 * Gets the sender.
	 * @return The sender.
	 */
	public long getSender() {
		return sender;
	}

	/**
	 * Gets the sender rights.
	 * @return The sender rights.
	 */
	public int getLevel() {
		return rights;
	}
}
