package org.apollo.game.model.def;

import org.apollo.game.model.Npc;

/**
 * Represents a type of {@link Npc}.
 * @author Chris Fletcher
 */
public final class NpcDefinition {

    /**
     * The NPC definitions.
     */
    private static NpcDefinition[] definitions;

    /**
     * Gets the total number of NPCs.
     * @return The total number of NPCs.
     */
    public static int count() {
	return definitions.length;
    }

    /**
     * Gets the NPC definition for the specified id.
     * @param id The id.
     * @return The definition.
     * @throws IndexOutOfBoundsException if the id is out of bounds.
     */
    public static NpcDefinition forId(int id) {
	if (id < 0 || id >= definitions.length)
	    throw new IndexOutOfBoundsException();
	return definitions[id];
    }

    /**
     * Initialises the class with the specified set of definitions.
     * @param definitions The definitions.
     * @throws RuntimeException if there is an id mismatch.
     */
    public static void init(NpcDefinition[] definitions) {
	NpcDefinition.definitions = definitions;
	for (int id = 0; id < definitions.length; id++) {
	    final NpcDefinition def = definitions[id];
	    if (def.getId() != id)
		throw new RuntimeException("NPC definition id mismatch");
	}
    }

    /**
     * The NPC id.
     */
    private final int id;

    /**
     * The name of the NPC.
     */
    private String name;

    /**
     * The description of the NPC.
     */
    private String description;

    /**
     * The NPC's size, in tiles.
     */
    private int size = 1;

    /**
     * The various animation ids.
     */
    private int standAnim = -1, walkAnim = -1, walkBackAnim = -1, walkLeftAnim = -1, walkRightAnim = -1;

    /**
     * An array of interaction options.
     */
    private final String[] interactions = new String[5];

    /**
     * The combat level of the NPC.
     */
    private int combatLevel = -1;

    /**
     * Creates a new NPC definition.
     * @param id The NPC id.
     */
    public NpcDefinition(int id) {
	this.id = id;
    }

    /**
     * Gets the NPC's combat level.
     * @return The combat level, or -1 if it doesn't have one.
     */
    public int getCombatLevel() {
	return combatLevel;
    }

    /**
     * Gets the description of the NPC.
     * @return The description.
     */
    public String getDescription() {
	return description;
    }

    /**
     * Gets the NPC id.
     * @return The NPC id.
     */
    public int getId() {
	return id;
    }

    /**
     * Gets an interaction option.
     * @param slot The slot of the option.
     * @return The option, or {@code null} if there isn't any at the specified
     * slot.
     * @throws IndexOutOfBoundsException if the slot is out of bounds.
     */
    public String getInteraction(int slot) {
	if (slot < 0 || slot >= interactions.length)
	    throw new IndexOutOfBoundsException();
	return interactions[slot];
    }

    /**
     * Gets the array of interaction options.
     * @return The interaction options.
     */
    public String[] getInteractions() {
	return interactions;
    }

    /**
     * Gets the name of the NPC.
     * @return The name of the NPC.
     */
    public String getName() {
	return name;
    }

    /**
     * Gets the NPC's size, in tiles.
     * @return The size.
     */
    public int getSize() {
	return size;
    }

    /**
     * Gets the id of the NPC's standing animation.
     * @return The stand animation id, or -1 if it doesn't have one.
     */
    public int getStandAnimation() {
	return standAnim;
    }

    /**
     * Gets the walking animation of the NPC.
     * @return The walking animation.
     */
    public int getWalkAnimation() {
	return walkAnim;
    }

    /**
     * Gets the walk-back animation of the NPC.
     * @return The walk-back animation.
     */
    public int getWalkBackAnimation() {
	return walkBackAnim;
    }

    /**
     * Gets the walk-left animation of the NPC.
     * @return The walk-left animation.
     */
    public int getWalkLeftAnimation() {
	return walkLeftAnim;
    }

    /**
     * Gets the walk-right animation of the NPC.
     * @return The walk-right animation.
     */
    public int getWalkRightAnimation() {
	return walkRightAnim;
    }

    /**
     * Checks if the NPC has a combat level.
     * @return {@code true} if so, {@code false} if not.
     */
    public boolean hasCombatLevel() {
	return combatLevel != -1;
    }

    /**
     * Checks if there is an interaction option present.
     * @param slot The slot to check.
     * @return {@code true} if so, {@code false} if not.
     * @throws IndexOutOfBoundsException if the slot is out of bounds.
     */
    public boolean hasInteraction(int slot) {
	if (slot < 0 || slot >= interactions.length)
	    throw new IndexOutOfBoundsException();
	return interactions[slot] != null;
    }

    /**
     * Checks if the NPC has a standing animation id.
     * @return {@code true} if so, {@code false} if not.
     */
    public boolean hasStandAnimation() {
	return standAnim != -1;
    }

    /**
     * Checks if the NPC has a walking animation.
     * @return {@code true} if so, {@code false} if not.
     */
    public boolean hasWalkAnimation() {
	return walkAnim != -1;
    }

    /**
     * Checks if the NPC has a walk-back animation.
     * @return {@code true} if so, {@code false} if not.
     */
    public boolean hasWalkBackAnimation() {
	return walkBackAnim != -1;
    }

    /**
     * Checks if the NPC has a walk-left animation.
     * @return {@code true} if so, {@code false} if not.
     */
    public boolean hasWalkLeftAnimation() {
	return walkLeftAnim != -1;
    }

    /**
     * Checks if the NPC has a walk-right animation.
     * @return {@code true} if so, {@code false} if not.
     */
    public boolean hasWalkRightAnimation() {
	return walkRightAnim != -1;
    }

    /**
     * Sets the NPC's combat level.
     * @param combatLevel The combat level.
     */
    public void setCombatLevel(int combatLevel) {
	this.combatLevel = combatLevel;
    }

    /**
     * Sets the description of the NPC.
     * @param description The description.
     */
    public void setDescription(String description) {
	this.description = description;
    }

    /**
     * Sets an interaction option.
     * @param slot The slot of the option.
     * @param interaction The interaction options.
     * @throws IndexOutOfBoundsException if the slot is out of bounds.
     */
    public void setInteraction(int slot, String interaction) {
	if (slot < 0 || slot >= interactions.length)
	    throw new IndexOutOfBoundsException();
	interactions[slot] = interaction;
    }

    /**
     * Sets the name of the NPC.
     * @param name The name.
     */
    public void setName(String name) {
	this.name = name;
    }

    /**
     * Sets the size of the NPC, in tiles.
     * @param size The size.
     */
    public void setSize(int size) {
	this.size = size;
    }

    /**
     * Sets the id of the NPC's standing animation.
     * @param standAnim The stand animation id.
     */
    public void setStandAnimation(int standAnim) {
	this.standAnim = standAnim;
    }

    /**
     * Sets the walking animation of the NPC.
     * @param walkAnim The walking animation.
     */
    public void setWalkAnimation(int walkAnim) {
	this.walkAnim = walkAnim;
    }

    /**
     * Sets the various walking animations of the NPC.
     * @param walkAnim The walking animation.
     * @param walkBackAnim The walk-back animation.
     * @param walkLeftAnim The walk-left animation.
     * @param walkRightAnim The walk-right animation.
     */
    public void setWalkAnimations(int walkAnim, int walkBackAnim, int walkLeftAnim, int walkRightAnim) {
	this.walkAnim = walkAnim;
	this.walkBackAnim = walkBackAnim;
	this.walkLeftAnim = walkLeftAnim;
	this.walkRightAnim = walkRightAnim;
    }
}