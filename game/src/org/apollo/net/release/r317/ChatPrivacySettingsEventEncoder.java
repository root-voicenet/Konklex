package org.apollo.net.release.r317;

import org.apollo.game.event.impl.ChatPrivacySettingsEvent;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketBuilder;
import org.apollo.net.release.EventEncoder;

/**
 * An {@link EventEncoder} for the {@link ChatPrivacySettingsEvent}
 * @author Steve
 */
public final class ChatPrivacySettingsEventEncoder extends EventEncoder<ChatPrivacySettingsEvent> {

	@Override
	public GamePacket encode(ChatPrivacySettingsEvent event) {
		final GamePacketBuilder builder = new GamePacketBuilder(206);
		builder.put(DataType.BYTE, event.getPublicChat());
		builder.put(DataType.BYTE, event.getPrivateChat());
		builder.put(DataType.BYTE, event.getTrade());
		return builder.toGamePacket();
	}
}