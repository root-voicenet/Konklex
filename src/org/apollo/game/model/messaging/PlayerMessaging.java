package org.apollo.game.model.messaging;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apollo.game.event.impl.SendFriendEvent;
import org.apollo.game.event.impl.SendIgnoreEvent;
import org.apollo.game.model.Player;
import org.apollo.game.model.World;
import org.apollo.io.player.PlayerLoader;
import org.apollo.util.NameUtil;

/**
 * Holds the friends.
 * @author Steve
 */
public final class PlayerMessaging {

    /**
     * Event of input.
     */
    public enum Event {
	/**
	 * Adding a user.
	 */
	FRIEND(1),
	/**
	 * Ignoring a user.
	 */
	IGNORE(2);

	/**
	 * The value.
	 */
	private final int value;

	/**
	 * Creates the new event.
	 * @param value The value.
	 */
	Event(int value) {
	    this.value = value;
	}

	/**
	 * Gets the integer value.
	 * @return The integer value.
	 */
	public int toInteger() {
	    return value;
	}
    }

    /**
     * Hold the friends.
     */
    private final Map<String, Event> friends = new HashMap<String, Event>();

    /**
     * Hold the friend capacity.
     */
    private final int capacity;

    /**
     * Hold the chat id.
     */
    private int lastchat = 0;

    /**
     * The friend size.
     */
    private int size = 0;

    /**
     * Holds the player.
     */
    private final Player player;

    /**
     * Create a new friends list.
     * @param player The player.
     */
    public PlayerMessaging(Player player) {
	this.player = player;
	this.capacity = player.isMembers() ? 200 : 100;
    }

    /**
     * Add a user.
     * @param who The user
     * @param what Friend or ignore
     * @param loader Are we loading from the {@link PlayerLoader}
     * @throws Exception the exception
     */
    public void add(String who, Event what, boolean loader) throws Exception {
	who = who.toLowerCase();
	if (loader) {
	    friends.put(who, what);
	} else if (size > capacity) {
	    throw new Exception("Friends list full.");
	} else if (what == Event.FRIEND) {
	    if (friends.get(who) != null) {
		if (friends.get(who).equals(Event.IGNORE)) {
		    throw new Exception("Friend is already ignored.");
		} else if (friends.get(who).equals(Event.FRIEND)) {
		    throw new Exception("Friend is already added.");
		} else {
		    friends.put(who, what);
		}
	    } else {
		friends.put(who, what);
	    }
	} else if (what == Event.IGNORE) {
	    if (friends.get(who) != null) {
		if (friends.get(who).equals(Event.FRIEND)) {
		    throw new Exception("Friend is already added.");
		} else if (friends.get(who).equals(Event.IGNORE)) {
		    throw new Exception("Friend is already ignored.");
		} else {
		    friends.put(who, what);
		}
	    } else {
		friends.put(who, what);
	    }
	}
	this.size = friends.size();
    }

    /**
     * Get the friend capacity.
     * @return {@link Capacity}
     */
    public int capacity() {
	return capacity;
    }

    /**
     * Delete a user.
     * @param who The user.
     * @param what Friend or ignore.
     * @throws Exception the exception
     */
    public void delete(String who, Event what) throws Exception {
	who = who.toLowerCase();
	if (what == Event.FRIEND) {
	    if (friends.get(who) != null) {
		if (friends.get(who).equals(Event.IGNORE)) {
		    throw new Exception("Friend is ignored.");
		} else if (!friends.get(who).equals(Event.FRIEND)) {
		    throw new Exception("Friend is not added.");
		} else {
		    friends.remove(who);
		}
	    } else {
		friends.remove(who);
	    }
	} else if (what == Event.IGNORE) {
	    if (friends.get(who) != null) {
		if (friends.get(who).equals(Event.FRIEND)) {
		    throw new Exception("Friend is added.");
		} else if (!friends.get(who).equals(Event.IGNORE)) {
		    throw new Exception("Friend is not ignored.");
		} else {
		    friends.remove(who);
		}
	    } else {
		friends.remove(who);
	    }
	}
	this.size = friends.size();
    }

    /**
     * Gets the friends list.
     * @return {@link Map}
     */
    public Map<String, Event> getFriends() {
	return this.friends;
    }

    /**
     * Gets the size of the ignore list.
     * @return The size of the ignore list.
     */
    private int getIgnoreSize() {
	int size = 0;
	for (final Entry<String, Event> entry : friends.entrySet()) {
	    if (entry.getValue().toInteger() == 2) {
		size++;
	    }
	}
	return size;
    }

    /**
     * Get the chat id.
     * @return {@link Integer}
     */
    public int getLastId() {
	return lastchat++;
    }

    /**
     * Get the value for the event.
     * @param what The event.
     * @return {@link Integer}
     * @throws Exception the exception
     */
    public int getValue(Event what) throws Exception {
	if (what == Event.FRIEND) {
	    return 1;
	} else if (what == Event.IGNORE) {
	    return 2;
	} else {
	    throw new Exception("Invalid event.");
	}
    }

    /**
     * Get the event for the value.
     * @param what The event.
     * @return {@link Event}
     * @throws Exception the exception
     */
    public Event getValue(int what) throws Exception {
	if (what == 1) {
	    return Event.FRIEND;
	} else if (what == 2) {
	    return Event.IGNORE;
	} else {
	    throw new Exception("Invalid event.");
	}
    }

    /**
     * Refresh the friend list.
     * @throws Exception the exception
     */
    public void refresh() throws Exception {
	final long[] ignores = new long[getIgnoreSize()];
	int i = 0;
	for (final Entry<String, Event> entry : friends.entrySet()) {
	    if (entry.getValue().toInteger() == 1) {
		final boolean online = World.getWorld().isPlayerOnline(entry.getKey());
		final SendFriendEvent sendFriend = new SendFriendEvent(NameUtil.encodeBase37(entry.getKey()),
			online ? 1 : 0);
		player.send(sendFriend);
	    } else {
		ignores[i++] = NameUtil.encodeBase37(entry.getKey());
	    }
	}
	final SendIgnoreEvent sendIgnore = new SendIgnoreEvent(ignores);
	player.send(sendIgnore);
    }

    /**
     * Manually refresh the specified player.
     * @param user The player.
     * @param event Adding or ignoring.
     * @throws Exception the exception.
     */
    public void refresh(String user) throws Exception {
	user = user.toLowerCase();
	if (friends.containsKey(user)) {
	    final int value = friends.get(user).toInteger();
	    if (value == 1) {
		final boolean online = World.getWorld().isPlayerOnline(user);
		final SendFriendEvent sendFriend = new SendFriendEvent(NameUtil.encodeBase37(user), online ? 1 : 0);
		player.send(sendFriend);
	    }
	}
    }

    /**
     * Get the friends size.
     * @return {@link Integer}
     */
    public int size() {
	return size;
    }
}
