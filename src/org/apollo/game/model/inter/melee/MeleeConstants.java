package org.apollo.game.model.inter.melee;

/**
 * Contains some of the melee constants.
 * @author Steve
 */
public class MeleeConstants {

	// TODO: Make the melee into a java class for now, not a stupid plugin
	// :hurr:
	/**
	 * Returns the run animation for the player's weapon id.
	 * @param weaponId The weapon id.
	 * @return The run animation.
	 */
	public static int getRunAnimation(int weaponId) {
		switch (weaponId) {
		case 4151:
			return 1661;
		case 1419:
			return 9739;
		default:
			return 0x338;
		}
	}

	/**
	 * Returns the stand animation for the player's weapon id.
	 * @param weaponId The weapon id.
	 * @return The stand animation.
	 */
	public static int getStandAnimation(int weaponId) {
		switch (weaponId) {
		case 837:
			return 2237;
		case 4084:
			return 1642;
		case 4718:
			return 2065;
		case 746:
		case 667:
		case 35:
		case 2402:
		case 8100:
		case 16033:
		case 15936:
		case 15914:
			return 7047;
		case 1307:
		case 1309:
		case 1311:
		case 1313:
		case 1315:
		case 1317:
		case 1319:
		case 6609:
		case 7158:
			return 1131;
		case 4755:
			return 2061;
		case 4734:
			return 2074;
		case 4710:
		case 4726:
			return 802;
		case 4153:
			return 1662;
		case 6528:
			return 0x811;
		case 4565:
			return 1836;
		case 4151:
			return 11973;
		case 1419:
			return 847;
		default:
			return 0x328;
		}
	}

	/**
	 * Returns the walk animation for the player's weapon id.
	 * @param weaponId The weapon id.
	 * @return The walk animation.
	 */
	public static int getWalkAnimation(int weaponId) {
		switch (weaponId) {
		case 3565:
			return 1836;
		case 4153:
			return 1663;
		case 4151:
			return 11975;
		case 4755:
			return 824;
		case 1419:
			return 9738;
		default:
			return 0x333;
		}
	}
}
