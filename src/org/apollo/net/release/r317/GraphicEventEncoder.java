package org.apollo.net.release.r317;

import org.apollo.game.event.impl.GraphicEvent;
import org.apollo.game.model.Graphic;
import org.apollo.game.model.Position;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketBuilder;
import org.apollo.net.release.EventEncoder;

/**
 * An {@link EventEncoder} for the {@link GraphicEvent}
 * @author Steve
 */
public final class GraphicEventEncoder extends EventEncoder<GraphicEvent> {

	@Override
	public GamePacket encode(GraphicEvent event) {
		GamePacketBuilder builder = new GamePacketBuilder(4);
		Graphic graphic = event.getGraphic();
		final Position position = event.getPosition();
		final int offset = position.getLocalSectorX() << 4 | position.getLocalSectorY();
		builder.put(DataType.BYTE, offset); // implement this accordingly
		builder.put(DataType.SHORT, graphic.getId());
		builder.put(DataType.BYTE, graphic.getHeight());
		builder.put(DataType.SHORT, graphic.getDelay());
		return builder.toGamePacket();
	}

}