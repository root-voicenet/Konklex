package org.apollo.net.release.r317;

import org.apollo.game.event.impl.PrivateChatEvent;
import org.apollo.net.codec.game.DataTransformation;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketReader;
import org.apollo.net.release.EventDecoder;
import org.apollo.util.TextUtil;

/**
 * An {@link EventDecoder} for the {@link PrivateChatEvent}.
 * @author Steve
 */
public class PrivateChatEventDecoder extends EventDecoder<PrivateChatEvent> {

	/*
	 * (non-Javadoc)
	 * @see org.apollo.net.release.EventDecoder#decode(org.apollo.net.codec.game.GamePacket)
	 */
	@Override
	public PrivateChatEvent decode(GamePacket packet) {
		GamePacketReader reader = new GamePacketReader(packet);
		long friend = reader.getSigned(DataType.LONG, DataTransformation.QUADRUPLE);
		final int length = (byte) (packet.getLength() - 8);
		byte[] originalCompressed = new byte[length];
		reader.getBytes(originalCompressed);
		String uncompressed = TextUtil.uncompress(originalCompressed, length);
		uncompressed = TextUtil.filterInvalidCharacters(uncompressed);
		uncompressed = TextUtil.capitalize(uncompressed);
		byte[] recompressed = new byte[length];
		TextUtil.compress(uncompressed, recompressed);
		return new PrivateChatEvent(uncompressed, recompressed, friend);
	}
}
