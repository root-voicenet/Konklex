package org.apollo.game.sync.seg;

import org.apollo.game.model.Position;
import org.apollo.game.sync.block.SynchronizationBlockSet;

/**
 * A {@link SynchronizationSegment} which adds a character.
 * @author Graham
 */
public final class AddCharacterSegment extends SynchronizationSegment {

    /**
     * The index.
     */
    private final int index;

    /**
     * The position.
     */
    private final Position position;

    /**
     * Creates the add character segment.
     * @param blockSet The block set.
     * @param index The characters's index.
     * @param position The position.
     */
    public AddCharacterSegment(SynchronizationBlockSet blockSet, int index, Position position) {
	super(blockSet);
	this.index = index;
	this.position = position;
    }

    /**
     * Gets the character's index.
     * @return The index.
     */
    public int getIndex() {
	return index;
    }

    /**
     * Gets the position.
     * @return The position.
     */
    public Position getPosition() {
	return position;
    }

    /*
     * (non-Javadoc)
     * @see org.apollo.game.sync.seg.SynchronizationSegment#getType()
     */
    @Override
    public SegmentType getType() {
	return SegmentType.ADD_CHARACTER;
    }
}
