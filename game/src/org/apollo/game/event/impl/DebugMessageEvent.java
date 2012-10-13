package org.apollo.game.event.impl;

/**
 * An event which is sent to the client with a server-side debug message.
 * @author Steve
 */
public final class DebugMessageEvent extends ServerMessageEvent {

	/**
	 * Creates the {@link ServerMessageEvent}.
	 * @param message The message.
	 */
	public DebugMessageEvent(String message) {
		super(message);
	}
}
