package org.apollo.api;

import java.util.HashMap;
import java.util.Map;

import org.apollo.api.method.Method;
import org.apollo.api.method.MethodDecoder;
import org.apollo.api.method.MethodEncoder;
import org.apollo.api.method.impl.LabelWorldMethod;
import org.apollo.api.method.impl.PlayerCommandMethod;
import org.apollo.api.method.impl.PrivateChatMethod;
import org.apollo.api.method.impl.ReceivePlayerMethod;
import org.apollo.api.method.impl.SendFriendMethod;
import org.apollo.api.method.impl.UpdateMethod;
import org.apollo.net.meta.PacketMetaData;
import org.apollo.net.meta.PacketMetaDataGroup;

/**
 * A {@link Frontend} is a distinct client version, for example 317 is a common
 * release used in server emulators.
 * @author Graham
 * @author Steve
 */
public final class Frontend {
	
	/**
	 * The api decoders.
	 */
	private final MethodDecoder<?>[] methodDecoders = new MethodDecoder<?>[256];
	
	/**
	 * The api encoders.
	 */
	private final Map<Class<? extends Method>, MethodEncoder<?>> methodEncoders = new HashMap<Class<? extends Method>, MethodEncoder<?>>();

	/**
	 * The incoming packet meta data.
	 */
	private final PacketMetaDataGroup incomingPacketMetaData;
	
	/**
	 * The incoming packet lengths array.
	 */
	public static final int[] PACKET_LENGTHS = { 
		0, 16, 11, -1, 34, 0, 0, 0, 0, 0,// 0
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0, // 10
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0, // 20
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0, // 30
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0, // 40
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0, // 50
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0, // 60
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0, // 70
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0, // 80
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0, // 90
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0, // 100
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0, // 110
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0, // 120
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0, // 130
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0, // 140
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0, // 150
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0, // 160
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0, // 170
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0, // 180
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0, // 190
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0, // 200
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0, // 210
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0, // 220
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0, // 230
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0,// 240
		0, 0, 0, 0, 0, 0, // 250
	};

	/**
	 * Creates the release.
	 */
	public Frontend() {
		this.incomingPacketMetaData = PacketMetaDataGroup.createFromArray(PACKET_LENGTHS);
		
		// register encoders
		register(PrivateChatMethod.class, new PrivateChatMethodEncoder());
		register(SendFriendMethod.class, new SendFriendMethodEncoder());
		register(LabelWorldMethod.class, new LabelWorldMethodEncoder());
		register(ReceivePlayerMethod.class, new ReceivePlayerMethodEncoder());
		register(UpdateMethod.class, new UpdateMethodEncoder());
		register(PlayerCommandMethod.class, new PlayerCommandMethodEncoder());
		// register(ExecuteMethod.class, new ExecuteMethodEncoder());
		// register decoders
		register(new ReceiveFriendMethodDecoder(), 1);
		register(new ReceivePlayerMethodDecoder(), 2);
		register(new PrivateChatMethodDecoder(), 3);
		register(new TimeMethodDecoder(), 4);
	}
	
	/**
	 * Gets the {@link MethodDecoder} for the specified opcode.
	 * @param opcode The opcode.
	 * @return The {@link MethodDecoder}.
	 */
	public final MethodDecoder<?> getMethodDecoder(int opcode) {
		if (opcode < 0 || opcode >= methodDecoders.length)
			throw new IndexOutOfBoundsException();
		return methodDecoders[opcode];
	}
	
	/**
	 * Gets an {@link MethodEncoder} for the specified method type.
	 * @param <E> the element type
	 * @param type The type of method.
	 * @return The {@link MethodEncoder}.
	 */
	@SuppressWarnings("unchecked")
	public <E extends Method> MethodEncoder<E> getMethodEncoder(Class<E> type) {
		return (MethodEncoder<E>) methodEncoders.get(type);
	}
	
	/**
	 * Gets meta data for the specified incoming packet.
	 * @param opcode The opcode of the incoming packet.
	 * @return The {@link PacketMetaData} object.
	 */
	public final PacketMetaData getIncomingPacketMetaData(int opcode) {
		return incomingPacketMetaData.getMetaData(opcode);
	}
	
	/**
	 * Registers a {@link MethodEncoder} for the specified method type.
	 * @param <E> the element type
	 * @param type The method type.
	 * @param encoder The {@link MethodEncoder}.
	 */
	private final <E extends Method> void register(Class<E> type, MethodEncoder<E> encoder) {
		methodEncoders.put(type, encoder);
	}
	
	/**
	 * Registers a {@link MethodDecoder} for the specified opcode.
	 * @param decoder The {@link MethodDecoder}.
	 * @param opcodes The opcodes, between 0 and 255 inclusive.
	 * @param <E> the element type
	 */
	private final <E extends Method> void register(MethodDecoder<E> decoder, int... opcodes) {
		for (final int opcode : opcodes) {
			if (opcode < 0 || opcode >= methodDecoders.length)
				throw new IndexOutOfBoundsException();
			methodDecoders[opcode] = decoder;
		}
	}
}
