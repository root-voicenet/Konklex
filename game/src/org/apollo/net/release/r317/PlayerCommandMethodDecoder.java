package org.apollo.net.release.r317;

import org.apollo.api.method.impl.PlayerCommandMethod;
import org.apollo.net.codec.game.DataTransformation;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketReader;
import org.apollo.net.release.MethodDecoder;
import org.apollo.util.TextUtil;

/**
 * An {@link MethodDecoder} for the {@link PlayerCommandMethod}
 * @author Steve
 */
public final class PlayerCommandMethodDecoder extends MethodDecoder<PlayerCommandMethod> {

	@Override
	public PlayerCommandMethod decode(GamePacket packet) {
		GamePacketReader reader = new GamePacketReader(packet);
		long player = reader.getSigned(DataType.LONG, DataTransformation.QUADRUPLE);
		final int length = (byte) (packet.getLength() - 8);
		final byte[] originalCompressed = new byte[length];
		reader.getBytes(originalCompressed);
		String uncompressed = TextUtil.uncompress(originalCompressed, length);
		uncompressed = TextUtil.filterInvalidCharacters(uncompressed);
		uncompressed = TextUtil.capitalize(uncompressed);
		final byte[] recompressed = new byte[length];
		TextUtil.compress(uncompressed, recompressed);
		return new PlayerCommandMethod(player, uncompressed);
	}

}
