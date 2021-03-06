package org.apollo.game.model;

import org.apollo.game.event.Event;
import org.apollo.game.event.impl.ServerMessageEvent;
import org.apollo.game.minigame.MinigameService;
import org.apollo.game.model.def.NpcDefinition;
import org.apollo.game.sync.block.SynchronizationBlock;

/**
 * A {@link Npc} is a {@link Character} which is computer-controlled (Non-Player Character).
 * @author Steve
 */
public final class Npc extends Character {

	/**
	 * The default position of this NPC.
	 */
	private static final Position DEFAULT_POSITION = new Position(0, 0);

	/**
	 * The NPC id.
	 */
	private final int id;

	/**
	 * The random walking flag.
	 */
	private boolean randomWalking;

	/**
	 * The facing id.
	 */
	private int face;

	/**
	 * Creates a new NPC.
	 * @param id The NPC id.
	 */
	public Npc(int id) {
		this(id, DEFAULT_POSITION);
	}

	/**
	 * Creates a new NPC.
	 * @param id The NPC id.
	 * @param position The position to place the NPC at.
	 */
	public Npc(int id, Position position) {
		super(position);
		this.id = id;
		this.init();
	}

	@SuppressWarnings("deprecation")
	@Override
	public void exit() {
		MinigameService service = World.getWorld().getContext().getService(MinigameService.class);
		if (service != null) {
			service.playerDisconnected(this);
		}
	}

	/**
	 * Gets the definition of this NPC.
	 * @return The definition.
	 */
	public NpcDefinition getDefinition() {
		return NpcDefinition.forId(id);
	}

	/**
	 * Gets the face.
	 * @return The face.
	 */
	public int getFace() {
		return face;
	}

	/**
	 * Gets the NPC id.
	 * @return The id.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Initializes this npc.
	 */
	public void init() {
		getMeleeSet().setAutoRetaliating(true); // agressive
		getMeleeSet().setAttackable(true); // can attack
	}

	@Override
	public boolean isControlling() {
		return false;
	}

	/**
	 * Gets the random walking flag.
	 * @return True if random walking, false if otherwise.
	 */
	public boolean isRandomWalking() {
		return randomWalking;
	}

	@Override
	public void send(Event event) {
		if (event.getEventId() == 3) {
			final String message = ((ServerMessageEvent) event).getMessage();
			getBlockSet().add(SynchronizationBlock.createForceChatBlock(message));
		}
	}

	/**
	 * Sets the face.
	 * @param face The face.
	 */
	public void setFace(int face) {
		this.face = face;
	}

	/**
	 * Sets the random walking flag.
	 * @param randomWalking The random walking flag.
	 */
	public void setRandomWalking(boolean randomWalking) {
		this.randomWalking = randomWalking;
	}
	
	@Override
	public int getRunEnergy() {
		return 0;
	}

	@Override
	public String toString() {
		return Npc.class.getName() + " [id=" + id + "]";
	}

}