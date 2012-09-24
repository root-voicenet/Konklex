package org.apollo.net.release.r317;

import org.apollo.game.event.impl.ProjectileEvent;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketBuilder;
import org.apollo.net.release.EventEncoder;

/**
 * An {@link EventEncoder} for the {@link ProjectileEvent}
 * @author Steve
 */
public final class ProjectileEventEncoder extends EventEncoder<ProjectileEvent> {

	@Override
	public GamePacket encode(ProjectileEvent event) {
		final GamePacketBuilder builder = new GamePacketBuilder(117);
		builder.put(DataType.BYTE, 50);
		builder.put(DataType.BYTE, event.getOffsetX());
		builder.put(DataType.BYTE, event.getOffsetY());
		builder.put(DataType.SHORT, event.getLockOn());
		builder.put(DataType.SHORT, event.getProjectileId());
		builder.put(DataType.BYTE, event.getStartHeight());
		builder.put(DataType.BYTE, event.getEndHeight());
		builder.put(DataType.SHORT, event.getDelay());
		builder.put(DataType.SHORT, event.getDuration());
		builder.put(DataType.BYTE, event.getCurve());
		builder.put(DataType.BYTE, 64);
		return builder.toGamePacket();
	}
}