package org.apollo.game.model;

import java.util.ArrayDeque;
import java.util.Queue;

import org.apollo.game.action.impl.TeleportAction;
import org.apollo.game.event.impl.BuildPlayerMenuEvent;
import org.apollo.game.event.impl.ChatPrivacySettingsEvent;
import org.apollo.game.event.impl.IdAssignmentEvent;
import org.apollo.game.event.impl.LogoutEvent;
import org.apollo.game.event.impl.SoundEvent;
import org.apollo.game.event.impl.SwitchTabInterfaceEvent;
import org.apollo.game.event.impl.UpdateRunEnergyEvent;
import org.apollo.game.minigame.MinigameService;
import org.apollo.game.model.Inventory.StackMode;
import org.apollo.game.model.inter.bank.BankConstants;
import org.apollo.game.model.inter.store.Shop;
import org.apollo.game.model.inter.trade.TradeSession;
import org.apollo.game.model.inv.AppearanceInventoryListener;
import org.apollo.game.model.inv.FullInventoryListener;
import org.apollo.game.model.inv.InventoryListener;
import org.apollo.game.model.inv.SynchronizationInventoryListener;
import org.apollo.game.model.messaging.PlayerMessaging;
import org.apollo.game.model.region.Region;
import org.apollo.game.model.skill.HitpointSkillListener;
import org.apollo.game.model.skill.LevelUpSkillListener;
import org.apollo.game.model.skill.PrayerSkillListener;
import org.apollo.game.model.skill.SkillListener;
import org.apollo.game.model.skill.SynchronizationSkillListener;
import org.apollo.game.scheduling.impl.NormalizeEnergyTask;
import org.apollo.game.scheduling.impl.UpdateSpecialTask;
import org.apollo.game.sync.block.SynchronizationBlock;
import org.apollo.net.session.GameSession;
import org.apollo.security.PlayerCredentials;

/**
 * A {@link Player} is a {@link Character} that a user is controlling.
 * @author Graham
 */
public final class Player extends Character {

    /**
     * An enumeration with the different privilege levels a player can have.
     * @author Graham
     */
    public enum PrivilegeLevel {
	/**
	 * A standard (rights 0) account.
	 */
	STANDARD(0),
	/**
	 * A player member (rights 1) account.
	 */
	MEMBER(1),
	/**
	 * A player moderator (rights 2) account.
	 */
	MODERATOR(2),
	/**
	 * A player administrator (rights 3) account.
	 */
	ADMINISTRATOR(3),
	/**
	 * A player developer (rights 4) account.
	 */
	DEVELOPER(4),
	/**
	 * A owner (rights 5) account.
	 */
	OWNER(5);

	/**
	 * Gets the privilege level for the specified numerical level.
	 * @param numericalLevel The numerical level.
	 * @return The privilege level.
	 */
	public static PrivilegeLevel valueOf(int numericalLevel) {
	    for (final PrivilegeLevel level : values()) {
		if (level.numericalLevel == numericalLevel) {
		    return level;
		}
	    }
	    throw new IllegalArgumentException("invalid numerical level");
	}

	/**
	 * The numerical level used in the protocol.
	 */
	private final int numericalLevel;

	/**
	 * Creates a privilege level.
	 * @param numericalLevel The numerical level.
	 */
	private PrivilegeLevel(int numericalLevel) {
	    this.numericalLevel = numericalLevel;
	}

	/**
	 * Gets the numerical level.
	 * @return The numerical level used in the protocol.
	 */
	public int toInteger() {
	    return numericalLevel;
	}
    }

    /**
     * A temporary queue of events sent during the login process.
     */
    private final Queue<org.apollo.game.event.Event> queuedEvents = new ArrayDeque<org.apollo.game.event.Event>();

    /**
     * The player's credentials.
     */
    private final PlayerCredentials credentials;

    /**
     * The player's messaging.
     */
    private final PlayerMessaging messaging = new PlayerMessaging(this);

    /**
     * The privilege level.
     */
    private PrivilegeLevel privilegeLevel = PrivilegeLevel.STANDARD;

    /**
     * The membership flag.
     */
    private boolean members = false;

    /**
     * A flag indicating if the player has designed their character.
     */
    private boolean designedCharacter = false;

    /**
     * The {@link GameSession} currently attached to this {@link Player}.
     */
    private GameSession session;

    /**
     * The center of the last region the client has loaded.
     */
    private Position lastKnownRegion;

    /**
     * A flag indicating if the region changed in the last cycle.
     */
    private boolean regionChanged = false;

    /**
     * The player's appearance.
     */
    private Appearance appearance = Appearance.DEFAULT_APPEARANCE;

    /**
     * The current maximum viewing distance of this player.
     */
    private int viewingDistance = 1;

    /**
     * A flag which indicates there are players that couldn't be added.
     */
    private boolean excessivePlayers = false;

    /**
     * This player's interface set.
     */
    private final InterfaceSet interfaceSet = new InterfaceSet(this);

    /**
     * The player's deposit box.
     */
    private final Inventory depositBox = new Inventory(InventoryConstants.INVENTORY_CAPACITY,
	    StackMode.STACK_STACKABLE_ITEMS);

    /**
     * The player's trade request.
     */
    private Request request;

    /**
     * The player's trade session.
     */
    private TradeSession tradeSession;

    /**
     * A flag indicating if the player is withdrawing items as notes.
     */
    private boolean withdrawingNotes = false; // TODO find a better place!

    /**
     * Auto retaliate flag.
     */
    private boolean retaliate = false;

    /**
     * The current playing sound.
     */
    private int currentSound = -1;

    /**
     * The public chat setting.
     */
    private int publicChat = 0;

    /**
     * The private chat value.
     */
    private int privateChat = 0;

    /**
     * The trade chat value.
     */
    private int trade = 0;

    /**
     * The shop that is currently open.
     */
    private Shop shop;

    /**
     * The dialogue id that is currently open.
     */
    private int dialogueId;

    /**
     * Creates the {@link Player}.
     * @param credentials The player's credentials.
     * @param position The initial position.
     */
    public Player(PlayerCredentials credentials, Position position) {
	super(position);
	init();
	this.credentials = credentials;
    }

    /**
     * Decrements this player's viewing distance if it is greater than 1.
     */
    public void decrementViewingDistance() {
	if (viewingDistance > 1) {
	    viewingDistance--;
	}
    }

    /**
     * Sends the exit player events.
     */
    @SuppressWarnings("deprecation")
    public void exitInitialEvents() {
	interfaceSet.interfaceClosed();
	World.getWorld().getMessaging().deregister(this);
	World.getWorld().getContext().getService(MinigameService.class).playerDisconnected(this);
    }

    /**
     * Sets the excessive players flag.
     */
    public void flagExcessivePlayers() {
	excessivePlayers = true;
    }

    /**
     * Gets the player's appearance.
     * @return The appearance.
     */
    public Appearance getAppearance() {
	return appearance;
    }

    /**
     * Gets the player's credentials.
     * @return The player's credentials.
     */
    public PlayerCredentials getCredentials() {
	return credentials;
    }

    /**
     * Gets the player's deposit box.
     * @return The player's deposit box.
     */
    public Inventory getDepositBox() {
	return depositBox;
    }

    /**
     * Gets the currently opened dialogue id.
     * @return The currently opened dialogue id
     */
    public int getDialogueId() {
	return dialogueId;
    }

    /**
     * Gets the player's name, encoded as a long.
     * @return The encoded player name.
     */
    public long getEncodedName() {
	return credentials.getEncodedUsername();
    }

    /**
     * Gets this player's interface set.
     * @return The interface set for this player.
     */
    public InterfaceSet getInterfaceSet() {
	return interfaceSet;
    }

    /**
     * Gets the last known region.
     * @return The last known region, or {@code null} if the player has never
     * known a region.
     */
    public Position getLastKnownRegion() {
	return lastKnownRegion;
    }

    /**
     * Gets the player's friends.
     * @return The player's friends.
     */
    public PlayerMessaging getMessaging() {
	return messaging;
    }

    /**
     * Gets the player's name.
     * @return The player's name.
     */
    public String getName() {
	return credentials.getUsername();
    }

    /**
     * Gets the private chat value.
     * @return The private chat value.
     */
    public int getPrivateChat() {
	return privateChat;
    }

    /**
     * Gets the privilege level.
     * @return The privilege level.
     */
    public PrivilegeLevel getPrivilegeLevel() {
	return privilegeLevel;
    }

    /**
     * Gets the public chat value.
     * @return The public chat value.
     */
    public int getPublicChat() {
	return publicChat;
    }

    /**
     * Gets the player's trade request.
     * @return The player's trade request.
     */
    public Request getRequest() {
	return request;
    }

    /**
     * Check the retaliate flag.
     * @return {@link Boolean}
     */
    public boolean getRetaliate() {
	return retaliate;
    }

    /**
     * Gets the game session.
     * @return The game session.
     */
    public GameSession getSession() {
	return session;
    }

    /**
     * Gets the shop.
     * @return the shop.
     */
    public Shop getShop() {
	return shop;
    }

    /**
     * Gets the trade value.
     * @return The trade value.
     */
    public int getTrade() {
	return trade;
    }

    /**
     * Gets the current trade session.
     * @return The trade session.
     */
    public TradeSession getTradeSession() {
	return tradeSession;
    }

    /**
     * Gets this player's viewing distance.
     * @return The viewing distance.
     */
    public int getViewingDistance() {
	return viewingDistance;
    }

    /**
     * Checks if the player has designed their character.
     * @return A flag indicating if the player has designed their character.
     */
    public boolean hasDesignedCharacter() {
	return designedCharacter;
    }

    /**
     * Checks if this player has ever known a region.
     * @return {@code true} if so, {@code false} if not.
     */
    public boolean hasLastKnownRegion() {
	return lastKnownRegion != null;
    }

    /**
     * Checks if the region has changed.
     * @return {@code true} if so, {@code false} if not.
     */
    public boolean hasRegionChanged() {
	return regionChanged;
    }

    /**
     * Increments this player's viewing distance if it is less than the maximum
     * viewing distance.
     */
    public void incrementViewingDistance() {
	if (viewingDistance < Position.MAX_DISTANCE) {
	    viewingDistance++;
	}
    }

    /**
     * Initialises this player.
     */
    private void init() {
	initInventories();
	initSkills();
	initTasks();
    }

    /**
     * Initialises the player's inventories.
     */
    private void initInventories() {
	final Inventory inventory = getInventory();
	final Inventory bank = getBank();
	final Inventory equipment = getEquipment();
	// TODO only add bank listener when it is open? (like Hyperion)
	// inventory full listeners
	final InventoryListener fullInventoryListener = new FullInventoryListener(this,
		FullInventoryListener.FULL_INVENTORY_MESSAGE);
	final InventoryListener fullBankListener = new FullInventoryListener(this,
		FullInventoryListener.FULL_BANK_MESSAGE);
	final InventoryListener fullEquipmentListener = new FullInventoryListener(this,
		FullInventoryListener.FULL_EQUIPMENT_MESSAGE);
	// equipment appearance listener
	final InventoryListener appearanceListener = new AppearanceInventoryListener(this);
	// synchronization listeners
	final InventoryListener syncInventoryListener = new SynchronizationInventoryListener(this,
		SynchronizationInventoryListener.INVENTORY_ID);
	final InventoryListener syncBankListener = new SynchronizationInventoryListener(this,
		BankConstants.BANK_INVENTORY_ID);
	final InventoryListener syncEquipmentListener = new SynchronizationInventoryListener(this,
		SynchronizationInventoryListener.EQUIPMENT_ID);
	// add the listeners
	inventory.addListener(syncInventoryListener);
	inventory.addListener(fullInventoryListener);
	bank.addListener(syncBankListener);
	bank.addListener(fullBankListener);
	equipment.addListener(syncEquipmentListener);
	equipment.addListener(appearanceListener);
	equipment.addListener(fullEquipmentListener);
    }

    /**
     * Initialises the player's skills.
     */
    private void initSkills() {
	final SkillSet skills = getSkillSet();
	// synchronization listener
	final SkillListener syncListener = new SynchronizationSkillListener(this);
	// level up listener
	final SkillListener levelUpListener = new LevelUpSkillListener(this);
	// prayer listener
	final SkillListener prayerListener = new PrayerSkillListener(this);
	// hitpoints listener
	final SkillListener hitpointListener = new HitpointSkillListener(this);
	// add the listeners
	skills.addListener(syncListener);
	skills.addListener(levelUpListener);
	skills.addListener(prayerListener);
	skills.addListener(hitpointListener);
    }

    /**
     * Starts player tasks.
     */
    private void initTasks() {
	World.getWorld().schedule(new UpdateSpecialTask(this));
	// World.getWorld().schedule(new UpdateObjectsTask(this));
	World.getWorld().schedule(new NormalizeEnergyTask(this));
    }

    /**
     * Checks if there are excessive players.
     * @return {@code true} if so, {@code false} if not.
     */
    public boolean isExcessivePlayersSet() {
	return excessivePlayers;
    }

    /**
     * Checks if this player account has membership.
     * @return {@code true} if so, {@code false} if not.
     */
    public boolean isMembers() {
	return members;
    }

    /**
     * Gets the withdrawing notes flag.
     * @return The flag.
     */
    public boolean isWithdrawingNotes() {
	return withdrawingNotes;
    }

    /**
     * Logs the player out, if possible.
     */
    public void logout() {
	send(new LogoutEvent());
    }

    /**
     * Play a new sound.
     * @param id The sound id.
     * @param temp Is the sound temporary.
     */
    public void playSound(int id, boolean temp) {
	if (temp && currentSound != -1) {
	    send(new SoundEvent(id, currentSound));
	} else {
	    send(new SoundEvent(id));
	}
	currentSound = id;
    }

    /**
     * Resets the excessive players flag.
     */
    public void resetExcessivePlayers() {
	excessivePlayers = false;
    }

    /**
     * Resets this player's viewing distance.
     */
    public void resetViewingDistance() {
	viewingDistance = 1;
    }

    /*
     * (non-Javadoc)
     * @see org.apollo.game.model.Character#send(org.apollo.game.event.Event)
     */
    @Override
    public void send(org.apollo.game.event.Event event) {
	if (isActive()) {
	    if (!queuedEvents.isEmpty()) {
		for (final org.apollo.game.event.Event queuedEvent : queuedEvents) {
		    session.dispatchEvent(queuedEvent);
		}
		queuedEvents.clear();
	    }
	    session.dispatchEvent(event);
	} else {
	    queuedEvents.add(event);
	}
    }

    /**
     * Sends the initial events.
     */
    private void sendInitialEvents() {
	// vital initial stuff
	send(new IdAssignmentEvent(getIndex(), members)); // TODO Should this be
							  // sent on a
							  // reconnect?
	if (Config.SERVER_LOGIN_SHOW) {
	    sendMessage("Wecome to " + Config.SERVER_NAME + ".");
	}
	// character design screen
	if (!designedCharacter) {
	    interfaceSet.openWindow(3559);
	}
	// send tabs
	for (int i = 0; i < PlayerConstants.TABS.length; i++) {
	    send(new SwitchTabInterfaceEvent(i, PlayerConstants.TABS[i]));
	}
	// force inventories to update
	getInventory().forceRefresh();
	getEquipment().forceRefresh();
	getBank().forceRefresh();
	// force skills to update
	getSkillSet().forceRefresh();
	// send the context menu
	send(new BuildPlayerMenuEvent(3, true, "Attack"));
	send(new BuildPlayerMenuEvent(4, false, "Follow"));
	send(new BuildPlayerMenuEvent(5, false, "Trade with"));
	// send privacy settings
	send(new ChatPrivacySettingsEvent(publicChat, privateChat, trade));
	// send private chat
	World.getWorld().getMessaging().register(this);
	// send the motd
	if (Config.SERVER_MOTD_SHOW) {
	    sendMessage("Alert##Message of the Day##" + Config.SERVER_MOTD);
	}
	// lastly, send the run
	send(new UpdateRunEnergyEvent(getRunEnergy()));
    }

    /**
     * Sets the player's appearance.
     * @param appearance The new appearance.
     */
    public void setAppearance(Appearance appearance) {
	this.appearance = appearance;
	this.getBlockSet().add(SynchronizationBlock.createAppearanceBlock(this));
    }

    /**
     * Sets the character design flag.
     * @param designedCharacter A flag indicating if the character has been
     * designed.
     */
    public void setDesignedCharacter(boolean designedCharacter) {
	this.designedCharacter = designedCharacter;
    }

    /**
     * Sets the dialogue id.
     * @param dialogueId The dialogue id.
     */
    public void setDialogueId(int dialogueId) {
	this.dialogueId = dialogueId;
    }

    /**
     * Sets the last known region.
     * @param lastKnownRegion The last known region.
     */
    public void setLastKnownRegion(Position lastKnownRegion) {
	this.lastKnownRegion = lastKnownRegion;
    }

    /**
     * Changes the membership status of this player.
     * @param members The new membership flag.
     */
    public void setMembers(boolean members) {
	this.members = members;
	if (members) {
	    if (privilegeLevel.toInteger() < PrivilegeLevel.MEMBER.toInteger()) {
		privilegeLevel = PrivilegeLevel.MEMBER;
	    }
	}
    }

    @Override
    public void setPosition(Position position) {
	Region region = World.getWorld().getRegionManager().getRegionByLocation(position);
	if (getRegion() != null) {
	    if (getRegion() != region) {
		getRegion().removePlayer(this);
		setRegion(region);
		region.addPlayer(this);
	    }
	} else {
	    setRegion(region);
	    region.addPlayer(this);
	}
	super.setPosition(position);
    }

    /**
     * Sets the private chat value.
     * @param privateChat The private chat value.
     */
    public void setPrivateChat(int privateChat) {
	this.privateChat = privateChat;
    }

    /**
     * Sets the privilege level.
     * @param privilegeLevel The privilege level.
     */
    public void setPrivilegeLevel(PrivilegeLevel privilegeLevel) {
	this.privilegeLevel = privilegeLevel;
    }

    /**
     * Sets the public chat value.
     * @param publicChat The public chat value.
     */
    public void setPublicChat(int publicChat) {
	this.publicChat = publicChat;
    }

    /**
     * Sets the region changed flag.
     * @param regionChanged A flag indicating if the region has changed.
     */
    public void setRegionChanged(boolean regionChanged) {
	this.regionChanged = regionChanged;
    }

    /**
     * Sets the player's trade request.
     * @param request The player's trade request.
     */
    public void setRequest(Request request) {
	this.request = request;
    }

    /**
     * Set the retaliate flag.
     * @param retaliate the new retaliate
     */
    public void setRetaliate(boolean retaliate) {
	this.retaliate = retaliate;
    }

    /**
     * Sets the player's {@link GameSession}.
     * @param session The player's {@link GameSession}.
     * @param reconnecting The reconnecting flag.
     */
    public void setSession(GameSession session, boolean reconnecting) {
	this.session = session;
	if (!reconnecting) {
	    sendInitialEvents();
	}
	getBlockSet().add(SynchronizationBlock.createAppearanceBlock(this));
    }

    /**
     * Sets the shop.
     * @param shop The shop.
     */
    public void setShop(Shop shop) {
	this.shop = shop;
    }

    /**
     * Sets the trade value.
     * @param trade The trade value.
     */
    public void setTrade(int trade) {
	this.trade = trade;
    }

    /**
     * Sets the trade session.
     * @param tradeSession The trade session.
     * @return The trade session.
     */
    public TradeSession setTradeSession(TradeSession tradeSession) {
	this.tradeSession = tradeSession;
	return tradeSession;
    }

    /**
     * Sets the withdrawing notes flag.
     * @param withdrawingNotes The flag.
     */
    public void setWithdrawingNotes(boolean withdrawingNotes) {
	this.withdrawingNotes = withdrawingNotes;
    }

    /*
     * (non-Javadoc)
     * @see
     * org.apollo.game.model.Character#teleport(org.apollo.game.model.Position)
     */
    @Override
    public void teleport(Position position, boolean action) {
	if (action) {
	    startAction(new TeleportAction(this, position));
	} else {
	    super.teleport(position, action);
	}
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	return Player.class.getName() + " [username=" + credentials.getUsername() + ", privilegeLevel="
		+ privilegeLevel + "]";
    }
}
