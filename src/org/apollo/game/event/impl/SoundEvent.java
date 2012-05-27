package org.apollo.game.event.impl;

import org.apollo.game.event.Event;
import org.apollo.net.release.r317.SoundEventEncoder;

/**
 * An {@link Event} for the {@link SoundEventEncoder}.
 * @author Steve
 */
public class SoundEvent extends Event {

	/**
	 * The current sound.
	 */
	private final int sound;

	/**
	 * Last sound the client made.
	 */
	private int lastsound;

	/**
	 * Hold the temporary set.
	 */
	private final boolean temp;

	/**
	 * Create a temporary sound.
	 * @param sound The sound id.
	 * @param lastsound The last sound id.
	 */
	public SoundEvent(int sound, int lastsound) {
		this.sound = sound;
		this.lastsound = lastsound;
		this.temp = true;
	}

	/**
	 * Create a sound.
	 * @param sound The sound id.
	 */
	public SoundEvent(int sound) {
		this.sound = sound;
		this.temp = false;
	}

	/**
	 * Gets the current sound id.
	 * @return {@link Integer} The sound id.
	 */
	public int getSound() {
		return sound;
	}

	/**
	 * Gets the last sound id.
	 * @return {@link Integer} The sound id.
	 */
	public int getLastSound() {
		return lastsound;
	}

	/**
	 * Gets the temporary boolean.
	 * @return {@link Boolean} The temporary boolean.
	 */
	public boolean isTemp() {
		return temp;
	}
}
