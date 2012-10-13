package org.apollo.game.model.inter.melee;

import java.util.Arrays;
import java.util.List;

import org.apollo.game.model.Animation;
import org.apollo.game.model.Graphic;
import org.apollo.game.model.Item;

/**
 * A class which contains useful magic constants.
 * @author Steve
 */
public final class MagicConstants {

	/**
	 * An {@link Enumeration} of the possible mage projectiles.
	 * @author Steve
	 */
	public enum Mage {
		WINDSTRIKE(1152, 1, 91, new Animation(711), Arrays.asList(new Graphic(90, 0, 100), new Graphic(92, 0, 100)),
				Arrays.asList(new Item(556), new Item(558))), WATERSTRIKE(1154, 5, 94, new Animation(711), Arrays
				.asList(new Graphic(93, 0, 100), new Graphic(95, 0, 100)), Arrays.asList(new Item(555), new Item(556),
				new Item(558))), EARTHSTRIKE(1156, 9, 97, new Animation(711), Arrays.asList(new Graphic(96, 0, 100),
				new Graphic(98, 0, 100)), Arrays.asList(new Item(557, 2), new Item(556), new Item(558))), FIRESTRIKE(
				1158, 13, 100, new Animation(711), Arrays.asList(new Graphic(99, 0, 100), new Graphic(101, 0, 100)),
				Arrays.asList(new Item(554, 3), new Item(556, 2), new Item(558))), WINDBOLT(1160, 17, 118,
				new Animation(711), Arrays.asList(new Graphic(117, 0, 100), new Graphic(119, 0, 100)), Arrays.asList(
						new Item(556, 2), new Item(562))), WATERBOLT(1163, 23, 121, new Animation(711), Arrays.asList(
				new Graphic(120, 0, 100), new Graphic(122, 0, 100)), Arrays.asList(new Item(556, 2), new Item(555, 2),
				new Item(562))), EARTHBOLT(1166, 29, 124, new Animation(711), Arrays.asList(new Graphic(123, 0, 100),
				new Graphic(125, 0, 100)), Arrays.asList(new Item(556, 2), new Item(557, 3), new Item(562))), FIREBOLT(
				1169, 35, 127, new Animation(711), Arrays.asList(new Graphic(126, 0, 100), new Graphic(128, 0, 100)),
				Arrays.asList(new Item(556, 3), new Item(554, 4), new Item(562))), WINDBLAST(1172, 41, 133,
				new Animation(711), Arrays.asList(new Graphic(132, 0, 100), new Graphic(134, 0, 100)), Arrays.asList(
						new Item(556, 3), new Item(560))), WATERBLAST(1175, 47, 136, new Animation(711), Arrays.asList(
				new Graphic(135, 0, 100), new Graphic(137, 0, 100)), Arrays.asList(new Item(556, 3), new Item(555, 3),
				new Item(560))), EARTHBLAST(1177, 53, 139, new Animation(711), Arrays.asList(new Graphic(138, 0, 100),
				new Graphic(140, 0, 100)), Arrays.asList(new Item(556, 3), new Item(557, 4), new Item(560))), FIREBLAST(
				1181, 59, 130, new Animation(711), Arrays.asList(new Graphic(129, 0, 100), new Graphic(131, 0, 100)),
				Arrays.asList(new Item(556, 4), new Item(554, 5), new Item(560))), WINDWAVE(1183, 62, 159,
				new Animation(711), Arrays.asList(new Graphic(158, 0, 100), new Graphic(160, 0, 100)), Arrays.asList(
						new Item(556, 5), new Item(565))), WATERWAVE(1185, 65, 162, new Animation(711), Arrays.asList(
				new Graphic(161, 0, 100), new Graphic(163, 0, 100)), Arrays.asList(new Item(556, 5), new Item(555, 7),
				new Item(565))), EARTHWAVE(1188, 70, 165, new Animation(711), Arrays.asList(new Graphic(164, 0, 100),
				new Graphic(166, 0, 100)), Arrays.asList(new Item(556, 5), new Item(557, 7), new Item(565))), FIREWAVE(
				1189, 75, 156, new Animation(711), Arrays.asList(new Graphic(155, 0, 100), new Graphic(157, 0, 100)),
				Arrays.asList(new Item(556, 5), new Item(554, 7), new Item(565))), SMOKERUSH(12939, 50, 384,
				new Animation(1978), Arrays.asList(null, new Graphic(385, 0, 100)), Arrays.asList(new Item(560, 2),
						new Item(562, 2), new Item(554), new Item(556))), SHADOWRUSH(12987, 52, 378,
				new Animation(1978), Arrays.asList(null, new Graphic(379, 0, 100)), Arrays.asList(new Item(560, 2),
						new Item(562, 2), new Item(566), new Item(556))), BLOODRUSH(12901, 56, 0, new Animation(1978),
				Arrays.asList(null, new Graphic(373, 0, 100)), Arrays.asList(new Item(560, 2), new Item(562, 2),
						new Item(565))), ICERUSH(12861, 58, 360, new Animation(1978), Arrays.asList(null, new Graphic(
				361, 0, 100)), Arrays.asList(new Item(560, 2), new Item(562, 2), new Item(555, 2))), SMOKEBURST(12963,
				62, 0, new Animation(1979), Arrays.asList(null, new Graphic(389, 0, 100)), Arrays.asList(new Item(560,
						2), new Item(562, 4), new Item(556, 2), new Item(554, 2))), SHADOWBURST(13011, 64, 0,
				new Animation(1979), Arrays.asList(null, new Graphic(382, 0, 100)), Arrays.asList(new Item(560, 2),
						new Item(562, 4), new Item(556, 2), new Item(566, 2))), BLOODBURST(12919, 68, 0, new Animation(
				1979), Arrays.asList(null, new Graphic(376, 0, 100)), Arrays.asList(new Item(560, 2), new Item(562, 4),
				new Item(565, 2))), ICEBURST(12881, 70, 0, new Animation(1979), Arrays.asList(null, new Graphic(363, 0,
				100)), Arrays.asList(new Item(560, 2), new Item(562, 4), new Item(555, 4))), SMOKEBLITZ(12951, 74, 386,
				new Animation(1978), Arrays.asList(null, new Graphic(387, 0, 100)), Arrays.asList(new Item(560, 2),
						new Item(554, 2), new Item(565, 2), new Item(556, 2))), SHADOWBLITZ(12999, 76, 380,
				new Animation(1978), Arrays.asList(null, new Graphic(381, 0, 100)), Arrays.asList(new Item(560, 2),
						new Item(565, 2), new Item(556, 2), new Item(566, 2))), BLOODLITZ(12911, 80, 374,
				new Animation(1978), Arrays.asList(null, new Graphic(375, 0, 100)), Arrays.asList(new Item(560, 2),
						new Item(565, 4))), ICEBLITZ(12871, 82, 0, new Animation(1978), Arrays.asList(new Graphic(366,
				0, 100), new Graphic(367, 0, 100)), Arrays.asList(new Item(560, 2), new Item(565, 2), new Item(555, 3))), SMOKEBARRAGE(
				12975, 86, 0, new Animation(1979), Arrays.asList(null, new Graphic(391, 0, 100)), Arrays.asList(
						new Item(560, 4), new Item(565, 2), new Item(556, 4), new Item(554, 4))), SHADOWBARRAGE(13023,
				88, 0, new Animation(1979), Arrays.asList(null, new Graphic(383, 0, 100)), Arrays.asList(new Item(560,
						4), new Item(565, 2), new Item(556, 4), new Item(566, 3))), BLOODBARRAGE(12929, 92, 0,
				new Animation(1979), Arrays.asList(null, new Graphic(377, 0, 100)), Arrays.asList(new Item(560, 4),
						new Item(565, 4), new Item(566))), ICEBARRAGE(12891, 94, 0, new Animation(1979), Arrays.asList(
				null, new Graphic(369, 0, 100)), Arrays.asList(new Item(560, 4), new Item(565, 2), new Item(555, 6)));

		/**
		 * The spell id.
		 */
		private final int spellId;

		/**
		 * The level requirement.
		 */
		private final int levelReq;

		/**
		 * The spell animation.
		 */
		private final Animation animation;

		/**
		 * The projectile.
		 */
		private final int projectile;

		/**
		 * The start and ending graphics.
		 */
		private final List<Graphic> graphics;

		/**
		 * The items required.
		 */
		private final List<Item> items;

		/**
		 * Creates a new magic spell that can be casted.
		 * @param spellId The spell id.
		 * @param projectile The projectile id.
		 * @param levelReq The level required.
		 * @param animation The animation.
		 * @param graphics The graphics.
		 * @param items The items.
		 */
		private Mage(int spellId, int levelReq, int projectile, Animation animation, List<Graphic> graphics,
				List<Item> items) {
			this.spellId = spellId;
			this.projectile = projectile;
			this.levelReq = levelReq;
			this.animation = animation;
			this.graphics = graphics;
			this.items = items;
		}

		/**
		 * Gets the spell id.
		 * @return The spell id.
		 */
		public int getSpellId() {
			return spellId;
		}

		/**
		 * Gets the level required.
		 * @return The level required.
		 */
		public int getLevelReq() {
			return levelReq;
		}

		/**
		 * Gets the animation.
		 * @return The animation.
		 */
		public Animation getAnimation() {
			return animation;
		}

		/**
		 * Gets the projectile.
		 * @return The projectile.
		 */
		public int getProjectile() {
			return projectile;
		}

		/**
		 * Gets the starting and ending graphics.
		 * @return The starting and ending graphics.
		 */
		public List<Graphic> getGraphics() {
			return graphics;
		}

		/**
		 * Gets the items required.
		 * @return The items required.
		 */
		public List<Item> getItems() {
			return items;
		}

		/**
		 * Gets the magic class for the.
		 * @param id The spell id.
		 * @return The mage spell if any, or null.
		 */
		public static Mage forMagic(int id) {
			for (final Mage mage : values())
				if (id == mage.spellId)
					return mage;
			return null;
		}
	}

}