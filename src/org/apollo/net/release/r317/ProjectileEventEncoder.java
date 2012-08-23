package org.apollo.net.release.r317;

import org.apollo.game.event.impl.ProjectileEvent;
import org.apollo.game.model.Position;
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
		final Position position = event.getPosition();
		builder.put(DataType.BYTE, event.getAngle());
		builder.put(DataType.BYTE, position.getY());
		builder.put(DataType.BYTE, position.getX());
		builder.put(DataType.SHORT, event.getVictim());
		builder.put(DataType.SHORT, event.getId());
		builder.put(DataType.BYTE, event.getHeight());
		builder.put(DataType.BYTE, position.getHeight());
		builder.put(DataType.SHORT, 51);
		builder.put(DataType.SHORT, event.getSpeed());
		builder.put(DataType.BYTE, 16);
		builder.put(DataType.BYTE, 64);
		return builder.toGamePacket();
	}
}