package org.apollo.io;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apollo.game.event.handler.chain.EventHandlerChainGroup;
import org.apollo.game.model.Npc;
import org.apollo.game.model.Position;
import org.apollo.game.model.def.NpcDefinition;
import org.apollo.util.xml.XmlNode;
import org.apollo.util.xml.XmlParser;
import org.xml.sax.SAXException;

/**
 * A class which parses the {@code npc-spawns.xml} file to populate the world
 * with {@link Npc}s.
 * @author Chris Fletcher
 */
public final class NpcSpawnParser {

    /**
     * The {@link XmlParser} instance.
     */
    private final XmlParser parser;

    /**
     * The source {@link InputStream}.
     */
    private final InputStream is;

    /**
     * Creates the NPC spawn parser.
     * @param is The source {@link InputStream}.
     * @throws SAXException if a SAX error occurs.
     */
    public NpcSpawnParser(InputStream is) throws SAXException {
	this.parser = new XmlParser();
	this.is = is;
    }

    /**
     * Parses the XML and produces an array of {@link Npc}s which are to be
     * registered to the world..
     * @return An {@link EventHandlerChainGroup}.
     * @throws IOException if an I/O error occurs.
     * @throws SAXException if a SAX error occurs.
     */
    public Npc[] parse() throws IOException, SAXException {
	final XmlNode rootNode = parser.parse(is);
	if (!rootNode.getName().equals("spawn"))
	    throw new IOException("root node name is not 'spawn'");

	final List<Npc> npcs = new ArrayList<Npc>();
	final int maxId = NpcDefinition.count() - 1;

	for (final XmlNode npcNode : rootNode) {
	    if (!npcNode.getName().equals("npc"))
		throw new IOException("only expected nodes named 'npc' beneath the root node");
	    if (!npcNode.containsAttribute("id"))
		throw new IOException("no attribute named 'id' specified at current NPC node");

	    final int id = Integer.parseInt(npcNode.getAttribute("id"));
	    if (id < 0)
		throw new IOException("negative 'id' attribute value at current NPC node");
	    // We simply ignore any ids above the maximum for the current
	    // release.
	    if (id > maxId)
		continue;

	    final XmlNode posNode = npcNode.getChild("position");
	    if (posNode == null)
		throw new IOException("no node named 'position' beneath current NPC node");
	    final XmlNode nodeX = posNode.getChild("x");
	    if (nodeX == null)
		throw new IOException("no node named 'x' beneath current NPC position node");
	    final XmlNode nodeY = posNode.getChild("y");
	    if (nodeY == null)
		throw new IOException("no node named 'y' beneath current NPC position node");

	    final int x = Integer.parseInt(nodeX.getValue());
	    if (x < 0)
		throw new IOException("negative 'x' node value beneath current NPC position node");
	    final int y = Integer.parseInt(nodeY.getValue());
	    if (y < 0)
		throw new IOException("negative 'y' node value beneath current NPC position node");

	    Position pos;

	    final XmlNode nodeZ = posNode.getChild("height");
	    if (nodeZ != null) {
		final int height = Integer.parseInt(nodeZ.getValue());
		pos = new Position(x, y, height);
	    } else
		pos = new Position(x, y);

	    final Npc npc = new Npc(id, pos);
	    npcs.add(npc);
	}

	return npcs.toArray(new Npc[npcs.size()]);
    }

}
