package org.apollo.game.sync.block;

import org.apollo.game.event.impl.DamageEvent;

/**
 * The second hit {@link SynchronizationBlock}.
 * @author Steve
 */
public final class SecondHitUpdateBlock extends HitUpdateBlock {

    /**
     * Create a new block.
     * @param damage the damage
     */
    SecondHitUpdateBlock(DamageEvent damage) {
	super(damage);
    }
}
