package org.apollo.tools;

import java.io.File;
import java.util.Collection;

import org.apollo.fs.IndexedFileSystem;
import org.apollo.fs.parser.ObjectDefinitionParser;
import org.apollo.fs.parser.StaticObjectDefinitionParser;
import org.apollo.game.model.World;
import org.apollo.game.model.def.ObjectDefinition;
import org.apollo.game.model.obj.StaticObject;
import org.apollo.game.model.region.Region;

/**
 * A tool for updating the equipment bonuses.
 * @author Graham
 * @author Palidino76
 * @author Steve
 */
public final class ObjectInformation {

	/**
	 * The entry point of the application.
	 * @param args The command line arguments.
	 * @throws Exception if an error occurs.
	 */
	public static void main(String[] args) throws Exception {
		if (args.length != 2) {
			System.err.println("Usage:");
			System.err.println("  java -cp ... org.apollo.tools.ObjectInformation [release] [object]");
			return;
		}
		final String release = args[0];
		final int object = Integer.parseInt(args[1]);
		final IndexedFileSystem fs = new IndexedFileSystem(new File("data/fs/" + release), true);
		try {
			final ObjectDefinitionParser parser = new ObjectDefinitionParser(fs);
			final ObjectDefinition[] defs = parser.parse();
			final StaticObjectDefinitionParser _parser = new StaticObjectDefinitionParser(fs);
			_parser.parse();
			ObjectDefinition.init(defs);
			ObjectDefinition def = ObjectDefinition.forId(object);
			Collection<Region> collection = World.getWorld().getRegionManager().getRegions();
			System.out.println("Name: " + def.getName());
			System.out.println("Description: " + def.getDescription());
			System.out.println("Offset: " + def.getOffset());
			System.out.println("Scale: " + def.getScale());
			System.out.println("Actions: ");
			for (String action : def.getActions()) {
				if (action != null) {
					System.out.println(">       " + action);
				}
			}
			System.out.println("");
			if (collection != null) {
				for (Region region : collection) {
					for (StaticObject so : region.getStaticObjects()) {
						if (so.getId() == object) {
							System.out.println(so.getPosition());
						}
					}
				}
			}
		}
		finally {
			fs.close();
		}
	}

	/**
	 * Default private constructor to prevent instantiation.
	 */
	private ObjectInformation() {
	}
}
