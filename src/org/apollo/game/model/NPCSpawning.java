package org.apollo.game.model;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apollo.game.model.region.Region;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * A utility that loads npcs positions.
 * @author Solid Snake
 */
public final class NPCSpawning {

	/**
	 * The factory.
	 */
	private static DocumentBuilderFactory factory;

	/**
	 * The {@link NPC} spawns.
	 */
	private static NodeList spawnsList;

	/**
	 * The {@link NPC} length.
	 */
	public static int NPCslength;

	/**
	 * The factory builder.
	 */
	private static DocumentBuilder builder;

	/**
	 * The {@link NPC} spawn file.
	 */
	private static Document doc;

	/**
	 * Define the npcs.
	 * @throws Exception If a IO exception occurs.
	 */
	private static void define() throws Exception {
		factory = DocumentBuilderFactory.newInstance();
		builder = factory.newDocumentBuilder();
		doc = builder.parse(new File("data/npc-spawns.xml"));
		spawnsList = doc.getElementsByTagName("SPAWN");
		NPCslength = spawnsList.getLength();
	}

	/**
	 * Initialize the spawning of npcs by definning and registering the npcs to the {@link World}
	 * @throws Exception If a IO exception occurs.
	 */
	public static void init() throws Exception {
		define();
		doc.getDocumentElement().normalize();
		for (int i = 0; i < spawnsList.getLength(); i++) {
			Element spawnElement = (Element) spawnsList.item(i);
			int ID = Integer.parseInt(spawnElement.getAttribute("NPCID"));
			Element posElement = (Element) spawnElement.getElementsByTagName("POSITION").item(0);
			int X = Integer.parseInt(posElement.getAttribute("X"));
			int Y = Integer.parseInt(posElement.getAttribute("Y"));
			int Z = Integer.parseInt(posElement.getAttribute("Z"));
			NPCDefinition nD = new NPCDefinition(ID);
			NPC n = new NPC(nD, new Position(X, Y, Z));
			Region region = World.getWorld().getRegionManager().getRegionByLocation(n.getPosition());
			region.addNPC(n);
			World.getWorld().register(n);
		}
	}
}
