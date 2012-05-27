package org.apollo.backend.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apollo.backend.codec.FrontendPacket;
import org.apollo.backend.codec.FrontendPacketBuilder;
import org.apollo.backend.method.MethodEncoder;
import org.apollo.backend.method.impl.StatusMethod;
import org.apollo.game.model.Config;
import org.apollo.game.model.Player;
import org.apollo.game.model.World;
import org.apollo.game.scheduling.impl.SystemUpdateTask;
import org.apollo.util.SystemUtil;
import org.json.JSONArray;

/**
 * An {@link MethodEncoder} for the {@link StatusMethod}
 * @author Steve
 */
public final class StatusMethodEncoder extends MethodEncoder<StatusMethod> {

	/**
	 * The date format.
	 */
	private DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

	@Override
	public FrontendPacket encode(StatusMethod method) {
		FrontendPacketBuilder builder = new FrontendPacketBuilder("getStatus");
		String status = SystemUpdateTask.isUpdating() ? "update" : Config.SERVER_WHITELIST ? "down" : "up";
		builder.addParameter("status", status);
		builder.addParameter("users", World.getWorld().getPlayerRepository().size());
		builder.addParameter("ram", SystemUtil.getRamUsage());
		builder.addParameter("cpuusage", SystemUtil.getCpuUsage());
		builder.addParameter("pid", SystemUtil.getProcessId());
		builder.addParameter("time", dateFormat.format(new Date()));
		JSONArray users = new JSONArray();
		for (Player player : World.getWorld().getPlayerRepository()) {
			users.put(player.getName());
		}
		builder.addParameter("user", users);
		return builder.toFrontendPacket();
	}
}
