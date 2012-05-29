package org.apollo.game.model;

/**
 * A class for creating or destroying a ground item.
 * @author Steve
 */
public final class GroundItem {

    /**
     * The name of the player who controls this item.
     */
    private final String controllerName;

    /**
     * The item that is on the floor.
     */
    private final Item item;

    /**
     * The position of the item.
     */
    private final Position position;

    /**
     * The amount of remaining pulses until this item disappears.
     */
    private int pulses;

    /**
     * Flag for removing the item.
     */
    private boolean remove = false;

    /**
     * Creates a private ground item.
     * @param controllerName The controller of this item.
     * @param item The item.
     * @param position The position.
     */
    public GroundItem(String controllerName, Item item, Position position) {
	pulses = 20; // 350
	this.controllerName = controllerName;
	this.item = item;
	this.position = position;
    }

    /**
     * Creates a private ground item.
     * @param controllerName The controller of this item.
     * @param item The item.
     * @param position The position.
     * @param remove True if removing, false if otherwise.
     */
    public GroundItem(String controllerName, Item item, Position position, boolean remove) {
	this(controllerName, item, position);
	this.remove = remove;
    }

    /**
     * Decreases the pulses.
     */
    public void decreasePulses() {
	pulses--;
    }

    /**
     * Gets the controller's name.
     * @return The controller's name.
     */
    public String getControllerName() {
	return controllerName;
    }

    /**
     * Gets the item.
     * @return The item.
     */
    public Item getItem() {
	return item;
    }

    /**
     * Gets the position.
     * @return The position.
     */
    public Position getPosition() {
	return position;
    }

    /**
     * Gets the pulses.
     * @return The pulses.
     */
    public int getPulses() {
	return pulses;
    }

    /**
     * Returns the remove flag.
     * @return True if removing this ground item, false if otherwise.
     */
    public boolean isRemoving() {
	return remove;
    }
}