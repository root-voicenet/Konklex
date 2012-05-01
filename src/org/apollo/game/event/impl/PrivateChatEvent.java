package org.apollo.game.event.impl;

import org.apollo.game.event.Event;
import org.apollo.game.model.Player;
import org.apollo.net.release.r317.PrivateChatEventEncoder;

/**
 * An {@link Event} for the {@link PrivateChatEventEncoder}.
 * @author Steve
 */
public class PrivateChatEvent extends Event {

	/**
	 * The friend.
	 */
	private final long friendlong;

	/**
	 * The friend's rights.
	 */
	private int friendrights;

	/**
	 * The message.
	 */
	private final String message;

	/**
	 * The compressed message.
	 */
	private final byte[] emessage;

	/**
	 * The message id.
	 */
	private int lastid;

	/**
	 * The player that send the message.
	 */
	private long from;

	/**
	 * Create a new private chat event.
	 * @param uncompressed the uncompressed
	 * @param length the length
	 * @param friend the friend
	 */
	public PrivateChatEvent(String uncompressed, byte[] length, long friend) {
		this.message = uncompressed;
		this.emessage = length;
		this.friendlong = friend;
	}

	/**
	 * The friend.
	 * @return {@link long}
	 */
	public long getFriendLong() {
		return friendlong;
	}

	/**
	 * The friend's rights.
	 * @return {@link Integer}
	 */
	public int getFriendRights() {
		return friendrights;
	}

	/**
	 * Gets the friend who sent the message.
	 * @return The friend who sent the message.
	 */
	public long getFrom() {
		return from;
	}

	/**
	 * Gets the last chat id.
	 * @return The last chat id.
	 */
	public int getLastId() {
		return lastid;
	}

	/**
	 * The message.
	 * @return {@link String}
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * The message compressed.
	 * @return {@link byte}
	 */
	public byte[] getMessageCompressed() {
		return emessage;
	}

	/**
	 * Set the friends rights.
	 * @param rights the new friend rights
	 */
	public void setFriendRights(int rights) {
		this.friendrights = rights;
	}

	/**
	 * Set the friend who sent the message.
	 * @param from The player who sent the message.
	 */
	public void setFrom(long from) {
		this.from = from;
	}

	/**
	 * Set the friend who sent the message.
	 * @param from The player who sent the message.
	 */
	public void setFrom(Player from) {
		this.from = from.getEncodedName();
	}

	/**
	 * Private chat id.
	 * @param id the new last id
	 */
	public void setLastId(int id) {
		this.lastid = id;
	}
}
