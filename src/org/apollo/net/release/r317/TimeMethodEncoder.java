package org.apollo.net.release.r317;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

import org.apollo.api.method.impl.TimeMethod;
import org.apollo.game.model.World;
import org.apollo.game.scheduling.impl.SystemUpdateTask;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketBuilder;
import org.apollo.net.release.MethodEncoder;
import org.apollo.util.SystemUtil;

/**
 * An {@link MethodEncoder} for the {@link TimeMethod}
 * @author Steve
 */
public final class TimeMethodEncoder extends MethodEncoder<TimeMethod> {

	@Override
	public GamePacket encode(TimeMethod method) {
		GamePacketBuilder builder = new GamePacketBuilder(4);
		builder.put(DataType.BYTE, World.getWorld().getId()); // 1
		builder.put(DataType.INT, World.getWorld().getObjects().size()); // 1
		builder.put(DataType.INT, World.getWorld().getNpcRepository().size());
		builder.put(DataType.INT, World.getWorld().getItems().size());
		builder.put(DataType.INT, World.getWorld().getRegionManager().size());
		builder.put(DataType.INT, method.getTime()); // 4
		ThreadMXBean bean = ManagementFactory.getThreadMXBean();
		builder.put(DataType.INT, bean.getThreadCount());
		builder.put(DataType.INT, SystemUtil.getCpuUsage());
		builder.put(DataType.INT, SystemUtil.getRamUsage());
		builder.put(DataType.BYTE, SystemUpdateTask.isUpdating() ? 1 : 0);
		// 34
		return builder.toGamePacket();
	}

}
