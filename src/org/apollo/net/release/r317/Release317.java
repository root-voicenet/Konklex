package org.apollo.net.release.r317;

import org.apollo.api.method.impl.PrivateChatMethod;
import org.apollo.api.method.impl.SendFriendMethod;
import org.apollo.api.method.impl.SendPlayerMethod;
import org.apollo.api.method.impl.TimeMethod;
import org.apollo.game.event.impl.NpcSynchronizationEvent;
import org.apollo.game.event.impl.PlayerSynchronizationEvent;
import org.apollo.net.meta.PacketMetaDataGroup;
import org.apollo.net.release.Release;

/**
 * An implementation of {@link Release} for the 317 protocol.
 * @author Graham
 */
public final class Release317 extends Release {

	/**
	 * The incoming packet lengths array.
	 */
	public static final int[] PACKET_LENGTHS = { 0, 0, 0, 1, -1, 0, 0, 0, 0, 0, // 0-10
		0, 0, 0, 0, 8, 0, 6, 2, 2, 0, // 10
		0, 2, 0, 6, 0, 12, 0, 0, 0, 0, // 20
		0, 0, 0, 0, 0, 8, 4, 0, 0, 2, // 30
		2, 6, 0, 6, 0, -1, 0, 0, 0, 0, // 40
		0, 0, 0, 12, 0, 0, 0, 8, 0, 0, // 50
		8, 0, 0, 0, 0, 0, 0, 0, 0, 0, // 60
		6, 0, 2, 2, 8, 6, 0, -1, 0, 6, // 70
		0, 0, 0, 0, 0, 1, 4, 6, 0, 0, // 80
		0, 0, 0, 0, 0, 3, 0, 0, -1, 0, // 90
		0, 13, 0, -1, 0, 0, 0, 0, 0, 0,// 100
		0, 0, 0, 0, 0, 0, 0, 6, 0, 0, // 110
		1, 0, 6, 0, 0, 0, -1, 0, 2, 6, // 120
		0, 4, 6, 8, 0, 6, 0, 0, 0, 2, // 130
		0, 0, 0, 0, 0, 6, 0, 0, 0, 0, // 140
		0, 0, 1, 2, 0, 2, 6, 0, 0, 0, // 150
		0, 0, 0, 0, -1, -1, 0, 0, 0, 0,// 160
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0, // 170
		0, 8, 0, 3, 0, 2, 0, 0, 8, 1, // 180
		0, 0, 12, 0, 6, 0, 0, 0, 0, 0, // 190
		2, 0, 0, 0, 0, 0, 0, 0, 4, 0, // 200
		4, 0, 0, 0, 7, 8, 0, 0, 10, 0, // 210
		0, 0, 0, 0, 0, 0, -1, 0, 6, 0, // 220
		1, 0, 0, 0, 6, 0, 6, 8, 1, 0, // 230
		0, 4, 0, 0, 0, 0, -1, 0, -1, 4,// 240
		0, 0, 6, 6, 0, 0, // 250
	};

	/**
	 * The incoming packet lengths array.
	 */
	public static final int[] API_PACKET_LENGTHS = { 0, -1, 17, 1, 10, 2, -1, 0, 0, 0, // 0-10
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0, // 10
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0, // 20
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0, // 30
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0, // 40
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0, // 50
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0, // 60
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0, // 70
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0, // 80
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0, // 90
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0,// 100
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0, // 110
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0, // 120
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0, // 130
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0, // 140
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0, // 150
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0,// 160
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0, // 170
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0, // 180
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0, // 190
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0, // 200
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0, // 210
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0, // 220
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0, // 230
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0,// 240
		0, 0, 0, 0, 0, 0, // 250
	};

	/**
	 * Creates and initializes this release.
	 */
	public Release317() {
		super(317, PacketMetaDataGroup.createFromArray(PACKET_LENGTHS), PacketMetaDataGroup.createFromArray(API_PACKET_LENGTHS));
		init();
	}

	/**
	 * Initializes this release by registering encoders and decoders.
	 */
	private void init() {
		// register encoders
		register(NpcSynchronizationEvent.class, new NpcSynchronizationEventEncoder());
		register(PlayerSynchronizationEvent.class, new PlayerSynchronizationEventEncoder());
		// TODO pkt var 84 => change ground item amount
		// TODO pkt var 146 => player transform
		// register api decoders
		register(new PrivateChatMethodDecoder(), 1);
		register(new ReceiveFriendMethodDecoder(), 2);
		register(new LabelWorldMethodDecoder(), 3);
		register(new ReceivePlayerMethodDecoder(), 4);
		register(new UpdateMethodDecoder(), 5);
		register(new PlayerCommandMethodDecoder(), 6);
		// register api encoders
		register(SendFriendMethod.class, new SendFriendMethodEncoder());
		register(SendPlayerMethod.class, new SendPlayerMethodEncoder());
		register(PrivateChatMethod.class, new PrivateChatMethodEncoder());
		register(TimeMethod.class, new TimeMethodEncoder());
	}
}
