package org.apollo.net.release.r317;

import org.apollo.game.event.impl.ChatPrivacySettingsEvent;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketReader;
import org.apollo.net.release.EventDecoder;

/**
 * An {@link EventDecoder} that reads the chat privacy settings.
 * @author Steve
 */
public final class ChatPrivacySettingsEventDecoder extends EventDecoder<ChatPrivacySettingsEvent> {

	@Override
	public ChatPrivacySettingsEvent decode(GamePacket packet) {
		final GamePacketReader reader = new GamePacketReader(packet);
		final int publicChat = (int) reader.getUnsigned(DataType.BYTE);
		final int privateChat = (int) reader.getUnsigned(DataType.BYTE);
		final int trade = (int) reader.getUnsigned(DataType.BYTE);
		return new ChatPrivacySettingsEvent(publicChat, privateChat, trade);
	}
}
