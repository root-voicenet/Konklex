package org.apollo.game.scheduling.impl;

import org.apollo.game.event.impl.GroundItemEvent;
import org.apollo.game.event.impl.PositionEvent;
import org.apollo.game.model.GroundItem;
import org.apollo.game.model.Player;
import org.apollo.game.model.Position;
import org.apollo.game.model.World;
import org.apollo.game.scheduling.ScheduledTask;

/**
 * A {@link ScheduledTask} which processes all ground items currently in game, decreasing their pulses, making them global or removing them if needed.
 * @author Michael Bull (Scu11)
 */
public final class ProcessGroundItemsTask extends ScheduledTask {

	/**
	 * Get the ground item instance.
	 */
	public GroundItem instance = GroundItem.getInstance();

	/**
	 * Creates the ground item process task.
	 */
	public ProcessGroundItemsTask() {
		super(5, false); // 3 seconds
	}

	/*
	 * (non-Javadoc)
	 * @see org.apollo.game.scheduling.ScheduledTask#execute()
	 */
	@Override
	public void execute() {
		for (Position position : instance.getItems().keySet()) {
			GroundItem item = instance.get(position);
			if (item != null) {
				item.decreasePulses();
				if (item.getPulses() == instance.GLOBAL_PULSES) {
					for (Player p : World.getWorld().getPlayerRepository()) {
						if (!p.getName().equals(item.getControllerName()) && p.getPosition().isWithinDistance(position, 60)) {
							p.send(new PositionEvent(p.getPosition(), item.getPosition()));
							p.send(new GroundItemEvent(item.getItem().getId(), item.getItem().getAmount()));
						}
					}
				}
				if (item.getPulses() == 0) {
					instance.delete(item);
					if (instance.get(position) == null) {
						break;
					}
				}
			}
		}
	}
}