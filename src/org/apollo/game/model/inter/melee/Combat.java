package org.apollo.game.model.inter.melee;

import org.apollo.game.event.impl.ProjectileEvent;
import org.apollo.game.model.Animation;
import org.apollo.game.model.Character;
import org.apollo.game.model.EquipmentConstants;
import org.apollo.game.model.GroundItem;
import org.apollo.game.model.Inventory;
import org.apollo.game.model.Item;
import org.apollo.game.model.Player;
import org.apollo.game.model.Npc;
import org.apollo.game.model.Position;
import org.apollo.game.model.Skill;
import org.apollo.game.model.World;
import org.apollo.game.scheduling.ScheduledTask;
import org.apollo.game.sync.block.SynchronizationBlock;
import org.apollo.util.CombatUtil;
import org.apollo.util.TextUtil;

/**
 * An Character based combat handler, used for npcs vs npcs, player vs player and player vs npc.
 * @author iRageQuit201
 * @author Steve
 */
public final class Combat {

	/**
	 * The default spawn position.
	 */
	private static final Position SPAWN_POSITION = new Position(3094, 3495);

	/**
	 * Handles the attacking packet.
	 * @param source	Character that initiates combat.
	 * @param victim	Character that is being attacked.
	 */
	public static void attack(Character source, Character victim) {
		if (source == null || victim == null ||
				source.getHealth() <= 0 || victim.getHealth() <= 0) {
			return;
		}
		if (source.getMeleeSet().isUnderAttack()) {
			if (source instanceof Player) {
				((Player)source).sendMessage("You are already in combat!");
			}
			return;
		}
		if (victim.getMeleeSet().isUnderAttack()) {
			if (victim.getMeleeSet().getInteractingCharacter() != source) {
				if (source instanceof Player) {
					((Player)source).sendMessage("This " + (victim instanceof Npc ? "npc" : "player") + " is already in combat!");
				}
			}
			return;
		}
		if (source.getPosition().isWithinDistance(victim.getPosition(), 2)) {
			initiateInteraction(source, victim);
		} else {
			walkToVictim(source, victim, true);
		}
	}

	/**
	 * Combat process, used to check if player is under attack,
	 * lower attack timer, walk to the Character if not in distance, etc.
	 * @param source	Character to process.
	 */
	public static void process(Character source) {
		if (source.getMeleeSet().getInteractingCharacter() != null && source.getMeleeSet().isUnderAttack() && System.currentTimeMillis() - source.getMeleeSet().getLastAttack() >= 10000) {
			if (!source.getMeleeSet().isAttacking())
				source.getMeleeSet().setInteractingCharacter(null);
			source.getMeleeSet().setUnderAttack(false);
		}
		if (source.getMeleeSet().getInteractingCharacter() != null) {
			Character victim = source.getMeleeSet().getInteractingCharacter();
			if (victim.getHealth() <= 0 && !victim.getMeleeSet().isDying()) {
				victim.getMeleeSet().setAttacking(false);
				victim.getMeleeSet().setUnderAttack(false);
				victim.getMeleeSet().setInteractingCharacter(null);
				victim.getBlockSet().add(SynchronizationBlock.createTurnToEntityBlock(-1));
				source.getMeleeSet().setAttacking(false);
				source.getMeleeSet().setUnderAttack(false);
				source.getMeleeSet().setInteractingCharacter(null);
				source.getBlockSet().add(SynchronizationBlock.createTurnToEntityBlock(-1));
				appendDeath(source, victim);
				return;
			}
			if (!source.getPosition().isWithinDistance(victim.getPosition(), 2)) {
				walkToVictim(source, victim, false);
				return;
			}
			source.turnTo(victim.getPosition());
			if (source.getMeleeSet().getAttackTimer() > 0) {
				source.getMeleeSet().setAttackTimer(source.getMeleeSet().getAttackTimer()-1);
			} else {
				if (source.getMeleeSet().isUsingSpecial()) {
					//appendSpecial(Character, victim);
				}
				int type = grabHitType(source);
				int damage = TextUtil.random(grabMaxHit(type, source, victim));
				boolean success = false;
				
				switch(type) {
					default:
						success = appendMelee(source, victim, damage);
						break;
					case RANGED:
						success = appendRange(source, victim, damage);
						break;
					case MAGIC:
						success = false;
						break;
				}

				if (success)
					victim.damage(damage);
				source.getMeleeSet().setAttackTimer(1);
				victim.getMeleeSet().setLastAttack(System.currentTimeMillis());
			}
		}
	}

	/**
	 * Appends a range hit to the victim.
	 * @param source The source.
	 * @param victim The victim.
	 * @param damage The damage.
	 * @return True if successfully hit, false if otherwise.
	 */
	private static boolean appendRange(Character source, Character victim, int damage) {
		int offsetX = (source.getPosition().getX() - victim.getPosition().getX()) * -1;
		int offsetY = (source.getPosition().getY() - victim.getPosition().getY()) * -1;
		// Some kind of range projectile, sniffed from a session.
		ProjectileEvent rangeProjectile = new ProjectileEvent(
			source.getPosition(),
			0,
			-victim.getIndex()-1,
			(byte) offsetX,
			(byte) offsetY,
			70,
			44,
			3,
			43,
			31,
			15
		);
		source.getRegion().sendEvent(rangeProjectile);
		source.playAnimation(new Animation(24));
		victim.playAnimation(new Animation(404));
		return true;
	}

	/**
	 * Appends the melee hit to the victim.
	 * @param source The source.
	 * @param victim The victim.
	 * @param damage The damage.
	 * @return True if successfully hit, false if otherwise.
	 */
	private static boolean appendMelee(Character source, Character victim, int damage) {
		source.playAnimation(new Animation(422));
		victim.playAnimation(new Animation(404));
		return true;
	}

	/**
	 * Creates the death amongst the player.
	 * @param source
	 * @param victim
	 */
	private static void appendDeath(final Character source, final Character victim) {
		if (victim instanceof Player)
			victim.sendMessage("Oh dear, you are dead!");
		
		victim.getMeleeSet().setDying(true);
		victim.playAnimation(new Animation(2304));

		final Inventory inventory = new Inventory(victim.getEquipment().size() + victim.getInventory().size());
		inventory.addAll(victim.getEquipment());
		inventory.addAll(victim.getInventory());
		
		final Inventory keep = CombatUtil.getItemsKeptOnDeath(3, inventory);

		victim.getInventory().stopFiringEvents();
		victim.getInventory().clear();
		victim.getEquipment().stopFiringEvents();
		victim.getEquipment().clear();

		inventory.removeAll(keep);
		inventory.shift();
		
		final Position position = victim.getPosition();

		World.getWorld().schedule(new ScheduledTask(4, false) {

			@Override
			public void execute() {
				if (victim instanceof Player)
					victim.teleport(SPAWN_POSITION);
				
				victim.addHealth(victim.getHealthMax());
				victim.stopAnimation();

				victim.getInventory().startFiringEvents();
				victim.getInventory().addAll(keep);
				victim.getInventory().forceRefresh();

				victim.getEquipment().startFiringEvents();
				victim.getEquipment().forceRefresh();

				if (victim instanceof Player && source instanceof Npc)
					for (Item item : inventory)
						World.getWorld().register(new GroundItem(((Player) victim).getName(), item, position));
				else if (source instanceof Player)
					for (Item item : inventory)
						World.getWorld().register(new GroundItem(((Player) source).getName(), item, position));
				else
					for (Item item : inventory)
						World.getWorld().register(new GroundItem(item, position));

				if (victim instanceof Npc) {
					World.getWorld().unregister((Npc) victim);
					World.getWorld().schedule(new ScheduledTask(300, false) {

						@Override
						public void execute() {
							World.getWorld().register((Npc) victim);
							stop();
						}

					});
				}

				victim.getMeleeSet().setDying(false);
				stop();
			}

		});
	}

	/**
	 * Calculates entity's max hit.
	 * @param type      The type of max hit to calculate.
	 * @param source	Entity to grab max hit for.
	 * @param victim	To have for calculating their defense bonus, etc.
	 * @return			Source's max hit.
	 */
	@SuppressWarnings("unused")
	private static int grabMaxHit(int type, Character source, Character victim) {
		double MaxHit = 0;
		switch(type) {
			default:
				int StrBonus = 1; // Strength Bonus
				int Strength = source.getSkillSet().getSkill(Skill.STRENGTH).getCurrentLevel(); // Strength
				int RngBonus = 1; // Ranged Bonus
				int Range = source.getSkillSet().getSkill(Skill.RANGED).getCurrentLevel(); // Ranged
				if (source instanceof Player) {
					StrBonus = (int) ((Player) source).getBonuses().getBonuses().getStrengthMelee();
					RngBonus = (int) ((Player) source).getBonuses().getBonuses().getAttackRange();
				}
				MaxHit += 1.05 + StrBonus * Strength * 0.00175;
				MaxHit += Strength * 0.1;
				break;
			case RANGED:
				double d1 = source.getSkillSet().getSkill(Skill.RANGED).getCurrentLevel();
				MaxHit += 1.399D + d1 * 0.00125D;
				MaxHit += d1 * 0.11D;
				Item item = source.getEquipment().get(EquipmentConstants.ARROWS);
				if (item != null) {
					switch(item.getId()) {
					case 882:
					case 883:
						MaxHit *= 1.042D;
						break;
					case 884:
					case 885:
						MaxHit *= 1.044D;
						break;
					case 886:
					case 887:
						MaxHit *= 1.1339999999999999D;
						break;
					case 888:
					case 889:
						MaxHit *= 1.2D;
						break;
					case 890:
					case 891:
						MaxHit *= 1.3500000000000001D;
						break;
					case 892:
					case 893:
						MaxHit *= 1.6000000000000001D;
						break;
					case 4740:
						MaxHit *= 1.95D;
						break;
					}
				}
				break;
		}
		return (int) Math.floor(MaxHit);
	}

	/**
	 * Gets character's attack type; melee, ranged or magic.
	 * @param character	Character to get attack type for.
	 * @return			Character's attack type.
	 */
	private static int grabHitType(Character character) {
		if (character instanceof Player) {
			Item str = ((Player) character).getEquipment().get(EquipmentConstants.WEAPON);
			if (str == null) {
				return MELEE;
			}
			String strn = str.getDefinition().getName();
			if (strn.contains("bow") || strn.contains("knive") || strn.contains("dart")) {
				return RANGED;
			}
			if (character.getMeleeSet().getMagicSpellId() > 0) {
				character.getMeleeSet().setUsingMagic(true);
				return MAGIC;
			}
		} else if (character instanceof Npc) {
			// return ((NPC)Character).getAttacktype().getId();
			return MELEE;
		}
		return MELEE;
	}

	/**
	 * Initiates an interaction between two entities.
	 * @param source	Initiating Character.
	 * @param victim	Character being attacked.
	 */
	private static void initiateInteraction(final Character source, final Character victim) {
		source.getMeleeSet().setInteractingCharacter(victim);
		source.getMeleeSet().setAttackTimer(0);
		if (victim.getMeleeSet().isAutoRetaliating()) {
			victim.getMeleeSet().setInteractingCharacter(source);
			victim.getMeleeSet().setAttackTimer(0);
		}
	}

	/**
	 * Handles an Character walking to their victim if distance is off.
	 * @param source		Character that will walk to victim.
	 * @param victim		Character being attacked.
	 * @param initAttack	Check if it's the first time attack is being initialized.
	 */
	private static void walkToVictim(final Character source, final Character victim, final boolean initAttack) {
		World.getWorld().schedule(new ScheduledTask(1, true) {
			@Override
			public void execute() {
				if (!source.getPosition().isWithinDistance(victim.getPosition(), 2)) {
					final int x = victim.getPosition().getX() - source.getPosition().getX();
					final int y = victim.getPosition().getY() - (source.getPosition().getY() + 1);
					final Position walkToPosition = source.getPosition().transform(x, y, 0);
					source.getWalkingQueue().walkTo(walkToPosition);
				} else {
					if (initAttack)
						attack(source, victim);
					stop();
				}
			}
		});
	}

	/**
	 * Attack type constants.
	 */
	@SuppressWarnings("unused")
	private static final int MELEE = 0, RANGED = 1, MAGIC = 2, CANNON = 3, RECOIL = 4, VENGEANCE = 5;
}