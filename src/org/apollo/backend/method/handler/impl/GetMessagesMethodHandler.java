package org.apollo.backend.method.handler.impl;

import org.apollo.backend.method.handler.FrontendHandler;
import org.apollo.backend.method.impl.GetMessagesMethod;
import org.apollo.backend.util.Notification;
import org.apollo.backend.util.Notification.Notify;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * An {@link FrontendHandler} that handles {@link GetMessageEvent} requests.
 * @author Steve
 */
public class GetMessagesMethodHandler extends FrontendHandler<GetMessagesMethod> {

	@Override
	public Object handle(GetMessagesMethod method) throws JSONException {
		JSONArray builder = new JSONArray();
		int id = Integer.parseInt(method.getRequested().getArguments()[0]);
		for (Notify yell : Notification.getInstance().getYells()) {
			if (!yell.getAttribute(id)) {
				JSONObject obj = new JSONObject();
				obj.put("name", yell.getKey());
				obj.put("message", yell.getValue());
				yell.setAttribute(id, true);
				builder.put(obj);
			}
		}
		return builder;
	}

	@Override
	public boolean isError() {
		return false;
	}
}
