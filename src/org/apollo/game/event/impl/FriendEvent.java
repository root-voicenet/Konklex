package org.apollo.game.event.impl;

import org.apollo.game.event.Event;
import org.apollo.net.release.r317.BuildFriendsEventEncoder;
import org.apollo.util.NameUtil;

/**
 * An {@link Event} for the {@link BuildFriendsEventEncoder}.
 * @author Steve
 */
public final class FriendEvent extends Event {

	/** The opcode. */
	private final int opcode;

	/**
	 * The friend.
	 */
	private final String friend;

	/**
	 * The friend long.
	 */
	private final long longfriend;

	/**
	 * The world id.
	 */
	private int world = 0;

	/**
	 * Create a new private chat event.
	 * @param friend the friend
	 * @param opcode the opcode
	 */
	public FriendEvent(String friend, int opcode) {
		this.opcode = opcode;
		this.friend = friend;
		this.longfriend = NameUtil.encodeBase37(friend);
	}

	/**
	 * Gets the friend.
	 * @return The friend.
	 */
	public String getFriend() {
		return friend;
	}

	/**
	 * Encode the name to a long.
	 * @return {@link Long} The friend long.
	 */
	public long getFriendLong() {
		return longfriend;
	}

	/**
	 * Gets the opcode.
	 * @return The opcode.
	 */
	public int getOpcode() {
		return opcode;
	}

	/**
	 * Get the chat id.
	 * @return {@link Integer} The world.
	 */
	public int getWorld() {
		return world;
	}

	/**
	 * Set the chat id
	 * @param world The world id.
	 */
	public void setWorld(int world) {
		this.world = world;
	}
}
