package org.apollo.game.model.def;

import org.apollo.game.model.Position;
import org.apollo.game.model.obj.ObjectEnum;
import org.apollo.game.model.obj.PlayerObject;

/**
 * Represents a type of {@link PlayerObject}.
 * @author Steve
 */
public final class StaticObjectDefinition {

    /**
     * The object position.
     */
    public final Position position;

    /**
     * The object id.
     */
    private final int objectId;

    /**
     * The orientation.
     */
    private final int orient;

    /**
     * The tile id.
     */
    private final int tileId;

    /**
     * The type
     */
    private ObjectEnum type;

    /**
     * Defines a new object static object.
     * @param position The position of the object.
     * @param objectId The object id.
     * @param orient The orientation.
     * @param tileId The tile id.
     */
    public StaticObjectDefinition(Position position, int objectId, int orient, int tileId) {
	this.position = position;
	this.objectId = objectId;
	this.orient = orient;
	this.tileId = tileId;
    }

    /**
     * Gets the object id.
     * @return The object id.
     */
    public int getObject() {
	return objectId;
    }

    /**
     * Gets the orientation.
     * @return The orientation.
     */
    public int getOrient() {
	return orient;
    }

    /**
     * Gets the object position.
     * @return The object position.
     */
    public Position getPosition() {
	return position;
    }

    /**
     * Gets the tile id.
     * @return The tile id.
     */
    public int getTile() {
	return tileId;
    }

    /**
     * Gets the object type.
     * @return The object type.
     */
    public ObjectEnum getType() {
	return type;
    }

    /**
     * Sets the object type.
     * @param type The object type.
     */
    public void setType(ObjectEnum type) {
	this.type = type;
    }
}
