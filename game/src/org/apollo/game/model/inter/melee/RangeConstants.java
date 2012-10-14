package org.apollo.game.model.inter.melee;

import org.apollo.game.model.Graphic;

/**
 * A class which contains useful range constants.
 * @author Steve
 */
@SuppressWarnings("javadoc")
public final class RangeConstants {

	/**
	 * An {@link Enumeration} of the possible range projectiles.
	 * @author Steve
	 */
	public enum Range {
		BRONZEA(10, 18, 882), IRONA(9, 19, 884), STEELA(11, 20, 886), MITHRILA(12, 21, 888), ADAMANTA(13, 22, 890), RUNEA(
				15, 24, 892), BRONZEK(212, 219, 864), IRONK(213, 220, 863), STEELK(214, 221, 865), MITHRILK(216, 223,
				866), ADAMANTK(217, 224, 867), RUNEK(218, 225, 868), BLACKK(219, 222, 869);

		/**
		 * The arrows.
		 */
		private final int[] arrows;

		/**
		 * The projectile.
		 */
		private final int projectile;

		/**
		 * The drawback.
		 */
		private final int drawback;

		/**
		 * Creates a new arrow.
		 * @param projectile The projectile.
		 * @param drawback The drawback graphic.
		 * @param arrows The arrow ids.
		 */
		private Range(int projectile, int drawback, int... arrows) {
			this.projectile = projectile;
			this.drawback = drawback;
			this.arrows = arrows;
		}

		/**
		 * Gets the projectile graphic.
		 * @return The projectile graphic.
		 */
		public int getProjectile() {
			return projectile;
		}

		/**
		 * Gets the drawback graphic.
		 * @return The drawback graphic.
		 */
		public Graphic getDrawback() {
			return new Graphic(drawback, 0, 100);
		}

		/**
		 * Gets the range.
		 * @param arrow The weapon.
		 * @return The range.
		 */
		public static Range forArrow(int arrow) {
			for (final Range range : values())
				for (int a : range.arrows)
					if (a == arrow)
						return range;
			return null;
		}

	}

}