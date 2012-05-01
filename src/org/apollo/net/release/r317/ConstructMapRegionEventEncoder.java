package org.apollo.net.release.r317;

import org.apollo.game.event.impl.ConstructMapRegionEvent;
import org.apollo.game.model.Position;
import org.apollo.net.codec.game.DataTransformation;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketBuilder;
import org.apollo.net.meta.PacketType;
import org.apollo.net.release.EventEncoder;

/**
 * An {@link EventEncoder} for the {@link ConstructMapRegionEvent}
 * @author Steve
 */
public final class ConstructMapRegionEventEncoder extends EventEncoder<ConstructMapRegionEvent> {

	@Override
	public GamePacket encode(ConstructMapRegionEvent event) {
		GamePacketBuilder builder = new GamePacketBuilder(241, PacketType.VARIABLE_SHORT);
		Position position = event.getPosition();
		builder.put(DataType.SHORT, DataTransformation.ADD, position.getCentralRegionY() + 6);
		builder.switchToBitAccess();
		for (int z = 0; z < 4; z++) {
			for (int x = 0; x < 13; x++) {
				for (int y = 0; y < 13; y++) {
					builder.putBit(event.isDisplayable());
					if (event.isDisplayable()) {
						builder.putBits(26, position.getX() << 14 | position.getY() << 3);
					}
				}
			}
		}
		builder.switchToByteAccess();
		builder.put(DataType.SHORT, position.getCentralRegionX() + 6);
		return builder.toGamePacket();
	}
}
