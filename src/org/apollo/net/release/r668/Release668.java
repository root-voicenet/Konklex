package org.apollo.net.release.r668;

import org.apollo.game.event.impl.ConfigEvent;
import org.apollo.game.event.impl.LogoutEvent;
import org.apollo.game.event.impl.PositionEvent;
import org.apollo.game.event.impl.RemoveGroundItemEvent;
import org.apollo.game.event.impl.ServerMessageEvent;
import org.apollo.game.event.impl.UpdateRunEnergyEvent;
import org.apollo.net.meta.PacketMetaDataGroup;
import org.apollo.net.release.Release;

/**
 * An implementation of {@link Release} for the 668 protocol.
 * @author Steve
 */
public final class Release668 extends Release {

    /**
     * The incoming packet lengths array.
     */
    public static final int[] PACKET_LENGTHS = new int[256];
    /**
     * Initializes the {@link #PACKET_LENGTHS} array. TODO make it like the 317
     * one.
     */
    static {
	for (int id = 0; id < PACKET_LENGTHS.length; id++)
	    PACKET_LENGTHS[id] = -4;
	PACKET_LENGTHS[64] = 8;
	PACKET_LENGTHS[18] = 8;
	PACKET_LENGTHS[25] = 8;
	PACKET_LENGTHS[41] = -1;
	PACKET_LENGTHS[14] = 3;
	PACKET_LENGTHS[46] = 3;
	PACKET_LENGTHS[87] = 6;
	PACKET_LENGTHS[47] = 9;
	PACKET_LENGTHS[57] = 3;
	PACKET_LENGTHS[67] = 3;
	PACKET_LENGTHS[91] = 8;
	PACKET_LENGTHS[24] = 7;
	PACKET_LENGTHS[73] = 16;
	PACKET_LENGTHS[40] = 11;
	PACKET_LENGTHS[36] = -1;
	PACKET_LENGTHS[74] = -1;
	PACKET_LENGTHS[31] = 3;
	PACKET_LENGTHS[54] = 6;
	PACKET_LENGTHS[12] = -1;
	PACKET_LENGTHS[23] = 1;
	PACKET_LENGTHS[9] = 3;
	PACKET_LENGTHS[17] = -1;
	PACKET_LENGTHS[44] = -1;
	PACKET_LENGTHS[88] = -1;
	PACKET_LENGTHS[42] = 17;
	PACKET_LENGTHS[49] = 3;
	PACKET_LENGTHS[21] = 15;
	PACKET_LENGTHS[59] = -1;
	PACKET_LENGTHS[37] = -1;
	PACKET_LENGTHS[6] = 8;
	PACKET_LENGTHS[55] = 7;
	PACKET_LENGTHS[69] = 9;
	PACKET_LENGTHS[26] = 16;
	PACKET_LENGTHS[39] = 12;
	PACKET_LENGTHS[71] = 4;
	PACKET_LENGTHS[22] = 2;
	PACKET_LENGTHS[32] = -1;
	PACKET_LENGTHS[79] = -1;
	PACKET_LENGTHS[89] = 4;
	PACKET_LENGTHS[90] = -1;
	PACKET_LENGTHS[15] = 4;
	PACKET_LENGTHS[72] = -2;
	PACKET_LENGTHS[20] = 8;
	PACKET_LENGTHS[92] = 3;
	PACKET_LENGTHS[82] = 3;
	PACKET_LENGTHS[28] = 3;
	PACKET_LENGTHS[81] = 8;
	PACKET_LENGTHS[7] = -1;
	PACKET_LENGTHS[4] = 8;
	PACKET_LENGTHS[60] = -1;
	PACKET_LENGTHS[13] = 2;
	PACKET_LENGTHS[52] = 8;
	PACKET_LENGTHS[65] = 11;
	PACKET_LENGTHS[85] = 2;
	PACKET_LENGTHS[86] = 7;
	PACKET_LENGTHS[78] = -1;
	PACKET_LENGTHS[83] = -1;
	PACKET_LENGTHS[27] = 7;
	PACKET_LENGTHS[2] = 9;
	PACKET_LENGTHS[93] = 1;
	PACKET_LENGTHS[70] = -1;
	PACKET_LENGTHS[1] = -1;
	PACKET_LENGTHS[8] = -1;
	PACKET_LENGTHS[11] = 9;
	PACKET_LENGTHS[0] = 9;
	PACKET_LENGTHS[51] = -1;
	PACKET_LENGTHS[5] = 4;
	PACKET_LENGTHS[45] = 7;
	PACKET_LENGTHS[75] = 4;
	PACKET_LENGTHS[53] = 3;
	PACKET_LENGTHS[33] = 0;
	PACKET_LENGTHS[50] = 3;
	PACKET_LENGTHS[76] = 9;
	PACKET_LENGTHS[80] = -1;
	PACKET_LENGTHS[77] = 3;
	PACKET_LENGTHS[68] = -1;
	PACKET_LENGTHS[43] = 3;
	PACKET_LENGTHS[30] = -1;
	PACKET_LENGTHS[19] = 3;
	PACKET_LENGTHS[16] = 0;
	PACKET_LENGTHS[34] = 4;
	PACKET_LENGTHS[48] = 0;
	PACKET_LENGTHS[56] = 0;
	PACKET_LENGTHS[58] = 2;
	PACKET_LENGTHS[10] = 8;
	PACKET_LENGTHS[35] = 7;
	PACKET_LENGTHS[84] = 6;
	PACKET_LENGTHS[66] = 3;
	PACKET_LENGTHS[61] = 8;
	PACKET_LENGTHS[29] = -1;
	PACKET_LENGTHS[62] = 3;
	PACKET_LENGTHS[3] = 4;
	PACKET_LENGTHS[63] = 4;
	PACKET_LENGTHS[73] = 16;
	PACKET_LENGTHS[38] = -1;
    }

    /**
     * Creates and initializes this release.
     */
    public Release668() {
	super(668, PacketMetaDataGroup.createFromArray(PACKET_LENGTHS));
	init();
    }

    /**
     * Initializes this release by registering encoders and decoders.
     */
    private void init() {
	register(new KeepAliveEventDecoder(), 16);
	register(new CameraMovementEventDecoder(), 5);
	register(new ClosedInterfaceEventDecoder(), 56);
	register(new CommandEventDecoder(), 70);
	register(new ClientFocusChangeEventDecoder(), 75);
	register(new ObjectLoadEventDecoder(), 33);
	register(new PlayerIdleEventDecoder(), -1);
	register(LogoutEvent.class, new LogoutEventEncoder());
	register(ServerMessageEvent.class, new ServerMessageEventEncoder());
	register(UpdateRunEnergyEvent.class, new UpdateRunEnergyEventEncoder());
	register(PositionEvent.class, new PositionEventEncoder());
	register(ConfigEvent.class, new ConfigEventEncoder());
	register(RemoveGroundItemEvent.class, new RemoveGroundItemEncoder());
    }
}
