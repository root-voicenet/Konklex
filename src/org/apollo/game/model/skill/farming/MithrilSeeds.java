package org.apollo.game.model.skill.farming;

import org.apollo.game.model.Animation;
import org.apollo.game.model.GameObject;
import org.apollo.game.model.Player;
import org.apollo.game.model.Position;
import org.apollo.game.model.World;
import org.apollo.game.model.def.ObjectDefinition;
import org.apollo.util.TextUtil;

public class MithrilSeeds {

	public static final int[] flowerObjects = { 2980, 2981, 2982, 2983, 2984, 2985, 2986, 2987, 2988 };

	public static final int[] flowerItems = { 2460, 2462, 2464, 2466, 2468, 2470, 2472, 2474, 2476 };

	public static void plantMithrilSeed(Player player) {
		int flower = flowerObjects[TextUtil.random(flowerObjects.length) - 1];
		int x = player.getPosition().getX();
		int y = player.getPosition().getY();
		GameObject p = new GameObject(ObjectDefinition.forId(flower), player.getPosition(), 10, -1);
		if (World.getWorld().getObjects().contains(p)) {
			player.sendMessage("You can't plant a flower here.");
			return;
		}
		if (player.getInventory().contains(299)) {
			player.getInventory().remove(299);
		}
		else {
			return;
		}
		player.playAnimation(new Animation(827));
		World.getWorld().register(p);
		player.turnTo(new Position(x, y));
	}
}
