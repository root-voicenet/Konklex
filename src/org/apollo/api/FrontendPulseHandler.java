package org.apollo.api;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A class which handles the logic for each pulse of the {@link FrontendService}.
 * @author Steve
 */
public final class FrontendPulseHandler implements Runnable {

	/**
	 * The logger for this class.
	 */
	private static final Logger logger = Logger
			.getLogger(FrontendPulseHandler.class.getName());

	/**
	 * The {@link FrontendService}.
	 */
	private final FrontendService service;

	/**
	 * Creates the frontend pulse handler object.
	 * 
	 * @param service
	 *            The {@link FrontendService}.
	 */
	FrontendPulseHandler(FrontendService service) {
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
		} catch (final Throwable t) {
			logger.log(Level.SEVERE, "Exception during pulse.", t);
		}
	}
}