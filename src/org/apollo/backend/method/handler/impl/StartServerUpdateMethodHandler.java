package org.apollo.backend.method.handler.impl;

import org.apollo.backend.method.handler.FrontendHandler;
import org.apollo.backend.method.impl.StartServerUpdateMethod;
import org.apollo.game.scheduling.impl.SystemUpdateTask;
import org.json.JSONException;

/**
 * An {@link FrontendHandler} that handles {@link StartServerUpdateMethod} requests.
 * @author Steve
 */
public class StartServerUpdateMethodHandler extends FrontendHandler<StartServerUpdateMethod> {

	/**
	 * Flag for errors.
	 */
	private boolean error = false;

	@Override
	public Object handle(StartServerUpdateMethod method) throws JSONException {
		boolean serverUpdate = SystemUpdateTask.start();
		this.error = !serverUpdate;
		return "executed";
	}

	@Override
	public boolean isError() {
		return error;
	}
}
