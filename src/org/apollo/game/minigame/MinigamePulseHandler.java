package org.apollo.game.minigame;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A class which handles the logic for each pulse of the {@link MinigameService} .
 * @author Steve
 */
public final class MinigamePulseHandler implements Runnable {

	/**
	 * The logger for this class.
	 */
	private static final Logger logger = Logger.getLogger(MinigamePulseHandler.class.getName());

	/**
	 * The {@link MinigameService}.
	 */
	private final MinigameService service;

	/**
	 * Creates the minigame pulse handler object.
	 * @param service The {@link MinigameService}.
	 */
	MinigamePulseHandler(MinigameService service) {
		this.service = service;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		try {
			service.pulse();
		}
		catch (final Throwable t) {
			logger.log(Level.INFO, "Exception during pulse.", t);
		}
	}
}
