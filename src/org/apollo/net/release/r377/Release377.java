package org.apollo.net.release.r377;

import org.apollo.game.event.impl.CloseInterfaceEvent;
import org.apollo.game.event.impl.EnterAmountEvent;
import org.apollo.game.event.impl.IdAssignmentEvent;
import org.apollo.game.event.impl.LogoutEvent;
import org.apollo.game.event.impl.OpenInterfaceEvent;
import org.apollo.game.event.impl.OpenInterfaceSidebarEvent;
import org.apollo.game.event.impl.PlayerSynchronizationEvent;
import org.apollo.game.event.impl.RegionChangeEvent;
import org.apollo.game.event.impl.ServerMessageEvent;
import org.apollo.game.event.impl.SetInterfaceTextEvent;
import org.apollo.game.event.impl.SwitchTabInterfaceEvent;
import org.apollo.game.event.impl.UpdateItemsEvent;
import org.apollo.game.event.impl.UpdateSkillEvent;
import org.apollo.game.event.impl.UpdateSlottedItemsEvent;
import org.apollo.net.meta.PacketMetaDataGroup;
import org.apollo.net.release.Release;

/**
 * An implementation of {@link Release} for the 377 protocol.
 * @author Graham
 */
public final class Release377 extends Release {

	/**
	 * The incoming packet lengths array.
	 */
	public static final int[] PACKET_LENGTHS = new int[256];
	/**
	 * Initialises the {@link #PACKET_LENGTHS} array. TODO make it like the 317 one.
	 */
	static {
		PACKET_LENGTHS[1] = 12;
		PACKET_LENGTHS[3] = 6;
		PACKET_LENGTHS[4] = 6;
		PACKET_LENGTHS[6] = 0;
		PACKET_LENGTHS[8] = 2;
		PACKET_LENGTHS[13] = 2;
		PACKET_LENGTHS[19] = 4;
		PACKET_LENGTHS[22] = 2;
		PACKET_LENGTHS[24] = 6;
		PACKET_LENGTHS[28] = -1;
		PACKET_LENGTHS[31] = 4;
		PACKET_LENGTHS[36] = 8;
		PACKET_LENGTHS[40] = 0;
		PACKET_LENGTHS[42] = 2;
		PACKET_LENGTHS[45] = 2;
		PACKET_LENGTHS[49] = -1;
		PACKET_LENGTHS[50] = 6;
		PACKET_LENGTHS[54] = 6;
		PACKET_LENGTHS[55] = 6;
		PACKET_LENGTHS[56] = -1;
		PACKET_LENGTHS[57] = 8;
		PACKET_LENGTHS[67] = 2;
		PACKET_LENGTHS[71] = 6;
		PACKET_LENGTHS[75] = 4;
		PACKET_LENGTHS[77] = 6;
		PACKET_LENGTHS[78] = 4;
		PACKET_LENGTHS[79] = 2;
		PACKET_LENGTHS[80] = 2;
		PACKET_LENGTHS[83] = 8;
		PACKET_LENGTHS[91] = 6;
		PACKET_LENGTHS[95] = 4;
		PACKET_LENGTHS[100] = 6;
		PACKET_LENGTHS[104] = 4;
		PACKET_LENGTHS[110] = 0;
		PACKET_LENGTHS[112] = 2;
		PACKET_LENGTHS[116] = 2;
		PACKET_LENGTHS[119] = 1;
		PACKET_LENGTHS[120] = 8;
		PACKET_LENGTHS[123] = 7;
		PACKET_LENGTHS[126] = 1;
		PACKET_LENGTHS[136] = 6;
		PACKET_LENGTHS[140] = 4;
		PACKET_LENGTHS[141] = 8;
		PACKET_LENGTHS[143] = 8;
		PACKET_LENGTHS[152] = 12;
		PACKET_LENGTHS[157] = 4;
		PACKET_LENGTHS[158] = 6;
		PACKET_LENGTHS[160] = 8;
		PACKET_LENGTHS[161] = 6;
		PACKET_LENGTHS[163] = 13;
		PACKET_LENGTHS[165] = 1;
		PACKET_LENGTHS[168] = 0;
		PACKET_LENGTHS[171] = -1;
		PACKET_LENGTHS[173] = 3;
		PACKET_LENGTHS[176] = 3;
		PACKET_LENGTHS[177] = 6;
		PACKET_LENGTHS[181] = 6;
		PACKET_LENGTHS[184] = 10;
		PACKET_LENGTHS[187] = 1;
		PACKET_LENGTHS[194] = 2;
		PACKET_LENGTHS[197] = 4;
		PACKET_LENGTHS[202] = 0;
		PACKET_LENGTHS[203] = 6;
		PACKET_LENGTHS[206] = 8;
		PACKET_LENGTHS[210] = 8;
		PACKET_LENGTHS[211] = 12;
		PACKET_LENGTHS[213] = -1;
		PACKET_LENGTHS[217] = 8;
		PACKET_LENGTHS[222] = 3;
		PACKET_LENGTHS[226] = 2;
		PACKET_LENGTHS[227] = 9;
		PACKET_LENGTHS[228] = 6;
		PACKET_LENGTHS[230] = 6;
		PACKET_LENGTHS[231] = 6;
		PACKET_LENGTHS[233] = 2;
		PACKET_LENGTHS[241] = 6;
		PACKET_LENGTHS[244] = -1;
		PACKET_LENGTHS[245] = 2;
		PACKET_LENGTHS[247] = -1;
		PACKET_LENGTHS[248] = 0;
	}

	/**
	 * Creates and initialises this release.
	 */
	public Release377() {
		super(377, PacketMetaDataGroup.createFromArray(PACKET_LENGTHS));
		init();
	}

	/**
	 * Initialises this release by registering encoders and decoders.
	 */
	private void init() {
		// register decoders
		register(new KeepAliveEventDecoder(), 248);
		register(new CharacterDesignEventDecoder(), 163);
		register(new WalkEventDecoder(), 213, 28, 247);
		register(new ChatEventDecoder(), 49);
		register(new ButtonEventDecoder(), 79);
		register(new CommandEventDecoder(), 56);
		register(new SwitchItemEventDecoder(), 123);
		register(new FirstObjectActionEventDecoder(), 181);
		register(new SecondObjectActionEventDecoder(), 241);
		register(new ThirdObjectActionEventDecoder(), 50);
		register(new FirstItemActionEventDecoder(), 3);
		register(new SecondItemActionEventDecoder(), 177);
		register(new ThirdItemActionEventDecoder(), 91);
		register(new FourthItemActionEventDecoder(), 231);
		register(new FifthItemActionEventDecoder(), 158);
		register(new ClosedInterfaceEventDecoder(), 110);
		register(new EnteredAmountEventDecoder(), 75);
		// register encoders
		register(IdAssignmentEvent.class, new IdAssignmentEventEncoder());
		register(RegionChangeEvent.class, new RegionChangeEventEncoder());
		register(ServerMessageEvent.class, new ServerMessageEventEncoder());
		register(PlayerSynchronizationEvent.class, new PlayerSynchronizationEventEncoder());
		register(OpenInterfaceEvent.class, new OpenInterfaceEventEncoder());
		register(CloseInterfaceEvent.class, new CloseInterfaceEventEncoder());
		register(SwitchTabInterfaceEvent.class, new SwitchTabInterfaceEventEncoder());
		register(LogoutEvent.class, new LogoutEventEncoder());
		register(UpdateItemsEvent.class, new UpdateItemsEventEncoder());
		register(UpdateSlottedItemsEvent.class, new UpdateSlottedItemsEventEncoder());
		register(UpdateSkillEvent.class, new UpdateSkillEventEncoder());
		register(OpenInterfaceSidebarEvent.class, new OpenInterfaceSidebarEventEncoder());
		register(EnterAmountEvent.class, new EnterAmountEventEncoder());
		register(SetInterfaceTextEvent.class, new SetInterfaceTextEventEncoder());
	}
}
