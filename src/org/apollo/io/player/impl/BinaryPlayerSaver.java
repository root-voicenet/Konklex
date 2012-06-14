package org.apollo.io.player.impl;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map.Entry;

import org.apollo.game.model.Appearance;
import org.apollo.game.model.Inventory;
import org.apollo.game.model.Item;
import org.apollo.game.model.Player;
import org.apollo.game.model.Position;
import org.apollo.game.model.Skill;
import org.apollo.game.model.SkillSet;
import org.apollo.game.model.messaging.PlayerMessaging;
import org.apollo.game.model.messaging.PlayerMessaging.Event;
import org.apollo.io.player.PlayerSaver;
import org.apollo.util.NameUtil;
import org.apollo.util.StreamUtil;

/**
 * A {@link PlayerSaver} implementation that saves player data to a binary file.
 * @author Graham
 */
public final class BinaryPlayerSaver implements PlayerSaver {

    /*
     * (non-Javadoc)
     * @see
     * org.apollo.io.player.PlayerSaver#savePlayer(org.apollo.game.model.Player)
     */
    @Override
    public void savePlayer(Player player) throws Exception {
	final File f = BinaryPlayerUtil.getFile(player.getName());
	final DataOutputStream out = new DataOutputStream(new FileOutputStream(f));
	try {
	    // write credentials and privileges
	    StreamUtil.writeString(out, player.getName());
	    StreamUtil.writeString(out, player.getCredentials().getPassword());
	    out.writeByte(player.getPrivilegeLevel().toInteger());
	    out.writeBoolean(player.isMembers());
	    // write position
	    final Position position = player.getPosition();
	    out.writeShort(position.getX());
	    out.writeShort(position.getY());
	    out.writeByte(position.getHeight());
	    // write the privacy settings
	    out.writeShort(player.getPublicChat());
	    out.writeShort(player.getPrivateChat());
	    out.writeShort(player.getTrade());
	    // get the run energy
	    out.writeByte(player.getRunEnergy());
	    // get the money pouch amount
	    // write appearance
	    out.writeBoolean(player.hasDesignedCharacter());
	    final Appearance appearance = player.getAppearance();
	    out.writeByte(appearance.getGender().toInteger());
	    final int[] style = appearance.getStyle();
	    for (final int element : style)
		out.writeByte(element);
	    final int[] colors = appearance.getColors();
	    for (final int color : colors)
		out.writeByte(color);
	    out.flush();
	    // write inventories
	    writeInventory(out, player.getInventory());
	    writeInventory(out, player.getEquipment());
	    writeInventory(out, player.getBank());
	    // write skills
	    final SkillSet skills = player.getSkillSet();
	    out.writeByte(skills.size());
	    for (int i = 0; i < skills.size(); i++) {
		final Skill skill = skills.getSkill(i);
		out.writeByte(skill.getCurrentLevel());
		out.writeDouble(skill.getExperience());
	    }
	    // write friends
	    final PlayerMessaging friends = player.getMessaging();
	    out.writeByte(friends.size());
	    for (final Entry<String, Event> entry : friends.getFriends().entrySet()) {
		out.writeLong(NameUtil.encodeBase37(entry.getKey()));
		out.writeByte(friends.getValue(entry.getValue()));
	    }
	} finally {
	    out.close();
	}
    }

    /**
     * Writes an inventory to the specified output stream.
     * @param out The output stream.
     * @param inventory The inventory.
     * @throws IOException if an I/O error occurs.
     */
    private void writeInventory(DataOutputStream out, Inventory inventory) throws IOException {
	final int capacity = inventory.capacity();
	out.writeShort(capacity);
	for (int slot = 0; slot < capacity; slot++) {
	    final Item item = inventory.get(slot);
	    if (item != null) {
		out.writeShort(item.getId() + 1);
		out.writeInt(item.getAmount());
	    } else {
		out.writeShort(0);
		out.writeInt(0);
	    }
	}
    }
}
