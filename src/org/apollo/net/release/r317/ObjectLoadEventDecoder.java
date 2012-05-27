package org.apollo.net.release.r317;

import org.apollo.game.event.impl.ObjectLoadEvent;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.release.EventDecoder;

/**
 * An {@link EventDecoder} for the {@link ObjectLoadEvent}.
 * @author Steve
 */
public final class ObjectLoadEventDecoder extends EventDecoder<ObjectLoadEvent> {

    /*
     * (non-Javadoc)
     * @see
     * org.apollo.net.release.EventDecoder#decode(org.apollo.net.codec.game.
     * GamePacket)
     */
    @Override
    public ObjectLoadEvent decode(GamePacket packet) {
	return new ObjectLoadEvent();
    }
}
