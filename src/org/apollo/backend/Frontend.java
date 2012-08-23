package org.apollo.backend;

import java.util.HashMap;
import java.util.Map;

import org.apollo.backend.impl.ConfigMethodDecoder;
import org.apollo.backend.impl.GiveItemMethodDecoder;
import org.apollo.backend.impl.HiscoreMethodDecoder;
import org.apollo.backend.impl.HiscoreMethodEncoder;
import org.apollo.backend.impl.KickMethodDecoder;
import org.apollo.backend.impl.LoginMethodDecoder;
import org.apollo.backend.impl.NotificationMethodEncoder;
import org.apollo.backend.impl.OnebipIpnMethodDecoder;
import org.apollo.backend.impl.PaypalIpnMethodDecoder;
import org.apollo.backend.impl.RegisterMethodDecoder;
import org.apollo.backend.impl.ResponseMethodEncoder;
import org.apollo.backend.impl.SendMessageMethodDecoder;
import org.apollo.backend.impl.SendYellMethodDecoder;
import org.apollo.backend.impl.SetPositionMethodDecoder;
import org.apollo.backend.impl.SetPrivilegeLevelMethodDecoder;
import org.apollo.backend.impl.StatusMethodDecoder;
import org.apollo.backend.impl.StatusMethodEncoder;
import org.apollo.backend.impl.TakeItemMethodDecoder;
import org.apollo.backend.method.Method;
import org.apollo.backend.method.MethodDecoder;
import org.apollo.backend.method.MethodEncoder;
import org.apollo.backend.method.impl.HiscoreMethod;
import org.apollo.backend.method.impl.NotificationMethod;
import org.apollo.backend.method.impl.ResponseMethod;
import org.apollo.backend.method.impl.StatusMethod;

/**
 * The core class for handling frontend events.
 * @author Steve
 */
public final class Frontend {

	/**
	 * The instance.
	 */
	private static final Frontend instance = new Frontend();

	/**
	 * Gets the frontend instance.
	 * @return The frontend instance.
	 */
	public static Frontend getInstance() {
		return instance;
	}

	/**
	 * The decoders.
	 */
	private final Map<String, MethodDecoder<?>> decoders = new HashMap<String, MethodDecoder<?>>();

	/**
	 * The encoders.
	 */
	private final Map<Class<? extends Method>, MethodEncoder<?>> encoders = new HashMap<Class<? extends Method>, MethodEncoder<?>>();

	/**
	 * Registers the encoders and decoders.
	 */
	public Frontend() {
		// decoders
		register("sendYell", new SendYellMethodDecoder());
		register("kickPlayer", new KickMethodDecoder());
		register("getStatus", new StatusMethodDecoder());
		register("giveItem", new GiveItemMethodDecoder());
		register("takeItem", new TakeItemMethodDecoder());
		register("setPosition", new SetPositionMethodDecoder());
		register("setPrivilege", new SetPrivilegeLevelMethodDecoder());
		register("sendMessage", new SendMessageMethodDecoder());
		register("getHiscore", new HiscoreMethodDecoder());
		register("paypalIpn", new PaypalIpnMethodDecoder());
		register("onebipIpn", new OnebipIpnMethodDecoder());
		register("register", new RegisterMethodDecoder());
		register("login", new LoginMethodDecoder());
		register("sendConfig", new ConfigMethodDecoder());
		// encoders
		register(StatusMethod.class, new StatusMethodEncoder());
		register(ResponseMethod.class, new ResponseMethodEncoder());
		register(HiscoreMethod.class, new HiscoreMethodEncoder());
		register(NotificationMethod.class, new NotificationMethodEncoder());
	}

	/**
	 * Gets the {@link MethodDecoder} for the specified opcode.
	 * @param opcode The opcode.
	 * @return The {@link MethodDecoder}.
	 */
	@SuppressWarnings("unchecked")
	public final <E extends Method> MethodDecoder<E> getMethodDecoder(String opcode) {
		return (MethodDecoder<E>) decoders.get(opcode);
	}

	/**
	 * Gets an {@link MethodEncoder} for the specified method type.
	 * @param <E> the element type.
	 * @param type The type of method.
	 * @return The {@link MethodEncoder}.
	 */
	@SuppressWarnings("unchecked")
	public final <E extends Method> MethodEncoder<E> getMethodEncoder(Class<E> type) {
		return (MethodEncoder<E>) encoders.get(type);
	}

	/**
	 * Registers a {@link MethodEncoder} for the specified method type.
	 * @param <E> the element type.
	 * @param type The method type.
	 * @param encoder The {@link MethodEncoder}.
	 */
	public final <E extends Method> void register(Class<E> type, MethodEncoder<E> encoder) {
		encoders.put(type, encoder);
	}

	/**
	 * Registers a {@link MethodDecoder} for the specified opcode.
	 * @param opcode The opcode.
	 * @param decoder The {@link MethodDecoder}.
	 * @param <E> the element type.
	 */
	public final <E extends Method> void register(String opcode, MethodDecoder<E> decoder) {
		decoders.put(opcode, decoder);
	}
}
