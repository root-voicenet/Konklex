package org.apollo.net.release.r317;

import org.apollo.game.event.impl.CharacterDesignEvent;
import org.apollo.game.model.Appearance;
import org.apollo.game.model.Gender;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketReader;
import org.apollo.net.release.EventDecoder;

/**
 * An {@link EventDecoder} for the {@link CharacterDesignEvent}.
 * @author Graham
 */
public final class CharacterDesignEventDecoder extends EventDecoder<CharacterDesignEvent> {

	/*
	 * (non-Javadoc)
	 * @see
	 * org.apollo.net.release.EventDecoder#decode(org.apollo.net.codec.game.
	 * GamePacket)
	 */
	@Override
	public CharacterDesignEvent decode(GamePacket packet) {
		final GamePacketReader reader = new GamePacketReader(packet);
		final int genderIntValue = (int) reader.getUnsigned(DataType.BYTE);
		final int[] style = new int[7];
		for (int i = 0; i < style.length; i++)
			style[i] = (int) reader.getUnsigned(DataType.BYTE);
		final int[] color = new int[5];
		for (int i = 0; i < color.length; i++)
			color[i] = (int) reader.getUnsigned(DataType.BYTE);
		final Gender gender = genderIntValue == Gender.MALE.toInteger() ? Gender.MALE : Gender.FEMALE;
		return new CharacterDesignEvent(new Appearance(gender, style, color));
	}
}
