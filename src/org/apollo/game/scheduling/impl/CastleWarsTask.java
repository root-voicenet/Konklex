package org.apollo.game.scheduling.impl;

import org.apollo.game.model.World;
import org.apollo.game.model.inter.minigame.CastleWars;
import org.apollo.game.model.inter.minigame.util.GameType;
import org.apollo.game.scheduling.ScheduledTask;

/**
 * The Class CastleWarsTask. {@link CastleWars} ticker.
 * @author Steve
 */
public class CastleWarsTask extends ScheduledTask {

	/**
	 * The instance.
	 */
	private static CastleWarsTask instance;

	/**
	 * Initialize the task.
	 */
	public CastleWarsTask() {
		super(1, true);
		World.getWorld().schedule(this);
	}

	/*
	 * (non-Javadoc)
	 * @see org.apollo.game.scheduling.ScheduledTask#execute()
	 */
	@Override
	public void execute() {
		CastleWars cw = CastleWars.getInstance();
		if (!cw.getAttribute(0)) {
			if (!cw.getPlayers(GameType.WAITING).isEmpty()) {
				if (cw.getTick() != 0 && cw.getTick() % 60 == 0) {
					cw.setAttribute(2, true);
				}
				cw.process();
			} else {
				if (cw.getTick() != 180) {
					cw.setTick(180);
				}
			}
		} else {
			if (cw.getTick() != 0 && cw.getTick() % 60 == 0) {
				cw.setAttribute(2, true);
			}
			cw.process();
		}
	}

	/**
	 * Start the task.
	 */
	public static void start() {
		if (instance == null) {
			instance = new CastleWarsTask();
		}
	}
}
