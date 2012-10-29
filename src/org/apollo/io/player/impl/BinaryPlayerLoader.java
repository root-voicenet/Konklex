package org.apollo.io.player.impl;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apollo.game.model.Appearance;
import org.apollo.game.model.Gender;
import org.apollo.game.model.Inventory;
import org.apollo.game.model.Item;
import org.apollo.game.model.Player;
import org.apollo.game.model.Player.PrivilegeLevel;
import org.apollo.game.model.Position;
import org.apollo.game.model.Skill;
import org.apollo.game.model.SkillSet;
import org.apollo.game.model.messaging.PlayerMessaging;
import org.apollo.io.player.PlayerLoader;
import org.apollo.io.player.PlayerLoaderResponse;
import org.apollo.net.codec.login.LoginConstants;
import org.apollo.security.PlayerCredentials;
import org.apollo.util.NameUtil;
import org.apollo.util.StreamUtil;

/**
 * A {@link PlayerLoader} implementation that loads data from a binary file.
 * @author Graham
 */
public final class BinaryPlayerLoader implements PlayerLoader {

	/**
	 * The default spawn position.
	 */
	private static final Position SPAWN_POSITION = new Position(2827, 3344);

	/**
	 * Temporary size.
	 */
	private int size = 0;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apollo.io.player.PlayerLoader#loadPlayer(org.apollo.security. PlayerCredentials)
	 */
	@Override
	public PlayerLoaderResponse loadPlayer(PlayerCredentials credentials) throws Exception {
		final File f = BinaryPlayerUtil.getFile(credentials.getUsername());
		if (!f.exists()) {
			final Player player = new Player(credentials, SPAWN_POSITION);
			return new PlayerLoaderResponse(LoginConstants.STATUS_OK, player);
		}
		final DataInputStream in = new DataInputStream(new FileInputStream(f));
		try {
			// read credentials and privileges
			final String name = StreamUtil.readString(in);
			final String pass = StreamUtil.readString(in);
			if (!name.equalsIgnoreCase(credentials.getUsername()) || !pass.equalsIgnoreCase(credentials.getPassword()))
				return new PlayerLoaderResponse(LoginConstants.STATUS_INVALID_CREDENTIALS);
			final PrivilegeLevel privilegeLevel = PrivilegeLevel.valueOf(in.readByte());
			final boolean members = in.readBoolean();
			// read position
			final int x = in.readUnsignedShort();
			final int y = in.readUnsignedShort();
			final int height = in.readUnsignedByte();
			// read privacy settings
			final int publicChat = in.readUnsignedShort();
			final int privateChat = in.readUnsignedShort();
			final int trade = in.readUnsignedShort();
			// read the energy
			final int energy = in.readByte();
			// read appearance
			final boolean designedCharacter = in.readBoolean();
			final int genderIntValue = in.readUnsignedByte();
			final Gender gender = genderIntValue == Gender.MALE.toInteger() ? Gender.MALE : Gender.FEMALE;
			final int[] style = new int[7];
			for (int i = 0; i < style.length; i++)
				style[i] = in.readUnsignedByte();
			final int[] colors = new int[5];
			for (int i = 0; i < colors.length; i++)
				colors[i] = in.readUnsignedByte();
			final Player p = new Player(credentials, new Position(x, y, height));
			p.setPrivilegeLevel(privilegeLevel);
			p.setMembers(members);
			p.setDesignedCharacter(designedCharacter);
			p.setAppearance(new Appearance(gender, style, colors));
			p.setRunEnergy(energy);
			// set the privacy settings
			p.setPublicChat(publicChat);
			p.setPrivateChat(privateChat);
			p.setTrade(trade);
			// read inventories
			readInventory(in, p.getInventory());
			readInventory(in, p.getEquipment());
			readInventory(in, p.getBank());
			// read skills
			size = in.readUnsignedByte();
			final SkillSet skills = p.getSkillSet();
			skills.stopFiringEvents();
			try {
				for (int i = 0; i < size; i++) {
					final int level = in.readUnsignedByte();
					final double experience = in.readDouble();
					skills.setSkill(i, new Skill(experience, level, SkillSet.getLevelForExperience(experience)));
				}
			}
			finally {
				skills.startFiringEvents();
			}
			// read friends
			size = in.readUnsignedByte();
			final PlayerMessaging friends = p.getMessaging();
			for (int i = 0; i < size; i++) {
				final String user = NameUtil.decodeBase37(in.readLong());
				final int event = in.readUnsignedByte();
				p.getMessaging().add(user, friends.getValue(event), true);
			}
			return new PlayerLoaderResponse(LoginConstants.STATUS_OK, p);
		}
		finally {
			in.close();
		}
	}

	/**
	 * Reads an inventory from the input stream.
	 * @param in The input stream.
	 * @param inventory The inventory.
	 * @throws IOException if an I/O error occurs.
	 */
	private void readInventory(DataInputStream in, Inventory inventory) throws IOException {
		final int capacity = in.readUnsignedShort();
		inventory.stopFiringEvents();
		try {
			for (int slot = 0; slot < capacity; slot++) {
				final int id = in.readUnsignedShort();
				final int amount = in.readInt();
				if (id != 0)
					inventory.set(slot, new Item(id - 1, amount));
				else
					inventory.reset(slot);
			}
		}
		finally {
			inventory.startFiringEvents();
		}
	}
}
