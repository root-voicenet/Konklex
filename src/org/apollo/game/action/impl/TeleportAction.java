package org.apollo.game.action.impl;

import java.util.BitSet;

import org.apollo.game.action.Action;
import org.apollo.game.model.Animation;
import org.apollo.game.model.Character;
import org.apollo.game.model.Graphic;
import org.apollo.game.model.Player;
import org.apollo.game.model.Position;

/**
 * An {@link Action} that teleports the player.
 * @author Steve
 */
public final class TeleportAction extends Action<Character> {

	private final BitSet attributes = new BitSet(3);

	private final Position position;

	/**
	 * Create the teleport action on the specified player.
	 * @param player The player that will execute this action.
	 * @param position The new position the player will be teleported too.
	 */
	public TeleportAction(Character player, Position position) {
		super(2, true, player);
		this.position = position;
	}

	@Override
	public boolean equals(Object other) {
		return getClass() == other.getClass();
	}

	@Override
	public void execute() {
		if (!attributes.get(0)) {
			if (getCharacter().isControlling()) {
				((Player) getCharacter()).getInterfaceSet().close(false);
			}
			getCharacter().playAnimation(new Animation(714));
			attributes.set(0, true);
		}
		else if (!attributes.get(1)) {
			getCharacter().playGraphic(new Graphic(308, 15, 100));
			attributes.set(1, true);
			setDelay(1);
		}
		else if (!attributes.get(2)) {
			getCharacter().stopGraphic();
			getCharacter().playAnimation(new Animation(1979));
			teleport();
			stop();
		}
	}

	/**
	 * Teleports the player.
	 */
	public void teleport() {
		getCharacter().setPosition(position);
		getCharacter().setTeleporting(true);
	}
}
