package org.apollo.net.release.r317;

import org.apollo.api.method.impl.PrivateChatMethod;
import org.apollo.net.codec.game.DataTransformation;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketReader;
import org.apollo.net.release.MethodDecoder;
import org.apollo.util.TextUtil;

/**
 * An {@link MethodDecoder} for the {@link PrivateChatMethod}
 * @author Steve
 */
public final class PrivateChatMethodDecoder extends MethodDecoder<PrivateChatMethod> {

	@Override
	public PrivateChatMethod decode(GamePacket packet) {
		final GamePacketReader reader = new GamePacketReader(packet);
		final long friend = reader.getSigned(DataType.LONG, DataTransformation.QUADRUPLE); //8
		final long sender = reader.getSigned(DataType.LONG, DataTransformation.QUADRUPLE); //8
		final int rights = (int) reader.getUnsigned(DataType.BYTE); //1
		final int length = (byte) (packet.getLength() - 17);
		final byte[] originalCompressed = new byte[length];
		reader.getBytes(originalCompressed);
		String uncompressed = TextUtil.uncompress(originalCompressed, length);
		uncompressed = TextUtil.filterInvalidCharacters(uncompressed);
		uncompressed = TextUtil.capitalize(uncompressed);
		final byte[] recompressed = new byte[length];
		TextUtil.compress(uncompressed, recompressed);
		return new PrivateChatMethod(friend, sender, rights, recompressed);
	}

}
