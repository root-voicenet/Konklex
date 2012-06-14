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
 * This class manages all six distinct types of interface (the last two are not
 * present on 317 servers).
 * </p>
 * <ul>
 * <li><strong>Windows:</strong> the ones people mostly associate with the word
 * interfaces. Things like your bank, the wildy warning screen, the trade
 * screen, etc.</li>
 * <li><strong>Overlays:</strong> display in the same place as windows, but
 * don't prevent you from moving. For example, the wilderness level indicator.</li>
 * <li><strong>Dialogues:</strong> interfaces which are displayed over the chat
 * box.</li>
 * <li><strong>Sidebars:</strong> an interface which displays over the inventory
 * area.</li>
 * <li><strong>Fullscreen windows:</strong> a window which displays over the
 * whole screen e.g. the 377 welcome screen.</li>
 * <li><strong>Fullscreen background:</strong> an interface displayed behind the
 * fullscreen window, typically a blank, black screen.</li>
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
     * Called when the player has clicked the specified button. Notifies the
     * current interface listener.
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
     * Closes the current open interface(s).
     */
    public void close() {
	closeAndNotify(false);
	player.send(new CloseInterfaceEvent());
    }

    /**
     * An internal method for closing the interface, notifying the listener if
     * appropriate, but not sending any events.
     * @param manually Flag for if the interface was closed manually (by the
     * player).
     */
    private void closeAndNotify(boolean manually) {
	amountListener = null;
	dialogueListener = null;
	interfaces.clear();
	if (listener != null) {
	    listener.interfaceClosed(player, manually);
	    listener = null;
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
     * Called when the player has clicked the "Click here to continue" button on
     * a dialogue.
     */
    public void continueRequested() {
	if (dialogueListener != null)
	    dialogueListener.continued(player);
    }

    /**
     * Called when the client has entered the specified amount. Notifies the
     * current listener.
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
	if (interfaces.containsValue(closedInterface))
	    closeAndNotify(true);
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
    public void openWalkable(int windowId) {
	// interfaces.put(InterfaceType.OVERLAY, windowId);
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
}
