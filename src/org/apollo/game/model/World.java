package org.apollo.game.model;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import org.apollo.ServerContext;
import org.apollo.Service;
import org.apollo.api.FrontendService;
import org.apollo.api.method.impl.SendPlayerMethod;
import org.apollo.fs.IndexedFileSystem;
import org.apollo.fs.parser.ItemDefinitionParser;
import org.apollo.fs.parser.NpcDefinitionParser;
import org.apollo.fs.parser.ObjectDefinitionParser;
import org.apollo.game.command.CommandDispatcher;
import org.apollo.game.minigame.Minigame;
import org.apollo.game.minigame.MinigameService;
import org.apollo.game.model.def.EquipmentDefinition;
import org.apollo.game.model.def.ItemDefinition;
import org.apollo.game.model.def.NpcDefinition;
import org.apollo.game.model.def.ObjectDefinition;
import org.apollo.game.model.inter.store.WorldStore;
import org.apollo.game.model.messaging.WorldMessaging;
import org.apollo.game.model.region.RegionManager;
import org.apollo.game.scheduling.ScheduledTask;
import org.apollo.game.scheduling.Scheduler;
import org.apollo.game.scheduling.impl.SystemUpdateTask;
import org.apollo.game.scheduling.impl.UptimeTask;
import org.apollo.io.EquipmentDefinitionParser;
import org.apollo.io.NpcSpawnParser;
import org.apollo.util.CharacterRepository;
import org.apollo.util.plugin.PluginManager;

/**
 * The world class is a singleton which contains objects like the
 * {@link CharacterRepository} for players and NPCs. It should only contain
 * things relevant to the in-game world and not classes which deal with I/O and
 * such (these may be better off inside some custom {@link Service} or other
 * code, however, the circumstances are rare).
 * @author Graham
 */
public final class World {

	/**
	 * Represents the different status codes for registering a player.
	 * @author Graham
	 */
	public enum RegistrationStatus {
		/**
		 * Indicates the world is full.
		 */
		WORLD_FULL,
		/**
		 * Indicates the world is offline.
		 */
		WORLD_OFFLINE,
		/**
		 * Indicates the world is being updated.
		 */
		WORLD_UPDATING,
		/**
		 * Indicates that the player is already online.
		 */
		ALREADY_ONLINE,
		/**
		 * Indicates that the player was registered successfully.
		 */
		OK;
	}

	/**
	 * The logger for this class.
	 */
	private static final Logger logger = Logger.getLogger(World.class.getName());

	/**
	 * The world.
	 */
	private static final World world = new World();

	/**
	 * The messaging.
	 */
	private static final WorldMessaging worldMessaging = new WorldMessaging();

	/**
	 * The world shops.
	 */
	private static final WorldStore worldStore = new WorldStore();

	/**
	 * Gets the world.
	 * @return The world.
	 */
	public static World getWorld() {
		return world;
	}

	/**
	 * The scheduler.
	 */
	private final Scheduler scheduler = new Scheduler();

	/**
	 * The command dispatcher.
	 */
	private final CommandDispatcher dispatcher = new CommandDispatcher();

	/**
	 * The plugin manager.
	 */
	private PluginManager pluginManager;

	/**
	 * The {@link CharacterRepository} of {@link Player}s.
	 */
	private final CharacterRepository<Player> playerRepository = new CharacterRepository<Player>(
			WorldConstants.MAXIMUM_PLAYERS);

	/**
	 * The {@link CharacterRepository} of {@link Npc}s.
	 */
	private final CharacterRepository<Npc> npcRepository = new CharacterRepository<Npc>(WorldConstants.MAXIMUM_NPCS);

	/**
	 * The list of ground items.
	 */
	private final List<GroundItem> items = new ArrayList<GroundItem>();

	/**
	 * The list of game objects.
	 */
	private final List<GameObject> objects = new ArrayList<GameObject>();

	/**
	 * The region manager.
	 */
	private final RegionManager regionManager = new RegionManager();

	/**
	 * The server context.
	 */
	private ServerContext context;

	/**
	 * The uptime.
	 */
	private int uptime;
	
	/**
	 * The world id.
	 */
	private int id;

	/**
	 * Creates the world.
	 */
	private World() {
		schedule(new UptimeTask());
	}
	
	/**
	 * Gets the id.
	 * @return The id.
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Sets the id.
	 * @param id The id.
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Gets the command dispatcher. TODO should this be here?
	 * @return The command dispatcher.
	 */
	public CommandDispatcher getCommandDispatcher() {
		return dispatcher;
	}

	/**
	 * Gets the server context.
	 * @return The server context.
	 */
	public ServerContext getContext() {
		return context;
	}

	/**
	 * Gets the items.
	 * @return The items.
	 */
	public Collection<GroundItem> getItems() {
		synchronized (this) {
			return Collections.unmodifiableCollection(new LinkedList<GroundItem>(items));
		}
	}

	/**
	 * Gets the world messaging.
	 * @return The world messaging.
	 */
	public WorldMessaging getMessaging() {
		return worldMessaging;
	}

	/**
	 * Gets the NPC repository.
	 * @return The NPC repository.
	 */
	public CharacterRepository<Npc> getNpcRepository() {
		return npcRepository;
	}

	/**
	 * Gets the object at the position.
	 * @param position The position.
	 * @return The game object, if any, at the position.
	 */
	private GameObject getObject(Position position) {
		GameObject returnz = null;
		for (final GameObject object : objects)
			if (object.getLocation().equals(position))
				returnz = object;
		return returnz;
	}

	/**
	 * Gets the objects.
	 * @return The objects.
	 */
	public Collection<GameObject> getObjects() {
		synchronized (this) {
			return Collections.unmodifiableCollection(new LinkedList<GameObject>(objects));
		}
	}

	/**
	 * Gets the specified player.
	 * @param name The player's name.
	 * @return player The player.
	 */
	public Player getPlayer(String name) {
		for (final Player player : getPlayerRepository())
			if (player.getName().equalsIgnoreCase(name))
				return player;
		return null;
	}

	/**
	 * Gets the character repository. NOTE:
	 * {@link CharacterRepository#add(Character)} and
	 * {@link CharacterRepository#remove(Character)} should not be called
	 * directly! These mutation methods are not guaranteed to work in future
	 * releases!
	 * <p>
	 * Instead, use the {@link World#register(Player)} and
	 * {@link World#unregister(Player)} methods which do the same thing and will
	 * continue to work as normal in future releases.
	 * @return The character repository.
	 */
	public CharacterRepository<Player> getPlayerRepository() {
		return playerRepository;
	}

	/**
	 * Gets the plugin manager. TODO should this be here?
	 * @return The plugin manager.
	 */
	public PluginManager getPluginManager() {
		return pluginManager;
	}

	/**
	 * Gets the region manager.
	 * @return The region manager.
	 */
	public RegionManager getRegionManager() {
		return regionManager;
	}

	/**
	 * Gets the world stores.
	 * @return The world stores.
	 */
	public WorldStore getStores() {
		return worldStore;
	}

	/**
	 * Initialises the world by loading definitions from the specified file
	 * system.
	 * @param release The release number.
	 * @param fs The file system.
	 * @param mgr The plugin manager. TODO move this.
	 * @param context The server context.
	 * @throws IOException if an I/O error occurs.
	 */
	public void init(int release, IndexedFileSystem fs, PluginManager mgr, ServerContext context) throws IOException {
		logger.info("Loading item definitions...");
		final ItemDefinitionParser itemParser = new ItemDefinitionParser(fs);
		final ItemDefinition[] itemDefs = itemParser.parse();
		ItemDefinition.init(itemDefs);
		logger.info("Done (loaded " + itemDefs.length + " item definitions).");

		logger.info("Loading equipment definitions...");
		int nonNull = 0;
		InputStream is = new BufferedInputStream(new FileInputStream("data/equipment-" + release + ".dat"));
		try {
			final EquipmentDefinitionParser equipParser = new EquipmentDefinitionParser(is);
			final EquipmentDefinition[] equipDefs = equipParser.parse();
			for (final EquipmentDefinition def : equipDefs)
				if (def != null)
					nonNull++;
			EquipmentDefinition.init(equipDefs);
		} finally {
			is.close();
		}
		logger.info("Done (loaded " + nonNull + " equipment definitions).");

		logger.info("Loading object definitions...");
		final ObjectDefinitionParser objectParser = new ObjectDefinitionParser(fs);
		final ObjectDefinition[] objectDefs = objectParser.parse();
		ObjectDefinition.init(objectDefs);
		logger.info("Done (loaded " + objectDefs.length + " object definitions).");

		logger.info("Loading NPC definitions...");
		final NpcDefinitionParser npcDefParser = new NpcDefinitionParser(fs);
		final NpcDefinition[] npcDefs = npcDefParser.parse();
		NpcDefinition.init(npcDefs);
		logger.info("Done (loaded " + npcDefs.length + " NPC definitions).");

		logger.info("Loading NPC spawns...");
		nonNull = 0;
		is = new FileInputStream("data/npc-spawns.xml");
		final NpcSpawnParser npcParser = new NpcSpawnParser(is);
		final Npc[] npcSpawns = npcParser.parse();
		for (final Npc npc : npcSpawns)
			if (npc != null) { // Shouldn't have to, but just in case.
				nonNull++;
				register(npc);
			}
		logger.info("Done (loaded " + nonNull + " NPC spawns).");

		this.pluginManager = mgr;
		this.context = context;
	}

	/**
	 * Checks if the specified player is online.
	 * @param name The player's name.
	 * @return {@code true} if so, {@code false} if not.
	 */
	public boolean isPlayerOnline(String name) {
		for (final Player player : playerRepository)
			if (player.getName().equalsIgnoreCase(name))
				return true;
		return false;
	}

	/**
	 * Calls the {@link Scheduler#pulse()} method.
	 */
	public void pulse() {
		scheduler.pulse();
	}

	/**
	 * Registers an game object.
	 * @param object The game object.
	 */
	public void register(final GameObject object) {
		synchronized (this) {
			if (objects.add(object))
				World.getWorld().getRegionManager().getRegionByLocation(object.getLocation()).addObject(object);
		}
	}

	/**
	 * Registers an ground item.
	 * @param item The ground item.
	 */
	public void register(final GroundItem item) {
		synchronized (this) {
			if (items.add(item))
				World.getWorld().getRegionManager().getRegionByLocation(item.getPosition()).addItem(item);
		}
	}

	/**
	 * Registers a minigame.
	 * @param minigame The {@link Minigame}.
	 */
	public void register(Minigame minigame) {
		context.getService(MinigameService.class).addMinigame(minigame);
	}

	/**
	 * Registers an NPC.
	 * @param npc The NPC.
	 */
	public void register(final Npc npc) {
		if (npcRepository.add(npc)) {
			regionManager.getRegionByLocation(npc.getPosition()).addNpc(npc);
			logger.info("Registered npc: " + npc + " [online=" + npcRepository.size() + "]");
		} else
			logger.info("Failed to register npc (server full): " + npc + " [online=" + npcRepository.size() + "]");
	}

	/**
	 * Registers the specified player.
	 * @param player The player.
	 * @return A {@link RegistrationStatus}.
	 */
	public RegistrationStatus register(final Player player) {
		if (isPlayerOnline(player.getName()))
			return RegistrationStatus.ALREADY_ONLINE;
		if (SystemUpdateTask.isUpdating()) {
			logger.warning("Failed to register player (server updating): " + player + " [online="
					+ playerRepository.size() + "]");
			return RegistrationStatus.WORLD_UPDATING;
		} else if (Config.SERVER_WHITELIST) {
			logger.warning("Failed to register player (server offline): " + player + " [online="
					+ playerRepository.size() + "]");
			return RegistrationStatus.WORLD_OFFLINE;
		} else {
			final boolean success = playerRepository.add(player);
			if (success) {
				context.getService(FrontendService.class).sendAll(new SendPlayerMethod(player.getEncodedName(), true));
				logger.info("Registered player: " + player + " [online=" + playerRepository.size() + "]");
				return RegistrationStatus.OK;
			} else {
				logger.warning("Failed to register player (server full): " + player + " [online="
						+ playerRepository.size() + "]");
				return RegistrationStatus.WORLD_FULL;
			}
		}
	}

	/**
	 * Replaces an object at the position.
	 * @param position The position of the old object.
	 * @param object The object to replace with.
	 */
	public void replaceObject(Position position, GameObject object) {
		final GameObject replace = getObject(position);
		synchronized (this) {
			objects.remove(replace);
			regionManager.getRegionByLocation(object.getLocation()).replaceObject(position, object);
		}
	}

	/**
	 * Schedules a new task.
	 * @param task The {@link ScheduledTask}.
	 */
	public void schedule(ScheduledTask task) {
		scheduler.schedule(task);
	}

	/**
	 * Unregisters an game object.
	 * @param object The game object.
	 */
	public void unregister(GameObject object) {
		synchronized (this) {
			objects.remove(object);
			regionManager.getRegionByLocation(object.getLocation()).removeObject(object);
		}
	}

	/**
	 * Unregisters an ground item.
	 * @param item The ground item.
	 */
	public void unregister(GroundItem item) {
		synchronized (this) {
			if (items.remove(item))
				regionManager.getRegionByLocation(item.getPosition()).removeItem(item);
		}
	}

	/**
	 * Unregisters the specified npc.
	 * @param npc The npc.
	 */
	public void unregister(Npc npc) {
		if (npcRepository.remove(npc)) {
			regionManager.getRegionByLocation(npc.getPosition()).removeNpc(npc);
			logger.info("Unregistered npc: " + npc + " [online=" + npcRepository.size() + "]");
		} else
			logger.warning("Could not find npc to unregister: " + npc + "!");
	}

	/**
	 * Unregisters the specified player.
	 * @param player The player.
	 */
	public void unregister(Player player) {
		if (playerRepository.remove(player)) {
			context.getService(FrontendService.class).sendAll(new SendPlayerMethod(player.getEncodedName(), false));
			regionManager.getRegionByLocation(player.getPosition()).removePlayer(player);
			logger.info("Unregistered player: " + player + " [online=" + playerRepository.size() + "]");
		} else
			logger.warning("Could not find player to unregister: " + player + "!");
	}

	/**
	 * Sets the uptime.
	 * @param uptime The uptime to set.
	 */
	public void setUptime(int uptime) {
		this.uptime = uptime;
	}

	/**
	 * Gets the uptime.
	 * @return The uptime.
	 */
	public long getUptime() {
		return (long) uptime;
	}
}
