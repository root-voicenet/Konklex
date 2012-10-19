package org.apollo.game.model.inter.melee;

import java.util.List;

import org.apollo.game.event.impl.ProjectileEvent;
import org.apollo.game.model.Animation;
import org.apollo.game.model.Character;
import org.apollo.game.model.EquipmentConstants;
import org.apollo.game.model.Graphic;
import org.apollo.game.model.GroundItem;
import org.apollo.game.model.Inventory;
import org.apollo.game.model.Item;
import org.apollo.game.model.Player;
import org.apollo.game.model.Position;
import org.apollo.game.model.Skill;
import org.apollo.game.model.World;
import org.apollo.game.model.inter.melee.MagicConstants.Mage;
import org.apollo.game.model.inter.melee.RangeConstants.Range;
import org.apollo.game.scheduling.ScheduledTask;
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
	 * Attack type constants.
	 */
	private static final int MELEE = 0, RANGED = 1, MAGIC = 2;

	/**
	 * Creates the death amongst the player.
	 * @param source The source.
	 * @param victim The victim.
	 */
	public static void appendDeath(final Character source, final Character victim) {
		if (victim.isControlling()) {
			victim.sendMessage("Oh dear, you are dead!");
		}

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
				if (victim.isControlling()) {
					victim.teleport(SPAWN_POSITION);
				}

				victim.addHealth(victim.getHealthMax());
				victim.stopAnimation();

				victim.getInventory().startFiringEvents();
				victim.getInventory().addAll(keep);
				victim.getInventory().forceRefresh();

				victim.getEquipment().startFiringEvents();
				victim.getEquipment().forceRefresh();

				if (source != null) {
					if (victim.isControlling() && !source.isControlling()) {
						for (Item item : inventory) {
							World.getWorld().register(new GroundItem(((Player) victim).getName(), item, position));
						}
					}
					else if (source.isControlling()) {
						for (Item item : inventory) {
							World.getWorld().register(new GroundItem(((Player) source).getName(), item, position));
						}
					}
					else {
						for (Item item : inventory) {
							World.getWorld().register(new GroundItem(item, position));
						}
					}
				}
				else {
					if (victim.isControlling()) {
						for (Item item : inventory) {
							World.getWorld().register(new GroundItem(((Player) victim).getName(), item, position));
						}
					}
					else {
						for (Item item : inventory) {
							World.getWorld().register(new GroundItem(item, position));
						}
					}
				}

				if (!victim.isControlling()) {
					// World.getWorld().unregister((Npc) victim);
					World.getWorld().schedule(new ScheduledTask(300, false) {

						@Override
						public void execute() {
							victim.getMeleeSet().setDying(false);
							// World.getWorld().register((Npc) victim);
							stop();
						}

					});
				}
				stop();
			}

		});
	}

	/**
	 * Appends a hit to the victim.
	 * @param type The type.
	 * @param source The source.
	 * @param victim The victim.
	 * @param damage The damage.
	 * @return True if hit was successful, false if not.
	 */
	private static boolean appendHit(int type, Character source, Character victim, int damage) {
		switch (type) {
		case RANGED:
			return appendRange(source, victim, damage);
		case MAGIC:
			return appendMagic(source, victim, damage);
		default:
			return appendMelee(source, victim, damage);
		}
	}

	/**
	 * Appends a mage hit to the victim.
	 * @param source The source.
	 * @param victim The victim.
	 * @param damage The damage.
	 * @return True if successfully hit, false if otherwise.
	 */
	private static boolean appendMagic(Character source, Character victim, int damage) {
		int offsetX = (source.getPosition().getX() - victim.getPosition().getX()) * -1;
		int offsetY = (source.getPosition().getY() - victim.getPosition().getY()) * -1;
		Mage spell = MagicConstants.Mage.forMagic(source.getMeleeSet().getMagicSpellId());
		if (spell != null) {
			/*
			 * for (Item item : spell.getItems()) { if (source.getInventory().contains(item)) {
			 * source.getInventory().remove(item); } else {
			 * source.sendMessage("You do not have enough runes to cast this spell!" ); return false; } }
			 */

			if (spell.getLevelReq() > source.getSkillSet().getSkill(Skill.MAGIC).getCurrentLevel()) {
				source.sendMessage("You do not have the required level to cast this spell!");
				return false;
			}

			source.playAnimation(spell.getAnimation());

			if (TextUtil.random(4) == 1) {
				victim.playGraphic(new Graphic(339, 0, 100));
				return false;
			}

			victim.playGraphic(spell.getGraphics().get(0));
			ProjectileEvent mageProjectile = new ProjectileEvent(source.getPosition(), 0,
					victim.isControlling() ? -victim.getIndex() - 1 : victim.getIndex() + 1, (byte) offsetX,
							(byte) offsetY, spell.getProjectile(), 51, 90, 43, 31, 16);
			source.getRegion().sendEvent(mageProjectile);
			victim.playGraphic(spell.getGraphics().get(1));

			String name = spell.name().toLowerCase();
			if (name.contains("ice")) {
				if (name.contains("rush")) {
					victim.getWalkingQueue().stop(12);
				}
				else if (name.contains("burst")) {
					victim.getWalkingQueue().stop(25);
				}
				else if (name.contains("blitz")) {
					victim.getWalkingQueue().stop(37);
				}
				else if (name.contains("barrage")) {
					victim.getWalkingQueue().stop(50);
				}
			}
			else if (name.contains("blood")) {
				if (TextUtil.random(2) == 1) {
					source.addHealth(damage / 4);
				}
			}
			else if (name.contains("smoke")) {
				victim.getMeleeSet().setPoison(5);
			}
			else if (name.contains("shadow")) {
				Skill skill = victim.getSkillSet().getSkill(Skill.ATTACK);
				new Skill(skill.getExperience(), skill.getCurrentLevel() - TextUtil.random(2), skill.getMaximumLevel());
				victim.getSkillSet().setSkill(Skill.ATTACK, skill);
			}

			// Multi barrage
			if (name.contains("barrage") || name.contains("burst")) {
				int rand = TextUtil.random(3);
				if (rand == 1) {
					List<Character> characters = victim.getRegion().getCharacters();
					int hit = TextUtil.random(16);
					for (Character character : characters) {
						if (!character.equals(source) && !character.equals(victim)) {
							if (character.getPosition().isWithinDistance(victim.getPosition(), 1)) {
								if (hit > (spell.name().toLowerCase().contains("barrage") ? 18 : 9)) {
									break;
								}
								offsetX = (source.getPosition().getX() - character.getPosition().getX()) * -1;
								offsetY = (source.getPosition().getY() - character.getPosition().getY()) * -1;
								if (name.contains("ice")) {
									if (name.contains("rush")) {
										victim.getWalkingQueue().stop(12); // 5 = 3 seconds
									}
									else if (name.contains("burst")) {
										victim.getWalkingQueue().stop(25);
									}
									else if (name.contains("blitz")) {
										victim.getWalkingQueue().stop(37);
									}
									else if (name.contains("barrage")) {
										victim.getWalkingQueue().stop(50);
									}
								}
								else if (name.contains("smoke")) {
									victim.getMeleeSet().setPoison(5);
								}
								else if (name.contains("shadow")) {
									Skill skill = victim.getSkillSet().getSkill(Skill.ATTACK);
									new Skill(skill.getExperience(), skill.getCurrentLevel() - TextUtil.random(2),
											skill.getMaximumLevel());
									victim.getSkillSet().setSkill(Skill.ATTACK, skill);
								}
								character.playGraphic(spell.getGraphics().get(0));
								ProjectileEvent projectile = new ProjectileEvent(source.getPosition(), 0,
										character.isControlling() ? -character.getIndex() - 1
												: character.getIndex() + 1, (byte) offsetX, (byte) offsetY,
												spell.getProjectile(), 51, 90, 43, 31, 16);
								source.getRegion().sendEvent(projectile);
								character.playGraphic(spell.getGraphics().get(1));
								character.getMeleeSet().damage(TextUtil.random(damage));
								hit++;
							}
						}
					}
				}
			}
			return true;
		}
		return false;
	}

	/**
	 * Appends the melee hit to the victim.
	 * @param source The source.
	 * @param victim The victim.
	 * @param damage The damage.
	 * @return True if successfully hit, false if otherwise.
	 */
	private static boolean appendMelee(Character source, Character victim, int damage) {
		source.playAnimation(new Animation(MeleeConstants.getAttackAnim(source)));
		victim.playAnimation(new Animation(404));
		return true;
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
		Item item = source.getEquipment().get(EquipmentConstants.ARROWS);
		Item bow = source.getEquipment().get(EquipmentConstants.WEAPON);
		boolean knife = bow != null && bow.getDefinition().getName().toLowerCase().contains("knife") ? true : false;
		boolean crystal = bow != null && bow.getDefinition().getName().toLowerCase().contains("crystal") ? true : false;
		if (item != null || knife || crystal) {
			if (crystal || knife && bow.getAmount() > 0 || item != null && item.getAmount() > 0) {
				Range range = RangeConstants.Range.forArrow(knife ? bow.getId() : crystal ? 78 : item.getId());
				if (range != null) {
					if (source.getMeleeSet().isUsingSpecial() && bow != null && bow.getId() == 861) {
						Range tmp = Range.ICE;
						source.playGraphic(tmp.getDrawback());
						ProjectileEvent rangeProjectile = new ProjectileEvent(source.getPosition(), 0,
								victim.isControlling() ? -victim.getIndex() - 1 : victim.getIndex() + 1,
										(byte) offsetX, (byte) offsetY, tmp.getProjectile(), 51, // Delay, Default:
										// 51
										70, // Duration, Default: 70
										43, 31, 16);
						victim.getRegion().sendEvent(rangeProjectile);
					}
					else {
						source.playGraphic(range.getDrawback());
						ProjectileEvent rangeProjectile = new ProjectileEvent(source.getPosition(), 0,
								victim.isControlling() ? -victim.getIndex() - 1 : victim.getIndex() + 1,
										(byte) offsetX, (byte) offsetY, range.getProjectile(), 51, // Delay, Default:
										// 51
										70, // Duration, Default: 70
										43, 31, 16);
						victim.getRegion().sendEvent(rangeProjectile);
					}
					if (knife) {
						source.getEquipment()
						.set(EquipmentConstants.WEAPON, new Item(bow.getId(), bow.getAmount() - 1));
					}
					else if (!crystal) {
						source.getEquipment().set(EquipmentConstants.ARROWS,
								new Item(item.getId(), item.getAmount() - 1));
					}
					if (TextUtil.random(2) == 1 && !crystal) {
						if (source.isControlling()) {
							Item cape = source.getEquipment().get(EquipmentConstants.CAPE);
							boolean special = cape != null && cape.getId() == 1052;
							String name = ((Player) source).getName();
							if (special) {
								if (!knife) {
									source.getInventory().add(item.getId(), 1);
								}
								else {
									source.getInventory().add(bow.getId(), 1);
								}

								source.playAnimation(new Animation(MeleeConstants.getAttackAnim(source)));
								return true;
							}
							if (!knife) {
								World.getWorld().register(
										new GroundItem(name, new Item(item.getId(), 1), victim.getPosition()));
							}
							else {
								World.getWorld().register(
										new GroundItem(name, new Item(bow.getId(), 1), victim.getPosition()));
							}
						}
						else {
							if (!knife) {
								World.getWorld().register(
										new GroundItem(new Item(item.getId(), 1), victim.getPosition()));
							}
							else {
								World.getWorld().register(
										new GroundItem(new Item(bow.getId(), 1), victim.getPosition()));
							}
						}
					}
				}
				else
					return false;
			}
			else {
				if (source.isControlling()) {
					source.sendMessage("There is no more ammo left in your quiver!");
				}
				return false;
			}
		}
		else
			return false;
		source.playAnimation(new Animation(MeleeConstants.getAttackAnim(source)));
		return true;
	}

	/**
	 * Appends a special effect to a hit.
	 * @param type The type of hit.
	 * @param source The source.
	 * @param victim The victim.
	 * @param damage The damage done.
	 * @param damage The second (not required) damage.
	 */
	private static void appendSpecial(int type, Character source, final Character victim, int damage) {
		Item weapon = source.getEquipment().get(EquipmentConstants.WEAPON);
		int drain = 0;
		int special = source.getMeleeSet().getSpecial();
		if (weapon != null) {
			switch (weapon.getId()) {
			case 1305:
				drain = 25;
				if (special >= drain) {
					source.playAnimation(new Animation(1058));
					source.playGraphic(new Graphic(248, 0, 100));
					victim.playGraphic(new Graphic(254, 0, 100));
					source.getMeleeSet().setSpecialBar(15);
				}
				break;
			case 1304:
				drain = 30;
				if (special >= drain) {
					source.playAnimation(new Animation(1203));
					source.playGraphic(new Graphic(285, 0, 100));
				}
				break;
			case 4151:
				drain = 50;
				if (special >= drain) {
					source.playAnimation(new Animation(1658));
					victim.playGraphic(new Graphic(341, 0, 100));
				}
				break;
			case 1249:
				drain = 25;
				if (special >= drain) {
					source.playAnimation(new Animation(405));
					victim.playGraphic(new Graphic(254, 0, 100));
				}
				break;
			case 4577:
				drain = 55;
				if (special >= drain) {
					source.playAnimation(new Animation(1872));
					source.playGraphic(new Graphic(347, 0, 100));
				}
				break;
			case 1434:
				drain = 25;
				if (special >= drain) {
					source.playAnimation(new Animation(1060));
					source.playGraphic(new Graphic(251, 0, 100));
				}
				break;
			case 5698:
				drain = 25;
				if (special >= drain) {
					source.playAnimation(new Animation(1062, 0));
					source.playGraphic(new Graphic(252, 0, 100));
					if (TextUtil.random(2) == 1) {
						victim.getMeleeSet().setPoison(5); // Ticks
					}
					victim.getMeleeSet().damage2(damage / 2);
				}
				break;
			case 4153:
				drain = 50;
				if (special >= drain) {
					source.playAnimation(new Animation(1677));
					source.playGraphic(new Graphic(340, 0, 100));
				}
				break;
			case 1377:
				drain = 100;
				if (special >= drain) {
					source.playAnimation(new Animation(1056));
					source.playGraphic(new Graphic(256, 0, 100));
				}
				break;
			case 861:
				drain = 60;
				if (special >= drain) {
					int offsetX = (source.getPosition().getX() - victim.getPosition().getX()) * -1;
					int offsetY = (source.getPosition().getY() - victim.getPosition().getY()) * -1;
					ProjectileEvent rangeProjectile = new ProjectileEvent(source.getPosition(), 0,
							victim.isControlling() ? -victim.getIndex() - 1 : victim.getIndex() + 1, (byte) offsetX,
									(byte) offsetY, 249, 73, // Delay, Default:
									// 51
									92, // Duration, Default: 70
									43, 31, 16);
					source.getRegion().sendEvent(rangeProjectile);
					source.playAnimation(new Animation(1074));
					source.playGraphic(new Graphic(256, 0, 100));
					victim.getMeleeSet().damage2(damage / 2);
				}
				break;
			}
			if (special >= drain) {
				damage += TextUtil.random(damage / 2);
				source.getMeleeSet().setSpecial(source.getMeleeSet().getSpecial() - drain);
				source.getMeleeSet().setUsingSpecial(false);
			}
		}
	}

	/**
	 * Handles the attacking packet.
	 * @param source Character that initiates combat.
	 * @param victim Character that is being attacked.
	 */
	public static void attack(Character source, Character victim) {
		if (source == null || victim == null || source.getHealth() <= 0 || victim.getHealth() <= 0)
			return;
		if (source.getMeleeSet().isUnderAttack()) {
			if (source.isControlling()) {
				((Player) source).sendMessage("You are already in combat!");
			}
			return;
		}
		if (victim.getMeleeSet().isUnderAttack()) {
			if (!victim.getMeleeSet().getInteractingCharacter().equals(source)) {
				if (source.isControlling()) {
					((Player) source).sendMessage("This " + (victim.isControlling() ? "player" : "npc")
							+ " is already in combat!");
				}
			}
			return;
		}
		int type = grabHitType(source);
		if (type != MELEE || source.getPosition().isWithinDistance(victim.getPosition(), 2)) {
			if (type == RANGED || type == MAGIC) {
				source.getWalkingQueue().clear();
			}
			initiateInteraction(source, victim);
		}
		else {
			walkToVictim(source, victim, true);
		}
	}

	/**
	 * Gets character's attack type; melee, ranged or magic.
	 * @param character Character to get attack type for.
	 * @return Character's attack type.
	 */
	private static int grabHitType(Character character) {
		if (character.isControlling()) {
			if (character.getMeleeSet().getMagicSpellId() > 0) {
				character.getMeleeSet().setUsingMagic(true);
				return MAGIC;
			}
			Item str = ((Player) character).getEquipment().get(EquipmentConstants.WEAPON);
			if (str == null)
				return MELEE;
			String strn = str.getDefinition().getName();
			if (strn.contains("bow") || strn.contains("knife"))
				return RANGED;
		}
		else if (!character.isControlling())
			return MELEE; // every character is melee for now
		return MELEE;
	}

	/**
	 * Calculates entity's max hit.
	 * @param type The type of max hit to calculate.
	 * @param source Entity to grab max hit for.
	 * @param victim To have for calculating their defense bonus, etc.
	 * @return Source's max hit.
	 */
	@SuppressWarnings("unused")
	private static int grabMaxHit(int type, Character source, Character victim) {
		double MaxHit = 0;
		switch (type) {
		case MAGIC:
			int MgBonus = 1; // Magic Bonus
			int Magic = source.getSkillSet().getSkill(Skill.MAGIC).getCurrentLevel(); // Magic
			if (source.isControlling()) {
				MgBonus = (int) ((Player) source).getBonuses().getBonuses().getMagic();
			}
			MaxHit += 1.05 + MgBonus * Magic * 0.00175;
			MaxHit += Magic * 0.1;
			break;
		case RANGED:
			double d1 = source.getSkillSet().getSkill(Skill.RANGED).getCurrentLevel();
			MaxHit += 1.399D + d1 * 0.00125D;
			MaxHit += d1 * 0.11D;
			Item item = source.getEquipment().get(EquipmentConstants.ARROWS);
			if (item != null) {
				switch (item.getId()) {
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
		default:
			int StrBonus = 1; // Strength Bonus
			int Strength = source.getSkillSet().getSkill(Skill.STRENGTH).getCurrentLevel(); // Strength
			int RngBonus = 1; // Ranged Bonus
			int Range = source.getSkillSet().getSkill(Skill.RANGED).getCurrentLevel(); // Ranged
			if (source.isControlling()) {
				StrBonus = (int) ((Player) source).getBonuses().getBonuses().getStrengthMelee();
				RngBonus = (int) ((Player) source).getBonuses().getBonuses().getAttackRange();
			}
			MaxHit += 1.05 + StrBonus * Strength * 0.00175;
			MaxHit += Strength * 0.1;
			break;
		}
		return (int) Math.floor(MaxHit);
	}

	/**
	 * Initiates an interaction between two entities.
	 * @param source Initiating Character.
	 * @param victim Character being attacked.
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
	 * Combat process, used to check if player is under attack, lower attack timer, walk to the Character if not in
	 * distance, etc.
	 * @param source Character to process.
	 */
	public static void process(Character source) {
		// TODO Service this maybe?
		// Checks
		int poison = source.getMeleeSet().getPoison();
		if (poison > 0 && System.currentTimeMillis() - source.getMeleeSet().getLastPoison() >= 10000) {
			source.getMeleeSet().poison(TextUtil.random(3));
			source.getMeleeSet().setPoison(poison - 1);
		}
		// Melee process
		if (source.getMeleeSet().getInteractingCharacter() != null && source.getMeleeSet().isUnderAttack()
				&& System.currentTimeMillis() - source.getMeleeSet().getLastAttack() >= 10000) {
			if (!source.getMeleeSet().isAttacking()) {
				source.getMeleeSet().setInteractingCharacter(null);
			}
			source.getMeleeSet().setUnderAttack(false);
		}
		if (source.getMeleeSet().getInteractingCharacter() != null) {
			Character victim = source.getMeleeSet().getInteractingCharacter();
			if (victim.getHealth() <= 0 || victim.getMeleeSet().isDying()) {
				source.resetMeleeSet();
			}
			final int type = grabHitType(source);
			if (!source.getPosition().isWithinDistance(victim.getPosition(), type == MAGIC || type == RANGED ? 16 : 1)) {
				walkToVictim(source, victim, false);
				return;
			}
			source.turnTo(victim.getPosition());
			int time = source.getMeleeSet().getAttackTimer();
			if (time > 0) {
				source.getMeleeSet().setAttackTimer(time - 1);
			}
			else {
				int damage = TextUtil.random(grabMaxHit(type, source, victim));
				if (appendHit(type, source, victim, damage)) {
					if (source.getMeleeSet().isUsingSpecial()) {
						appendSpecial(type, source, victim, damage);
					}
					victim.getMeleeSet().setUnderAttack(true);
					victim.getMeleeSet().damage(damage);
				}
				source.getMeleeSet().setAttackTimer(type != MAGIC ? 4 : 6);
				victim.getMeleeSet().setLastAttack(System.currentTimeMillis());
			}
		}
	}

	/**
	 * Handles an Character walking to their victim if distance is off.
	 * @param source Character that will walk to victim.
	 * @param victim Character being attacked.
	 * @param initAttack Check if it's the first time attack is being initialized.
	 */
	private static void walkToVictim(final Character source, final Character victim, final boolean initAttack) {
		World.getWorld().schedule(new ScheduledTask(0, true) {
			@Override
			public void execute() {
				if (!source.getPosition().isWithinDistance(victim.getPosition(), 1)) {
					final int x = victim.getPosition().getX() - source.getPosition().getX();
					final int y = victim.getPosition().getY() - (source.getPosition().getY() + 1);
					final Position walkToPosition = source.getPosition().transform(x, y, 0);
					source.getWalkingQueue().walkTo(walkToPosition);
				}
				else {
					if (initAttack) {
						attack(source, victim);
					}
					stop();
				}
			}
		});
	}
}