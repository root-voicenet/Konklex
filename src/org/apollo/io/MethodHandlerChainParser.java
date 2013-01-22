package org.apollo.io;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apollo.api.method.Method;
import org.apollo.api.method.handler.MethodHandler;
import org.apollo.api.method.handler.chain.MethodHandlerChain;
import org.apollo.api.method.handler.chain.MethodHandlerChainGroup;
import org.apollo.game.event.handler.chain.EventHandlerChain;
import org.apollo.game.event.handler.chain.EventHandlerChainGroup;
import org.apollo.util.xml.XmlNode;
import org.apollo.util.xml.XmlParser;
import org.xml.sax.SAXException;

/**
 * A class which parses the {@code methods.xml} file to produce {@link MethodHandlerChainGroup}s.
 * @author Steve
 */
public final class MethodHandlerChainParser {

	/**
	 * The {@link XmlParser} instance.
	 */
	private final XmlParser parser;

	/**
	 * The source {@link InputStream}.
	 */
	private final InputStream is;

	/**
	 * Creates the event chain parser.
	 * @param is The source {@link InputStream}.
	 * @throws SAXException if a SAX error occurs.
	 */
	public MethodHandlerChainParser(InputStream is) throws SAXException {
		this.parser = new XmlParser();
		this.is = is;
	}

	/**
	 * Parses the XML and produces a group of {@link EventHandlerChain}s.
	 * @return An {@link EventHandlerChainGroup}.
	 * @throws IOException if an I/O error occurs.
	 * @throws SAXException if a SAX error occurs.
	 * @throws ClassNotFoundException if a class was not found.
	 * @throws InstantiationException if a class could not be instantiated.
	 * @throws IllegalAccessException if a class was accessed illegally.
	 */
	@SuppressWarnings("unchecked")
	public MethodHandlerChainGroup parse() throws IOException, SAXException, ClassNotFoundException,
			InstantiationException, IllegalAccessException {
		final XmlNode rootNode = parser.parse(is);
		if (!rootNode.getName().equals("methods"))
			throw new IOException("root node name is not 'methods'");
		final Map<Class<? extends Method>, MethodHandlerChain<?>> chains = new HashMap<Class<? extends Method>, MethodHandlerChain<?>>();
		for (final XmlNode eventNode : rootNode) {
			if (!eventNode.getName().equals("method"))
				throw new IOException("only expected nodes named 'event' beneath the root node");
			final XmlNode typeNode = eventNode.getChild("type");
			if (typeNode == null)
				throw new IOException("no node named 'type' beneath current event node");
			final XmlNode chainNode = eventNode.getChild("chain");
			if (chainNode == null)
				throw new IOException("no node named 'chain' beneath current event node");
			final String eventClassName = typeNode.getValue();
			if (eventClassName == null)
				throw new IOException("type node must have a value");
			final Class<? extends Method> eventClass = (Class<? extends Method>) Class.forName(eventClassName);
			final List<MethodHandler<?>> handlers = new ArrayList<MethodHandler<?>>();
			for (final XmlNode handlerNode : chainNode) {
				if (!handlerNode.getName().equals("handler"))
					throw new IOException("only expected nodes named 'handler' beneath the root node");
				final String handlerClassName = handlerNode.getValue();
				if (handlerClassName == null)
					throw new IOException("handler node must have a value");
				final Class<? extends MethodHandler<?>> handlerClass = (Class<? extends MethodHandler<?>>) Class
						.forName(handlerClassName);
				final MethodHandler<?> handler = handlerClass.newInstance();
				handlers.add(handler);
			}
			final MethodHandler<?>[] handlersArray = handlers.toArray(new MethodHandler<?>[handlers.size()]);
			@SuppressWarnings("rawtypes")
			final MethodHandlerChain chain = new MethodHandlerChain(handlersArray);
			chains.put(eventClass, chain);
		}
		return new MethodHandlerChainGroup(chains);
	}
}
