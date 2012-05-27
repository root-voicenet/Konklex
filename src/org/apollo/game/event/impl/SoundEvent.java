package org.apollo.game.event.impl;

import org.apollo.game.event.Event;

/**
 * An {@link Event} that sends a sound id to the client.
 * @author Steve
 */
public final class SoundEvent extends Event {

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
     * Create a sound.
     * @param sound The sound id.
     */
    public SoundEvent(int sound) {
	this.sound = sound;
	this.temp = false;
    }

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
     * Gets the last sound id.
     * @return The sound id.
     */
    public int getLastSound() {
	return lastsound;
    }

    /**
     * Gets the current sound id.
     * @return The sound id.
     */
    public int getSound() {
	return sound;
    }

    /**
     * Gets the temporary boolean.
     * @return The temporary boolean.
     */
    public boolean isTemp() {
	return temp;
    }
}
