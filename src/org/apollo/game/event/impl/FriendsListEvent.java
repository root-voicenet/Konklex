package org.apollo.game.event.impl;

import org.apollo.game.event.Event;

/**
 * An {@link Event} for the {@link SendOnlineStatusEncoder}.
 * @author Steve
 */
public final class FriendsListEvent extends Event {

	/**
	 * The opcode.
	 */
	private final int opcode;

	/**
	 * The friend.
	 */
	private final long friend;

	/**
	 * Create a new private chat event.
	 * @param opcode The opcode.
	 * @param friend The friend.
	 */
	public FriendsListEvent(int opcode, long friend) {
		this.opcode = opcode;
		this.friend = friend;
	}

	/**
	 * Gets the friend.
	 * @return The friend.
	 */
	public long getFriend() {
		return friend;
	}

	/**
	 * Gets the opcode.
	 * @return The opcode.
	 */
	public int getOpcode() {
		return opcode;
	}

}
