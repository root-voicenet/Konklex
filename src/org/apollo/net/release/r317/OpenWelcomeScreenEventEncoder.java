package org.apollo.net.release.r317;

import org.apollo.game.event.impl.OpenWelcomeScreenEvent;
import org.apollo.net.codec.game.DataTransformation;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketBuilder;
import org.apollo.net.release.EventEncoder;

/**
 * An {@link EventEncoder} for the {@link OpenWelcomeScreenEvent}
 * @author Steve
 */
public class OpenWelcomeScreenEventEncoder extends EventEncoder<OpenWelcomeScreenEvent> {

	@Override
	public GamePacket encode(OpenWelcomeScreenEvent event) {
		GamePacketBuilder builder = new GamePacketBuilder(176);
		builder.put(DataType.BYTE, event.getDaysSinceLastRecovery());
		builder.put(DataType.SHORT, DataTransformation.ADD, event.getUnreadMessages());
		builder.put(DataType.BYTE, event.isMember() ? 1 : 0);
		builder.put(DataType.INT, event.getLastLoggedIp());
		builder.put(DataType.SHORT, event.getLastLoggedIn());
		return builder.toGamePacket();
	}
}
