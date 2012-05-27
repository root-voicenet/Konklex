package org.apollo.game.sync.block;

import org.apollo.game.event.impl.DamageEvent;

/**
 * The hit {@link SynchronizationBlock}.
 * @author ArrowzFtw
 */
public class HitUpdateBlock extends SynchronizationBlock {

    /**
     * The damage.
     */
    private final DamageEvent damage;

    /**
     * Create a new block.
     * @param damage the damage
     */
    HitUpdateBlock(DamageEvent damage) {
	this.damage = damage;
    }

    /**
     * The damage.
     * @return {@link DamageEvent}
     */
    public DamageEvent getDamage() {
	return damage;
    }
}