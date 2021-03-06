package org.apollo.game.action;

import org.apollo.game.model.Character;
import org.apollo.game.scheduling.ScheduledTask;

/**
 * An action is a specialised {@link ScheduledTask} which is specific to a character.
 * <p>
 * <strong>ALL</strong> actions <strong>MUST</strong> implement the {@link #equals(Object)} method. This is to check if
 * two actions are identical: if they are, then the new action does not replace the old one (so spam/accidental clicking
 * won't cancel your action, and start another from scratch).
 * @param <T> The character
 * @author Graham
 */
public abstract class Action<T extends Character> extends ScheduledTask {

	/**
	 * The character performing the action.
	 */
	private final T character;

	/**
	 * A flag indicating if this action is stopping.
	 */
	private boolean stopping = false;

	/**
	 * Creates a new action.
	 * @param delay The delay in pulses.
	 * @param immediate A flag indicating if the action should happen immediately.
	 * @param character The character performing the action.
	 */
	public Action(int delay, boolean immediate, T character) {
		super(delay, immediate);
		this.character = character;
	}

	/**
	 * Gets the character which performed the action.
	 * @return The character.
	 */
	public T getCharacter() {
		return character;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apollo.game.scheduling.ScheduledTask#stop()
	 */
	@Override
	public void stop() {
		super.stop();
		if (!stopping) {
			stopping = true;
			character.stopAction();
		}
	}
}
