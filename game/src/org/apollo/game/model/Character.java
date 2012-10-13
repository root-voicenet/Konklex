package org.apollo.game.model;

import java.util.ArrayList;
import java.util.List;

import org.apollo.game.action.Action;
import org.apollo.game.event.Event;
import org.apollo.game.event.impl.DamageEvent;
import org.apollo.game.event.impl.ServerMessageEvent;
import org.apollo.game.model.Inventory.StackMode;
import org.apollo.game.model.inter.melee.Combat;
import org.apollo.game.model.region.Region;
import org.apollo.game.model.skill.HitpointSkillListener;
import org.apollo.game.scheduling.ScheduledTask;
import org.apollo.game.scheduling.impl.SkillNormalizationTask;
import org.apollo.game.sync.block.SynchronizationBlock;
import org.apollo.game.sync.block.SynchronizationBlockSet;
import org.apollo.util.CharacterRepository;

/**
 * A {@link Character} is a living creature in the world, such as a player or NPC.
 * @author Graham
 */
public abstract class Character {

	/**
	 * The index of this character in the {@link CharacterRepository} it belongs to.
	 */
	private int index = -1;

	/**
	 * Teleportation flag.
	 */
	private boolean teleporting = false;

	/**
	 * The walking queue.
	 */
	private final WalkingQueue walkingQueue = new WalkingQueue(this);

	/**
	 * The first direction.
	 */
	private Direction firstDirection = Direction.NONE;

	/**
	 * The second direction.
	 */
	private Direction secondDirection = Direction.NONE;

	/**
	 * The current position of this character.
	 */
	private Position position;

	/**
	 * A list of local players.
	 */
	private final List<Player> localPlayers = new ArrayList<Player>();

	/**
	 * A list of local NPCs.
	 */
	private final List<Npc> localNPCs = new ArrayList<Npc>();

	/**
	 * A list of local events.
	 */
	private final List<Event> localEvents = new ArrayList<Event>();

	/**
	 * A set of {@link SynchronizationBlock}s.
	 */
	private SynchronizationBlockSet blockSet = new SynchronizationBlockSet();

	/**
	 * The character's current action.
	 */
	private Action<?> action; // TODO

	/**
	 * The character's inventory.
	 */
	private final Inventory inventory = new Inventory(InventoryConstants.INVENTORY_CAPACITY);

	/**
	 * The character's equipment.
	 */
	private final Inventory equipment = new Inventory(InventoryConstants.EQUIPMENT_CAPACITY, StackMode.STACK_ALWAYS);

	/**
	 * The character's bank.
	 */
	private final Inventory bank = new Inventory(InventoryConstants.BANK_CAPACITY, StackMode.STACK_ALWAYS);

	/**
	 * The character's skill set.
	 */
	private final SkillSet skillSet = new SkillSet();

	/**
	 * The character's melee set.
	 */
	private MeleeSet meleeSet = new MeleeSet(this);

	/**
	 * The character's current region.
	 */
	private Region region = null;

	/**
	 * The default energy level.
	 */
	private int runEnergy = 100;

	/**
	 * True if facing, false if not.
	 */
	private boolean facing = false;

	/**
	 * Creates a new character with the specified initial position.
	 * @param position The initial position of this character.
	 */
	public Character(Position position) {
		this.position = position;
		init();
	}

	/**
	 * Add health (eating).
	 * @param health the health
	 */
	public void addHealth(int health) {
		final int temp = health + getHealth() > getHealthMax() ? getHealthMax() : health + getHealth();
		setHealth(temp);
	}

	/**
	 * Damages the character.
	 * @param hit The damage(s) to deal.
	 */
	public void damage(int... hit) {
		DamageEvent damage = new DamageEvent(hit[0] > getHealth() ? getHealth() : hit[0], getHealth(), getHealthMax());
		int health = getHealth() - damage.getDamageDone();
		setHealth(health);
		blockSet.add(SynchronizationBlock.createHitUpdateBlock(damage));
		if (hit.length == 2) {
			damage = new DamageEvent(hit[1] > getHealth() ? getHealth() : hit[1], getHealth(), getHealthMax());
			health = getHealth() - damage.getDamageDone();
			setHealth(health);
			blockSet.add(SynchronizationBlock.createSecondHitUpdateBlock(damage));
		}
	}
	
	/**
	 * Damages the character.
	 * @param hit The damage to deal.
	 */
	public void damage2(int hit) {
		DamageEvent damage = new DamageEvent(hit > getHealth() ? getHealth() : hit, getHealth(), getHealthMax());
		int health = getHealth() - damage.getDamageDone();
		setHealth(health);
		blockSet.add(SynchronizationBlock.createSecondHitUpdateBlock(damage));
	}

	/**
	 * Gets the character's bank.
	 * @return The character's bank.
	 */
	public Inventory getBank() {
		return bank;
	}

	/**
	 * Gets the {@link SynchronizationBlockSet}.
	 * @return The block set.
	 */
	public SynchronizationBlockSet getBlockSet() {
		return blockSet;
	}

	/**
	 * Gets the directions as an array.
	 * @return A zero, one or two element array containing the directions (in order).
	 */
	public Direction[] getDirections() {
		if (firstDirection != Direction.NONE) {
			if (secondDirection != Direction.NONE)
				return new Direction[] { firstDirection, secondDirection };
			else
				return new Direction[] { firstDirection };
		}
		else
			return Direction.EMPTY_DIRECTION_ARRAY;
	}

	/**
	 * Gets the character's equipment.
	 * @return The character's equipment.
	 */
	public Inventory getEquipment() {
		return equipment;
	}

	/**
	 * Gets the first direction.
	 * @return The first direction.
	 */
	public Direction getFirstDirection() {
		return firstDirection;
	}

	/**
	 * Get the players health.
	 * @return {@link Integer}
	 */
	public int getHealth() {
		return getSkillSet().getSkill(Skill.HITPOINTS).getCurrentLevel();
	}

	/**
	 * The max health.
	 * @return {@link Integer}
	 */
	public int getHealthMax() {
		return getSkillSet().getSkill(Skill.HITPOINTS).getMaximumLevel();
	}

	/**
	 * Gets the index of this character.
	 * @return The index of this character.
	 */
	public int getIndex() {
		synchronized (this) {
			return index;
		}
	}

	/**
	 * Gets the character's inventory.
	 * @return The character's inventory.
	 */
	public Inventory getInventory() {
		return inventory;
	}

	/**
	 * Gets the local event list.
	 * @return The local event list.
	 */
	public List<Event> getLocalEventList() {
		return localEvents;
	}

	/**
	 * Gets the local NPC list.
	 * @return The local NPC list.
	 */
	public List<Npc> getLocalNpcList() {
		return localNPCs;
	}

	/**
	 * Gets the local player list.
	 * @return The local player list.
	 */
	public List<Player> getLocalPlayerList() {
		return localPlayers;
	}

	/**
	 * Gets the {@link MeleeSet}.
	 * @return The melee set.
	 */
	public MeleeSet getMeleeSet() {
		return meleeSet;
	}

	/**
	 * Resets the melee set.
	 */
	public void resetMeleeSet() {
		meleeSet = new MeleeSet(this);
	}

	/**
	 * Gets the position of this character.
	 * @return The position of this character.
	 */
	public Position getPosition() {
		return position;
	}

	/**
	 * Gets the region of this character.
	 * @return The region of this character.
	 */
	public Region getRegion() {
		return region;
	}

	/**
	 * Return the energy level.
	 * @return {@link Integer}
	 */
	public int getRunEnergy() {
		return runEnergy;
	}

	/**
	 * Gets the second direction.
	 * @return The second direction.
	 */
	public Direction getSecondDirection() {
		return secondDirection;
	}

	/**
	 * Gets the character's skill set.
	 * @return The character's skill set.
	 */
	public SkillSet getSkillSet() {
		return skillSet;
	}

	/**
	 * Gets the walking queue.
	 * @return The walking queue.
	 */
	public WalkingQueue getWalkingQueue() {
		return walkingQueue;
	}

	/**
	 * Initialises this character.
	 */
	private void init() {
		skillSet.addListener(new HitpointSkillListener(this));
		World.getWorld().schedule(new SkillNormalizationTask(this));

		final Character me = this;
		World.getWorld().schedule(new ScheduledTask(1, false) {
			@Override
			public void execute() {
				if (isActive())
					Combat.process(me);
				else
					stop();
			}
		});
	}

	/**
	 * Checks if this character is active.
	 * @return {@code true} if so, {@code false} if not.
	 */
	public boolean isActive() {
		return index != -1;
	}

	/**
	 * Is the character dead.
	 * @return {@link Boolean}
	 */
	public boolean isDead() {
		return getHealth() <= 0;
	}

	/**
	 * Checks if this player is currently teleporting.
	 * @return {@code true} if so, {@code false} if not.
	 */
	public boolean isTeleporting() {
		return teleporting;
	}

	/**
	 * Plays the specified animation.
	 * @param animation The animation.
	 */
	public void playAnimation(Animation animation) {
		if (animation != null)
			blockSet.add(SynchronizationBlock.createAnimationBlock(animation));
	}

	/**
	 * Plays the specified graphic.
	 * @param graphic The graphic.
	 */
	public void playGraphic(Graphic graphic) {
		if (graphic != null)
			blockSet.add(SynchronizationBlock.createGraphicBlock(graphic));
	}

	/**
	 * Resets the block set.
	 */
	public void resetBlockSet() {
		blockSet = new SynchronizationBlockSet();
	}

	/**
	 * Checks if this user is a controllable player.
	 * @return True if this character is being controlled, false if otherwise.
	 */
	public abstract boolean isControlling();

	/**
	 * Sends an {@link Event} to either:
	 * <ul>
	 * <li>The client if this {@link Character} is a {@link Player}.</li>
	 * <li>The AI routines if this {@link Character} is an NPC</li>
	 * </ul>
	 * @param event The event.
	 */
	public abstract void send(Event event);

	/**
	 * Sends a message to the character.
	 * @param message The message.
	 */
	public void sendMessage(String message) {
		send(new ServerMessageEvent(message));
	}

	/**
	 * Sets the next directions for this character.
	 * @param first The first direction.
	 * @param second The second direction.
	 */
	public void setDirections(Direction first, Direction second) {
		this.firstDirection = first;
		this.secondDirection = second;
	}

	/**
	 * Set the characters health.
	 * @param health the new health
	 */
	public void setHealth(int health) {
		getSkillSet().setSkill(Skill.HITPOINTS,
				new Skill(getSkillSet().getSkill(Skill.HITPOINTS).getExperience(), health, getHealthMax()));
	}

	/**
	 * Sets the index of this character.
	 * @param index The index of this character.
	 */
	public void setIndex(int index) {
		synchronized (this) {
			this.index = index;
		}
	}

	/**
	 * Sets the position of this character.
	 * @param position The position of this character.
	 */
	public void setPosition(Position position) {
		final Region region = World.getWorld().getRegionManager().getRegionByLocation(position);
		if (getRegion() != null) {
			if (getRegion() != region) {
				getRegion().removeCharacter(this);
				setRegion(region);
				region.addCharacter(this);
			}
		}
		else {
			setRegion(region);
			region.addCharacter(this);
		}
		this.position = position;
	}

	/**
	 * Sets the region of this character.
	 * @param region The region of this character.
	 */
	public void setRegion(Region region) {
		this.region = region;
	}

	/**
	 * Set the energy level.
	 * @param runEnergy the new run energy
	 */
	public void setRunEnergy(int runEnergy) {
		this.runEnergy = runEnergy;
	}

	/**
	 * Sets the teleporting flag.
	 * @param teleporting the new teleporting {@code true} if the player is teleporting, {@code false} if not.
	 */
	public void setTeleporting(boolean teleporting) {
		this.teleporting = teleporting;
	}

	/**
	 * Starts a new action, stopping the current one if it exists.
	 * @param action The new action.
	 * @return A flag indicating if the action was started.
	 */
	public boolean startAction(Action<?> action) {
		if (this.action != null) {
			if (this.action.equals(action))
				return false;
			stopAction();
		}
		this.action = action;
		World.getWorld().schedule(action);
		return true; // TODO maybe this should be incorporated into the action
		// class itself?
	}

	/**
	 * Turns the character to face the specified position.
	 * @param id The entity id.
	 */
	public void startFacing(int id) {
		blockSet.add(SynchronizationBlock.createTurnToEntityBlock(id));
		facing = true;
	}

	/**
	 * Stops the current action.
	 */
	public void stopAction() {
		if (action != null) {
			action.stop();
			action = null;
		}
	}

	/**
	 * Stops the current animation.
	 */
	public void stopAnimation() {
		playAnimation(Animation.STOP_ANIMATION);
	}

	/**
	 * Stops facing a entity.
	 */
	public void stopFacing() {
		if (facing)
			startFacing(65535);
	}

	/**
	 * Stops the current graphic.
	 */
	public void stopGraphic() {
		playGraphic(Graphic.STOP_GRAPHIC);
	}

	/**
	 * Teleports this character to the specified position, setting the appropriate flags and clearing the walking queue.
	 * @param position The position.
	 * @param action True if starting the teleport action, false if not.
	 */
	public void teleport(Position position, boolean action) {
		this.teleporting = true;
		setPosition(position);
		this.walkingQueue.clear();
		this.stopAction(); // TODO do it on any movement is a must.. walking
		// queue perhaps?
	}

	/**
	 * Teleports this character to the specified position, setting the appropriate flags and clearing the walking queue.
	 * @param position The position.
	 */
	public void teleport(Position position) {
		teleport(position, false);
	}

	/**
	 * Turns the character to face the specified position.
	 * @param position The position to face.
	 */
	public void turnTo(Position position) {
		blockSet.add(SynchronizationBlock.createTurnToPositionBlock(position));
	}
}
