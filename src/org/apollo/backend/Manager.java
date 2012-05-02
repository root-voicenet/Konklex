package org.apollo.backend;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apollo.backend.method.Method;
import org.apollo.backend.method.handler.FrontendHandler;
import org.apollo.backend.method.handler.impl.GetFrontendInformationMethodHandler;
import org.apollo.backend.method.handler.impl.GetGroupsMethodHandler;
import org.apollo.backend.method.handler.impl.GetLatestUpdateMethodHandler;
import org.apollo.backend.method.handler.impl.GetMessagesMethodHandler;
import org.apollo.backend.method.handler.impl.GetServerConfigMethodHandler;
import org.apollo.backend.method.handler.impl.GetStatusMethodHandler;
import org.apollo.backend.method.handler.impl.GivePlayerItemMethodHandler;
import org.apollo.backend.method.handler.impl.SendGlobalYellMethodHandler;
import org.apollo.backend.method.handler.impl.SetPlayerKickMethodHandler;
import org.apollo.backend.method.handler.impl.SetPlayerMessageMethodHandler;
import org.apollo.backend.method.handler.impl.SetPlayerRightsMethodHandler;
import org.apollo.backend.method.handler.impl.SetPlayerTeleportMethodHandler;
import org.apollo.backend.method.handler.impl.SetServerConfigMethodHandler;
import org.apollo.backend.method.handler.impl.StartServerUpdateMethodHandler;
import org.apollo.backend.method.impl.GetFrontendInformationMethod;
import org.apollo.backend.method.impl.GetGroupsMethod;
import org.apollo.backend.method.impl.GetLatestUpdateMethod;
import org.apollo.backend.method.impl.GetMessagesMethod;
import org.apollo.backend.method.impl.GetServerConfigMethod;
import org.apollo.backend.method.impl.GetStatusMethod;
import org.apollo.backend.method.impl.GivePlayerItemMethod;
import org.apollo.backend.method.impl.SendGlobalYellMethod;
import org.apollo.backend.method.impl.SetPlayerKickMethod;
import org.apollo.backend.method.impl.SetPlayerMessageMethod;
import org.apollo.backend.method.impl.SetPlayerRightsMethod;
import org.apollo.backend.method.impl.SetPlayerTeleportMethod;
import org.apollo.backend.method.impl.SetServerConfigMethod;
import org.apollo.backend.method.impl.StartServerUpdateMethod;

/**
 * A class which holds the frontend methods.
 * @author Steve
 */
public final class Manager {

	/**
	 * The instance.
	 */
	private static final Manager instance = new Manager();

	/**
	 * Return the instance.
	 * @return {@link Manager} The instance.
	 */
	public static Manager getInstance() {
		return instance;
	}

	/**
	 * Map of our methods.
	 */
	private final Map<Method, FrontendHandler<? extends Method>> methods = new HashMap<Method, FrontendHandler<? extends Method>>();

	/**
	 * Register some events.
	 */
	private Manager() {
		register(new SetPlayerTeleportMethod(), new SetPlayerTeleportMethodHandler());
		register(new SetPlayerMessageMethod(), new SetPlayerMessageMethodHandler());
		register(new SetPlayerRightsMethod(), new SetPlayerRightsMethodHandler());
		register(new SetPlayerKickMethod(), new SetPlayerKickMethodHandler());
		register(new GivePlayerItemMethod(), new GivePlayerItemMethodHandler());
		register(new SetServerConfigMethod(), new SetServerConfigMethodHandler());
		register(new GetStatusMethod(), new GetStatusMethodHandler());
		register(new GetFrontendInformationMethod(), new GetFrontendInformationMethodHandler());
		register(new GetMessagesMethod(), new GetMessagesMethodHandler());
		register(new SendGlobalYellMethod(), new SendGlobalYellMethodHandler());
		register(new GetServerConfigMethod(), new GetServerConfigMethodHandler());
		register(new GetLatestUpdateMethod(), new GetLatestUpdateMethodHandler());
		register(new GetGroupsMethod(), new GetGroupsMethodHandler());
		register(new StartServerUpdateMethod(), new StartServerUpdateMethodHandler());
	}

	/**
	 * Check the method group for the method.
	 * @param method the method
	 * @return {@link Boolean}
	 */
	private boolean already(Method method) {
		return methods.containsValue(method);
	}

	/**
	 * Clear all methods.
	 */
	public void clear() {
		methods.clear();
	}

	/**
	 * Try to execute the method.
	 * @param method The method name.
	 * @return The method.
	 */
	@SuppressWarnings("unchecked")
	public <E extends Method> FrontendHandler<E> getHandler(E method) {
		return (FrontendHandler<E>) methods.get(method);
	}

	/**
	 * Try to execute the method.
	 * @param method The method name.
	 * @return The method.
	 */
	public Method getMethod(String method) {
		for (Method methodz : methods.keySet()) {
			if (methodz.getName().equals(method)) {
				return methodz;
			}
		}
		return null;
	}

	/**
	 * Gets the currently registered methods.
	 * @return The registed methods.
	 */
	public Set<Method> getMethods() {
		return methods.keySet();
	}

	/**
	 * Register the method.
	 * @param method The method class.
	 * @param handler The method handler.
	 */
	public <E extends Method> void register(E method, FrontendHandler<E> handler) {
		if (!already(method)) {
			methods.put(method, handler);
		}
	}
}
