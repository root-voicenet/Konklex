package org.apollo.game.model.obj;

import org.apollo.game.model.Position;

/**
 * Represents a static object in the game world.
 * @author Graham
 * @author Chris Fletcher
 */
public final class StaticObject {

	/**
	 * The object's id.
	 */
	private final short id;

	/**
	 * The object's position.
	 */
	private final Position position;

	/**
	 * The object type.
	 */
	private final byte type;

	/**
	 * The object's rotation.
	 */
	private final byte rotation;

	/**
	 * Creates a new static object.
	 * @param id The object's id.
	 * @param position The position.
	 * @param type The type code of the object.
	 * @param rotation The rotation of the object.
	 */
	public StaticObject(int id, Position position, int type, int rotation) {
		this.id = (short) id;
		this.position = position;
		this.type = (byte) type;
		this.rotation = (byte) rotation;
	}

	/**
	 * Gets the id of the object.
	 * @return The object id.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Gets the position of this object.
	 * @return The object's position.
	 */
	public Position getPosition() {
		return position;
	}

	/**
	 * Gets the object's rotation.
	 * @return The rotation.
	 */
	public int getRotation() {
		return rotation;
	}

	/**
	 * Gets the type code of the object.
	 * @return The type.
	 */
	public int getType() {
		return type;
	}

	@Override
	public String toString() {
		return StaticObject.class.getName() + "[id=" + id + ", type=" + type + ", rotation=" + rotation + "]";
	}

}