package org.apollo.game.model;

import java.util.HashMap;
import java.util.Map;

import org.apollo.game.event.impl.CloseInterfaceEvent;
import org.apollo.game.event.impl.EnterAmountEvent;
import org.apollo.game.event.impl.OpenInterfaceDialogueEvent;
import org.apollo.game.event.impl.OpenInterfaceEvent;
import org.apollo.game.event.impl.OpenInterfaceOverlayEvent;
import org.apollo.game.event.impl.OpenInterfaceSidebarEvent;
import org.apollo.game.event.impl.SetInterfaceTextEvent;
import org.apollo.game.model.inter.EnterAmountListener;
import org.apollo.game.model.inter.InterfaceListener;
import org.apollo.game.model.inter.dialog.DialogueListener;

/**
 * Represents the set of interfaces the player has open.
 * <p>
 * This class manages all six distinct types of interface (the last two are not present on 317 servers).
 * </p>
 * <ul>
 * <li><strong>Windows:</strong> the ones people mostly associate with the word interfaces. Things like your bank, the
 * wildy warning screen, the trade screen, etc.</li>
 * <li><strong>Overlays:</strong> display in the same place as windows, but don't prevent you from moving. For example,
 * the wilderness level indicator.</li>
 * <li><strong>Dialogues:</strong> interfaces which are displayed over the chat box.</li>
 * <li><strong>Sidebars:</strong> an interface which displays over the inventory area.</li>
 * <li><strong>Fullscreen windows:</strong> a window which displays over the whole screen e.g. the 377 welcome screen.</li>
 * <li><strong>Fullscreen background:</strong> an interface displayed behind the fullscreen window, typically a blank,
 * black screen.</li>
 * </ul>
 * @author Graham
 */
public final class InterfaceSet {

	/**
	 * The player whose interfaces are being managed.
	 */
	private final Player player;

	/**
	 * A map of open interfaces.
	 */
	private final Map<InterfaceType, Integer> interfaces = new HashMap<InterfaceType, Integer>();

	/**
	 * The current listener.
	 */
	private InterfaceListener listener;

	/**
	 * The current enter amount listener.
	 */
	private EnterAmountListener amountListener;

	/**
	 * The current chat box dialogue listener.
	 */
	private DialogueListener dialogueListener;

	/**
	 * Creates an interface set.
	 * @param player The player.
	 */
	public InterfaceSet(Player player) {
		this.player = player;
	}

	/**
	 * Called when the player has clicked the specified button. Notifies the current interface listener.
	 * @param button The button's interface id.
	 * @return {@code true} if the event handler chain should be broken.
	 */
	public boolean buttonClicked(int button) {
		if (listener != null)
			if (listener.buttonClicked(player, button))
				return true;
		if (dialogueListener != null)
			return dialogueListener.buttonClicked(player, button);
		return false;
	}

	/**
	 * Clears the interfaces, leaving the overlay's in the map.
	 */
	private void clear() {
		for (InterfaceType interfaceType : interfaces.keySet()) {
			if (!interfaceType.equals(InterfaceType.OVERLAY)) {
				interfaces.remove(interfaceType);
			}
		}
	}

	/**
	 * Closes the current open interface(s).
	 * @deprecated Consider using close(boolean) as this will be removed soon.
	 */
	@Deprecated
	public void close() {
		int temp = size();
		closeAndNotify(false);
		if (temp > 0) {
			player.send(new CloseInterfaceEvent());
		}
	}

	/**
	 * Closes the current open interfaces(s).
	 * @param force The forcible flag that closes overlay's also.
	 */
	public void close(boolean force) {
		int temp = force ? interfaces.size() : size();
		closeAndNotify(false, force);
		if (temp > 0) {
			player.send(new CloseInterfaceEvent());
		}
	}

	/**
	 * An internal method for closing the interface, notifying the listener if appropriate, but not sending any events.
	 * @param manually Flag for if the interface was closed manually (by the player).
	 */
	private void closeAndNotify(boolean... manually) {
		amountListener = null;
		dialogueListener = null;
		if (manually.length > 0) {
			if (listener != null) {
				listener.interfaceClosed(player, manually[0]);
				listener = null;
			}
			if (manually.length > 1 && manually[1]) {
				interfaces.clear();
			}
			else {
				clear();
			}
		}
	}

	/**
	 * Checks if this interface sets contains the specified interface.
	 * @param id The interface's id.
	 * @return {@code true} if so, {@code false} if not.
	 */
	public boolean contains(int id) {
		return interfaces.containsValue(id);
	}

	/**
	 * Checks if this interface set contains the specified interface type.
	 * @param type The interface's type.
	 * @return {@code true} if so, {@code false} if not.
	 */
	public boolean contains(InterfaceType type) {
		return interfaces.containsKey(type);
	}

	/**
	 * Called when the player has clicked the "Click here to continue" button on a dialogue.
	 */
	public void continueRequested() {
		if (dialogueListener != null) {
			dialogueListener.continued(player);
		}
	}

	/**
	 * Called when the client has entered the specified amount. Notifies the current listener.
	 * @param amount The amount.
	 */
	public void enteredAmount(int amount) {
		if (amountListener != null) {
			amountListener.amountEntered(amount);
			amountListener = null;
		}
	}

	/**
	 * Sent by the client when it has closed an interface.
	 */
	public void interfaceClosed() {
		closeAndNotify(true);
	}

	/**
	 * Sent by the client when it has closed an interface.
	 * @param closedInterface The interface that was closed.
	 */
	public void interfaceClosed(int closedInterface) {
		if (interfaces.containsValue(closedInterface)) {
			closeAndNotify(true);
		}
	}

	/**
	 * Opens a chat box dialogue.
	 * @param listener The listener for the dialogue.
	 * @param dialogueId The dialogue's id.
	 */
	public void openDialogue(DialogueListener listener, int dialogueId) {
		closeAndNotify(false);
		this.dialogueListener = listener;
		this.listener = listener;
		interfaces.put(InterfaceType.DIALOGUE, dialogueId);
		player.send(new OpenInterfaceDialogueEvent(dialogueId));
	}

	/**
	 * Opens a chat box dialogue.
	 * @param dialogueId The dialogue's id.
	 */
	public void openDialogue(int dialogueId) {
		openDialogue(null, dialogueId);
	}

	/**
	 * Opens the enter amount dialog.
	 * @param listener The enter amount listener.
	 */
	public void openEnterAmountDialog(EnterAmountListener listener) {
		this.amountListener = listener;
		player.send(new EnterAmountEvent());
	}

	/**
	 * Opens a overlay window.
	 * @param windowId The window's id.
	 */
	public void openOverlay(int windowId) {
		openOverlay(null, windowId);
	}

	/**
	 * Opens a overlay window with the specified listener.
	 * @param listener The listener for this interface.
	 * @param windowId The window's id.
	 */
	public void openOverlay(InterfaceListener listener, int windowId) {
		closeAndNotify(false);
		this.listener = listener;
		interfaces.put(InterfaceType.OVERLAY, windowId);
		player.send(new OpenInterfaceOverlayEvent(windowId));
	}

	/**
	 * Opens a window.
	 * @param windowId The window's id.
	 */
	public void openWindow(int windowId) {
		openWindow(null, windowId);
	}

	/**
	 * Opens a window with the specified listener.
	 * @param listener The listener for this interface.
	 * @param windowId The window's id.
	 */
	public void openWindow(InterfaceListener listener, int windowId) {
		closeAndNotify(false);
		this.listener = listener;
		interfaces.put(InterfaceType.WINDOW, windowId);
		player.send(new OpenInterfaceEvent(windowId));
	}

	/**
	 * Opens a window and inventory sidebar.
	 * @param windowId The window's id.
	 * @param sidebarId The sidebar's id.
	 */
	public void openWindowWithSidebar(int windowId, int sidebarId) {
		openWindowWithSidebar(null, windowId, sidebarId);
	}

	/**
	 * Opens a window and inventory sidebar with the specified listener.
	 * @param listener The listener for this interface.
	 * @param windowId The window's id.
	 * @param sidebarId The sidebar's id.
	 */
	public void openWindowWithSidebar(InterfaceListener listener, int windowId, int sidebarId) {
		closeAndNotify(false);
		this.listener = listener;
		interfaces.put(InterfaceType.WINDOW, windowId);
		interfaces.put(InterfaceType.SIDEBAR, sidebarId);
		player.send(new OpenInterfaceSidebarEvent(windowId, sidebarId));
	}

	/**
	 * Returns the current listener and removes the old one.
	 * @return The listener.
	 */
	public InterfaceListener removeListener() {
		final InterfaceListener listener = this.listener;
		this.listener = null;
		return listener;
	}

	/**
	 * Sends a statement.
	 * @param lines The lines.
	 */
	public void sendStatement(String... lines) {
		switch (lines.length) {
		case 1:
			sendStatement(lines[0]);
			break;
		case 2:
			sendStatement(lines[0], lines[1]);
			break;
		case 3:
			sendStatement(lines[0], lines[1], lines[2]);
			break;
		case 4:
			sendStatement(lines[0], lines[1], lines[2], lines[3]);
			break;
		case 5:
			sendStatement(lines[0], lines[1], lines[2], lines[3], lines[4]);
			break;
		}
	}

	/**
	 * Sends a statement.
	 * @param line1 The line 1.
	 */
	private void sendStatement(String line1) {
		setInterfaceText(357, line1);
		openDialogue(356);
	}

	/**
	 * Sends a statement.
	 * @param line1 The line 1.
	 * @param line2 The line 2.
	 */
	private void sendStatement(String line1, String line2) {
		setInterfaceText(360, line1);
		setInterfaceText(361, line2);
		openDialogue(359);
	}

	/**
	 * Sends a statement.
	 * @param line1 The line 1.
	 * @param line2 The line 2.
	 * @param line3 The line 3.
	 */
	private void sendStatement(String line1, String line2, String line3) {
		setInterfaceText(364, line1);
		setInterfaceText(365, line2);
		setInterfaceText(366, line3);
		openDialogue(363);
	}

	/**
	 * Sends a statement.
	 * @param line1 The line 1.
	 * @param line2 The line 2.
	 * @param line3 The line 3.
	 * @param line4 The line 4.
	 */
	private void sendStatement(String line1, String line2, String line3, String line4) {
		setInterfaceText(369, line1);
		setInterfaceText(370, line2);
		setInterfaceText(371, line3);
		setInterfaceText(372, line4);
		openDialogue(368);
	}

	/**
	 * Sends a statement.
	 * @param line1 The line 1.
	 * @param line2 The line 2.
	 * @param line3 The line 3.
	 * @param line4 The line 4.
	 * @param line5 The line 5.
	 */
	private void sendStatement(String line1, String line2, String line3, String line4, String line5) {
		setInterfaceText(375, line1);
		setInterfaceText(376, line2);
		setInterfaceText(377, line3);
		setInterfaceText(378, line4);
		setInterfaceText(379, line5);
		openDialogue(374);
	}

	/**
	 * Sets the text on a window specified listener.
	 * @param interfaceId The window's id.
	 * @param text The text to be displayed
	 */
	public void setInterfaceText(int interfaceId, String text) {
		setInterfaceText(null, interfaceId, text);
	}

	/**
	 * Sets the text on a window specified listener.
	 * @param listener The listener for this interface.
	 * @param interfaceId The window's id.
	 * @param text The text to be displayed
	 */
	public void setInterfaceText(InterfaceListener listener, int interfaceId, String text) {
		closeAndNotify(false);
		this.listener = listener;
		interfaces.put(InterfaceType.DIALOGUE, interfaceId);
		player.send(new SetInterfaceTextEvent(interfaceId, text));
	}

	/**
	 * Gets the size of the interfaces.
	 * @return The size of the interfaces.
	 */
	public int size() {
		int i = 0;
		for (InterfaceType interfaceSet : interfaces.keySet()) {
			if (interfaceSet.equals(InterfaceType.OVERLAY))
				return 0;
			else {
				i++;
			}
		}
		return i;
	}
}
