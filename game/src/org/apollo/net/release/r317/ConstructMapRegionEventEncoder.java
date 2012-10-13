package org.apollo.net.release.r317;

import org.apollo.game.event.impl.ConstructMapRegionEvent;
import org.apollo.game.model.Palette;
import org.apollo.game.model.Palette.PaletteTile;
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
		final GamePacketBuilder builder = new GamePacketBuilder(241, PacketType.VARIABLE_SHORT);
		final Palette palette = event.getPalette();
		final Position position = event.getPosition();
		builder.put(DataType.SHORT, DataTransformation.ADD, position.getRegionY() + 6);
		builder.switchToBitAccess();
		for (int z = 0; z < 4; z++)
			for (int x = 0; x < 13; x++)
				for (int y = 0; y < 13; y++) {
					final PaletteTile tile = palette.getTile(x, y, z);
					builder.putBit(tile != null);
					if (tile != null)
						builder.putBits(26,
								tile.getX() << 14 | tile.getY() << 3 | tile.getZ() << 24 | tile.getRotation() << 1);
				}
		builder.switchToByteAccess();
		builder.put(DataType.SHORT, position.getRegionX() + 6);
		return builder.toGamePacket();
	}
}
