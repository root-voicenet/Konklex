package org.apollo.game.event.impl;

import org.apollo.game.event.Event;
import org.apollo.game.model.Position;

/**
 * An {@link Event} that sends a projectile to the player.
 * @author Steve
 */
public final class ProjectileEvent extends Event {

	/**
	 * The starting height level.
	 */
	private final byte startHeight;

	/**
	 * The ending position.
	 */
	private final Position position;

	/**
	 * The victim.
	 */
	private final byte victim;

	/**
	 * The id of the projectile.
	 */
	private final short id;

	/**
	 * The speed of the projectile.
	 */
	private final short speed;

	/**
	 * The angle of the projectile.
	 */
	private final byte angle;

	/**
	 * Creates a new projectile event.
	 * @param startHeight The starting height level.
	 * @param position The ending position.
	 * @param victim The victim.
	 * @param id The projectile.
	 * @param speed The speed of the projectile.
	 * @param angle The angle of the projectile.
	 */
	public ProjectileEvent(byte startHeight, Position position, byte victim, short id, short speed, byte angle) {
		this.startHeight = startHeight;
		this.position = position;
		this.victim = victim;
		this.id = id;
		this.speed = speed;
		this.angle = angle;
	}

	/**
	 * Gets the angle.
	 * @return The angle.
	 */
	public byte getAngle() {
		return angle;
	}

	/**
	 * Gets the height level.
	 * @return The height level.
	 */
	public byte getHeight() {
		return startHeight;
	}

	/**
	 * Gets the projectile id.
	 * @return The projectile id.
	 */
	public short getId() {
		return id;
	}

	/**
	 * Gets the ending position.
	 * @return The ending position.
	 */
	public Position getPosition() {
		return position;
	}

	/**
	 * Gets the speed of the projectile.
	 * @return The speed of the projectile.
	 */
	public short getSpeed() {
		return speed;
	}

	/**
	 * Gets the victim.
	 * @return The victim.
	 */
	public byte getVictim() {
		return victim;
	}
}