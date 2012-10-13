package org.apollo.api;

import org.apollo.api.method.MethodDecoder;
import org.apollo.api.method.impl.TimeMethod;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketReader;

/**
 * An {@link MethodDecoder} for the {@link TimeMethod}
 * @author Steve
 */
public final class TimeMethodDecoder extends MethodDecoder<TimeMethod> {

	@Override
	public TimeMethod decode(GamePacket packet) {
		GamePacketReader reader = new GamePacketReader(packet);
		int world = (int) reader.getUnsigned(DataType.BYTE);
		int objects = (int) reader.getUnsigned(DataType.INT);
		int npcs = (int) reader.getUnsigned(DataType.INT);
		int items = (int) reader.getUnsigned(DataType.INT);
		int regions = (int) reader.getUnsigned(DataType.INT);
		long time = reader.getUnsigned(DataType.INT);
		int threads = (int) reader.getUnsigned(DataType.INT);
		int cpu = (int) reader.getUnsigned(DataType.INT);
		int ram = (int) reader.getUnsigned(DataType.INT);
		int status = (int) reader.getUnsigned(DataType.BYTE);
		return new TimeMethod(world, objects, npcs, items, regions, time, threads, cpu, ram, status);
	}

}
