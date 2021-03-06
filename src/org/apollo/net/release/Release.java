package org.apollo.net.release;

import java.util.HashMap;
import java.util.Map;

import org.apollo.api.method.Method;
import org.apollo.game.event.Event;
import org.apollo.net.meta.PacketMetaData;
import org.apollo.net.meta.PacketMetaDataGroup;

/**
 * A {@link Release} is a distinct client version, for example 317 is a common release used in server emulators.
 * @author Graham
 * @author Steve
 */
public abstract class Release {

	/**
	 * The release number, e.g. {@code 317}.
	 */
	private final int releaseNumber;

	/**
	 * The decoders.
	 */
	private final EventDecoder<?>[] decoders = new EventDecoder<?>[256];

	/**
	 * The api decoders.
	 */
	private final MethodDecoder<?>[] methodDecoders = new MethodDecoder<?>[256];

	/**
	 * The encoders.
	 */
	private final Map<Class<? extends Event>, EventEncoder<?>> encoders = new HashMap<Class<? extends Event>, EventEncoder<?>>();

	/**
	 * The api encoders.
	 */
	private final Map<Class<? extends Method>, MethodEncoder<?>> methodEncoders = new HashMap<Class<? extends Method>, MethodEncoder<?>>();

	/**
	 * The incoming packet meta data.
	 */
	private final PacketMetaDataGroup incomingPacketMetaData;

	/**
	 * The api incoming packet meta data.
	 */
	private final PacketMetaDataGroup incomingApiPacketMetaData;

	/**
	 * Creates the release.
	 * @param releaseNumber The release number.
	 * @param incomingPacketMetaData The incoming packet meta data.
	 */
	public Release(int releaseNumber, PacketMetaDataGroup incomingPacketMetaData) {
		this.releaseNumber = releaseNumber;
		this.incomingPacketMetaData = incomingPacketMetaData;
		this.incomingApiPacketMetaData = incomingPacketMetaData;
	}

	/**
	 * Creates the release.
	 * @param releaseNumber The release number.
	 * @param incomingPacketMetaData The incoming meta data.
	 * @param incomingApiPacketMetaData The incoming api meta data.
	 */
	public Release(int releaseNumber, PacketMetaDataGroup incomingPacketMetaData,
			PacketMetaDataGroup incomingApiPacketMetaData) {
		this.releaseNumber = releaseNumber;
		this.incomingPacketMetaData = incomingPacketMetaData;
		this.incomingApiPacketMetaData = incomingApiPacketMetaData;
	}

	/**
	 * Gets the {@link EventDecoder} for the specified opcode.
	 * @param opcode The opcode.
	 * @return The {@link EventDecoder}.
	 */
	public final EventDecoder<?> getEventDecoder(int opcode) {
		if (opcode < 0 || opcode >= decoders.length)
			throw new IndexOutOfBoundsException();
		return decoders[opcode];
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
	 * Gets an {@link EventEncoder} for the specified event type.
	 * @param <E> the element type
	 * @param type The type of event.
	 * @return The {@link EventEncoder}.
	 */
	@SuppressWarnings("unchecked")
	public <E extends Event> EventEncoder<E> getEventEncoder(Class<E> type) {
		return (EventEncoder<E>) encoders.get(type);
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
	 * Gets meta data for the specified incoming packet.
	 * @param opcode The opcode of the incoming packet.
	 * @return The {@link PacketMetaData} object.
	 */
	public final PacketMetaData getIncomingApiPacketMetaData(int opcode) {
		return incomingApiPacketMetaData.getMetaData(opcode);
	}

	/**
	 * Gets the release number.
	 * @return The release number.
	 */
	public final int getReleaseNumber() {
		return releaseNumber;
	}

	/**
	 * Registers a {@link EventEncoder} for the specified event type.
	 * @param <E> the element type
	 * @param type The event type.
	 * @param encoder The {@link EventEncoder}.
	 */
	public final <E extends Event> void register(Class<E> type, EventEncoder<E> encoder) {
		encoders.put(type, encoder);
	}

	/**
	 * Registers a {@link MethodEncoder} for the specified method type.
	 * @param <E> the element type
	 * @param type The method type.
	 * @param encoder The {@link MethodEncoder}.
	 */
	public final <E extends Method> void register(Class<E> type, MethodEncoder<E> encoder) {
		methodEncoders.put(type, encoder);
	}

	/**
	 * Registers a {@link EventDecoder} for the specified opcode.
	 * @param decoder The {@link EventDecoder}.
	 * @param opcodes The opcodes, between 0 and 255 inclusive.
	 * @param <E> the element type
	 */
	public final <E extends Event> void register(EventDecoder<E> decoder, int... opcodes) {
		for (final int opcode : opcodes) {
			if (opcode < 0 || opcode >= decoders.length)
				throw new IndexOutOfBoundsException();
			decoders[opcode] = decoder;
		}
	}

	/**
	 * Registers a {@link MethodDecoder} for the specified opcode.
	 * @param decoder The {@link MethodDecoder}.
	 * @param opcodes The opcodes, between 0 and 255 inclusive.
	 * @param <E> the element type
	 */
	public final <E extends Method> void register(MethodDecoder<E> decoder, int... opcodes) {
		for (final int opcode : opcodes) {
			if (opcode < 0 || opcode >= methodDecoders.length)
				throw new IndexOutOfBoundsException();
			methodDecoders[opcode] = decoder;
		}
	}
}
