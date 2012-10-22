package org.apollo.net.release.r484;

import org.apollo.game.event.impl.BuildPlayerMenuEvent;
import org.apollo.game.event.impl.PlayerSynchronizationEvent;
import org.apollo.game.event.impl.RegionChangeEvent;
import org.apollo.game.event.impl.UpdateSkillEvent;
import org.apollo.net.meta.PacketMetaDataGroup;
import org.apollo.net.release.Release;

/**
 * An implementation of {@link Release} for the 484 protocol.
 * @author Steve
 */
public final class Release484 extends Release {

	/**
	 * The packet sizes.
	 */
	public static final int PACKET_SIZES[] = new int[256];

	/**
	 * Initializes the packets.
	 */
	static {
		for (int i = 0; i < PACKET_SIZES.length; i++) {
			PACKET_SIZES[i] = -3;
		}
	}

	/**
	 * Creates and initializes this release.
	 */
	public Release484() {
		super(484, PacketMetaDataGroup.createFromArray(PACKET_SIZES));
		init();
	}

	/**
	 * Initializes this release by registering encoders and decoders.
	 */
	private void init() {
		// register encoders
		register(RegionChangeEvent.class, new RegionChangeEventEncoder());
		register(UpdateSkillEvent.class, new UpdateSkillEventEncoder());
		register(BuildPlayerMenuEvent.class, new BuildPlayerMenuEventEncoder());
		register(PlayerSynchronizationEvent.class, new PlayerSynchronizationEventEncoder());
	}
}
