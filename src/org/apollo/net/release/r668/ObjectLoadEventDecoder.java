package org.apollo.net.release.r668;

import org.apollo.game.event.impl.RegionLoadEvent;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.release.EventDecoder;

/**
 * A {@link EventDecoder} for the {@link RegionLoadEvent}.
 * @author Steve
 */
public class ObjectLoadEventDecoder extends EventDecoder<RegionLoadEvent> {

    /*
     * (non-Javadoc)
     * @see
     * org.apollo.net.release.EventDecoder#decode(org.apollo.net.codec.game.
     * GamePacket)
     */
    @Override
    public RegionLoadEvent decode(GamePacket packet) {
	return new RegionLoadEvent();
    }
}
