package org.apollo.game.model.inter.melee;

/**
 * A class which contains useful range constants.
 * @author Steve
 */
public final class RangeConstants {
	
	/**
	 * An {@link Enumeration} of the possible range projectiles.
	 * @author Steve
	 */
	public enum Range {
		BRONZE(10, 882),
		IRON(9, 884),
		STEEL(11, 886),
		MITHRIL(12, 888),
		ADAMANT(13, 890),
		RUNE(15, 892);
		
		/**
		 * The arrows.
		 */
		private final int[] arrows;
		
		/**
		 * The projectile.
		 */
		private int projectile;
		
		/**
		 * Creates a new arrow.
		 * @param projectile The projectile.
		 * @param arrows The arrow ids.
		 */
		private Range(int projectile, int... arrows) {
			this.projectile = projectile;
			this.arrows = arrows;
		}
		
		/**
		 * Gets the projectile for the arrow.
		 * @param arrow The arrow.
		 * @return The projectile if any, or -1.
		 */
		public static int forArrow(int arrow) {
			for (final Range range : values())
				for (int a : range.arrows)
					if (a == arrow)
						return range.projectile;
			return -1;
		}
		
	}
	
}