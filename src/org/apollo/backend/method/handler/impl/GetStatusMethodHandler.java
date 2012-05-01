package org.apollo.backend.method.handler.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apollo.backend.method.handler.FrontendHandler;
import org.apollo.backend.method.impl.GetStatusMethod;
import org.apollo.backend.util.Notification;
import org.apollo.backend.util.Notification.Notify;
import org.apollo.game.model.Config;
import org.apollo.game.model.Player;
import org.apollo.game.model.World;
import org.apollo.game.scheduling.impl.SystemUpdateTask;
import org.apollo.util.SystemUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * An {@link FrontendHandler} that handles {@link GetStatusMethod} requests.
 * @author Steve
 */
public class GetStatusMethodHandler extends FrontendHandler<GetStatusMethod> {

	private DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

	@Override
	public JSONObject handle(GetStatusMethod method) throws JSONException {
		JSONObject builder = new JSONObject();
		int id = Integer.parseInt(method.getRequested().getArguments()[0]);
		Date date = new Date();
		builder.put("status", SystemUpdateTask.isUpdating() ? "update" : Config.SERVER_WHITELIST ? "down" : "up");
		builder.put("users", World.getWorld().getPlayerRepository().size());
		builder.put("ram", SystemUtil.getRamUsage());
		builder.put("cpuusage", SystemUtil.getCpuUsage());
		builder.put("pid", SystemUtil.getProcessId());
		builder.put("time", dateFormat.format(date));
		JSONArray users = new JSONArray();
		for (Player player : World.getWorld().getPlayerRepository()) {
			users.put(player.getName());
		}
		builder.put("user", users);
		JSONArray notify = new JSONArray();
		for (Notify yell : Notification.getInstance().getNotifications()) {
			if (!yell.getAttribute(id)) {
				JSONObject obj = new JSONObject();
				obj.put("name", yell.getKey());
				obj.put("message", yell.getValue());
				notify.put(obj);
				yell.setAttribute(id, true);
			}
		}
		builder.put("notifications", notify);
		return builder;
	}

	@Override
	public boolean isError() {
		return false;
	}
}
