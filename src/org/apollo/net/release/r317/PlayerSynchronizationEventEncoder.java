package org.apollo.net.release.r317;

import org.apollo.game.event.impl.DamageEvent;
import org.apollo.game.event.impl.PlayerSynchronizationEvent;
import org.apollo.game.model.Animation;
import org.apollo.game.model.Appearance;
import org.apollo.game.model.Direction;
import org.apollo.game.model.EquipmentConstants;
import org.apollo.game.model.Gender;
import org.apollo.game.model.Graphic;
import org.apollo.game.model.Inventory;
import org.apollo.game.model.Item;
import org.apollo.game.model.Position;
import org.apollo.game.model.def.EquipmentDefinition;
import org.apollo.game.model.inter.melee.MeleeConstants;
import org.apollo.game.sync.block.AnimationBlock;
import org.apollo.game.sync.block.AppearanceBlock;
import org.apollo.game.sync.block.ChatBlock;
import org.apollo.game.sync.block.ForceChatBlock;
import org.apollo.game.sync.block.ForceMovementBlock;
import org.apollo.game.sync.block.GraphicBlock;
import org.apollo.game.sync.block.HitUpdateBlock;
import org.apollo.game.sync.block.InteractingEntityBlock;
import org.apollo.game.sync.block.SecondHitUpdateBlock;
import org.apollo.game.sync.block.SynchronizationBlockSet;
import org.apollo.game.sync.block.TurnToPositionBlock;
import org.apollo.game.sync.seg.AddCharacterSegment;
import org.apollo.game.sync.seg.MovementSegment;
import org.apollo.game.sync.seg.SegmentType;
import org.apollo.game.sync.seg.SynchronizationSegment;
import org.apollo.game.sync.seg.TeleportSegment;
import org.apollo.net.codec.game.DataOrder;
import org.apollo.net.codec.game.DataTransformation;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketBuilder;
import org.apollo.net.meta.PacketType;
import org.apollo.net.release.EventEncoder;

/**
 * An {@link EventEncoder} for the {@link PlayerSynchronizationEvent}.
 * @author Graham
 */
public final class PlayerSynchronizationEventEncoder extends EventEncoder<PlayerSynchronizationEvent> {

	/*
	 * (non-Javadoc)
	 * @see org.apollo.net.release.EventEncoder#encode(org.apollo.game.event.Event)
	 */
	@Override
	public GamePacket encode(PlayerSynchronizationEvent event) {
		GamePacketBuilder builder = new GamePacketBuilder(81, PacketType.VARIABLE_SHORT);
		builder.switchToBitAccess();
		GamePacketBuilder blockBuilder = new GamePacketBuilder();
		putMovementUpdate(event.getSegment(), event, builder);
		putBlocks(event.getSegment(), blockBuilder);
		builder.putBits(8, event.getLocalPlayers());
		for (SynchronizationSegment segment : event.getSegments()) {
			SegmentType type = segment.getType();
			if (type == SegmentType.REMOVE_CHARACTER) {
				putRemoveCharacterUpdate(builder);
			} else {
				if (type == SegmentType.ADD_CHARACTER) {
					putAddCharacterUpdate((AddCharacterSegment) segment, event, builder);
				} else {
					putMovementUpdate(segment, event, builder);
				}
				putBlocks(segment, blockBuilder);
			}
		}
		if (blockBuilder.getLength() > 0) {
			builder.putBits(11, 2047);
			builder.switchToByteAccess();
			builder.putRawBuilder(blockBuilder);
		} else {
			builder.switchToByteAccess();
		}
		return builder.toGamePacket();
	}

	/**
	 * Puts an add character update.
	 * @param seg The segment.
	 * @param event The event.
	 * @param builder The builder.
	 */
	private void putAddCharacterUpdate(AddCharacterSegment seg, PlayerSynchronizationEvent event,
			GamePacketBuilder builder) {
		boolean updateRequired = seg.getBlockSet().size() > 0;
		Position player = event.getPosition();
		Position other = seg.getPosition();
		builder.putBits(11, seg.getIndex());
		builder.putBit(updateRequired);
		builder.putBits(1, 1); // discard walking queue?
		builder.putBits(5, other.getY() - player.getY());
		builder.putBits(5, other.getX() - player.getX());
	}

	/**
	 * Puts an animation block into the specified builder.
	 * @param block The block.
	 * @param blockBuilder The builder.
	 */
	private void putAnimationBlock(AnimationBlock block, GamePacketBuilder blockBuilder) {
		Animation animation = block.getAnimation();
		blockBuilder.put(DataType.SHORT, DataOrder.LITTLE, animation.getId());
		blockBuilder.put(DataType.BYTE, DataTransformation.NEGATE, animation.getDelay());
	}

	/**
	 * Puts an appearance block into the specified builder.
	 * @param block The block.
	 * @param blockBuilder The builder.
	 */
	private void putAppearanceBlock(AppearanceBlock block, GamePacketBuilder blockBuilder) {
		Appearance appearance = block.getAppearance();
		GamePacketBuilder playerProperties = new GamePacketBuilder();
		playerProperties.put(DataType.BYTE, appearance.getGender().toInteger()); // gender
		playerProperties.put(DataType.BYTE, -1); // head icon
		Inventory equipment = block.getEquipment();
		int[] style = appearance.getStyle();
		Item item, chest, helm, weapon;
		weapon = equipment.get(EquipmentConstants.WEAPON) != null ? equipment.get(EquipmentConstants.WEAPON)
				: new Item(0);
		for (int slot = 0; slot < 4; slot++) {
			if ((item = equipment.get(slot)) != null) {
				playerProperties.put(DataType.SHORT, 0x200 + item.getId());
			} else {
				playerProperties.put(DataType.BYTE, 0);
			}
		}
		if ((chest = equipment.get(EquipmentConstants.CHEST)) != null) {
			playerProperties.put(DataType.SHORT, 0x200 + chest.getId());
		} else {
			playerProperties.put(DataType.SHORT, 0x100 + style[2]);
		}
		if ((item = equipment.get(EquipmentConstants.SHIELD)) != null) {
			playerProperties.put(DataType.SHORT, 0x200 + item.getId());
		} else {
			playerProperties.put(DataType.BYTE, 0);
		}
		if (chest != null) {
			EquipmentDefinition def = EquipmentDefinition.forId(chest.getId());
			if (def != null && !def.isFullBody()) {
				playerProperties.put(DataType.SHORT, 0x100 + style[3]);
			} else {
				playerProperties.put(DataType.BYTE, 0);
			}
		} else {
			playerProperties.put(DataType.SHORT, 0x100 + style[3]);
		}
		if ((item = equipment.get(EquipmentConstants.LEGS)) != null) {
			playerProperties.put(DataType.SHORT, 0x200 + item.getId());
		} else {
			playerProperties.put(DataType.SHORT, 0x100 + style[5]);
		}
		if ((helm = equipment.get(EquipmentConstants.HAT)) != null) {
			EquipmentDefinition def = EquipmentDefinition.forId(helm.getId());
			if (def != null && !def.isFullHat() && !def.isFullMask()) {
				playerProperties.put(DataType.SHORT, 0x100 + style[0]);
			} else {
				playerProperties.put(DataType.BYTE, 0);
			}
		} else {
			playerProperties.put(DataType.SHORT, 0x100 + style[0]);
		}
		if ((item = equipment.get(EquipmentConstants.HANDS)) != null) {
			playerProperties.put(DataType.SHORT, 0x200 + item.getId());
		} else {
			playerProperties.put(DataType.SHORT, 0x100 + style[4]);
		}
		if ((item = equipment.get(EquipmentConstants.FEET)) != null) {
			playerProperties.put(DataType.SHORT, 0x200 + item.getId());
		} else {
			playerProperties.put(DataType.SHORT, 0x100 + style[6]);
		}
		EquipmentDefinition def = null;
		if (helm != null) {
			def = EquipmentDefinition.forId(helm.getId());
		}
		if ((def != null && (def.isFullHat() || def.isFullMask())) || appearance.getGender() == Gender.FEMALE) {
			playerProperties.put(DataType.BYTE, 0);
		} else {
			playerProperties.put(DataType.SHORT, 0x100 + style[1]);
		}
		int[] colors = appearance.getColors();
		for (int color : colors) {
			playerProperties.put(DataType.BYTE, color);
		}
		playerProperties.put(DataType.SHORT, MeleeConstants.getStandAnimation(weapon.getId())); // stand
		playerProperties.put(DataType.SHORT, 0x337); // stand turn
		playerProperties.put(DataType.SHORT, MeleeConstants.getWalkAnimation(weapon.getId())); // walk
		playerProperties.put(DataType.SHORT, 0x334); // turn 180
		playerProperties.put(DataType.SHORT, 0x335); // turn 90 cw
		playerProperties.put(DataType.SHORT, 0x336); // turn 90 ccw
		playerProperties.put(DataType.SHORT, MeleeConstants.getRunAnimation(weapon.getId())); // run
		playerProperties.put(DataType.LONG, block.getName());
		playerProperties.put(DataType.BYTE, block.getCombatLevel()); // combat level
		playerProperties.put(DataType.SHORT, block.getSkillLevel()); // total skill level
		blockBuilder.put(DataType.BYTE, DataTransformation.NEGATE, playerProperties.getLength());
		blockBuilder.putRawBuilder(playerProperties);
	}

	/**
	 * Puts the blocks for the specified segment.
	 * @param segment The segment.
	 * @param blockBuilder The block builder.
	 */
	private void putBlocks(SynchronizationSegment segment, GamePacketBuilder blockBuilder) {
		SynchronizationBlockSet blockSet = segment.getBlockSet();
		if (blockSet.size() > 0) {
			int mask = 0;
			if (blockSet.contains(ForceMovementBlock.class)) {
				mask |= 0x400;
			}
			if (blockSet.contains(GraphicBlock.class)) {
				mask |= 0x100;
			}
			if (blockSet.contains(AnimationBlock.class)) {
				mask |= 8;
			}
			if (blockSet.contains(ForceChatBlock.class)) {
				mask |= 4;
			}
			if (blockSet.contains(ChatBlock.class)) {
				mask |= 0x80;
			}
			if (blockSet.contains(InteractingEntityBlock.class)) {
				mask |= 1;
			}
			if (blockSet.contains(AppearanceBlock.class)) {
				mask |= 0x10;
			}
			if (blockSet.contains(TurnToPositionBlock.class)) {
				mask |= 2;
			}
			if (blockSet.contains(HitUpdateBlock.class)) {
				mask |= 0x20;
			}
			if (blockSet.contains(SecondHitUpdateBlock.class)) {
				mask |= 0x200;
			}
			if (mask >= 0x100) {
				mask |= 0x40;
				blockBuilder.put(DataType.SHORT, DataOrder.LITTLE, mask);
			} else {
				blockBuilder.put(DataType.BYTE, mask);
			}
			if (blockSet.contains(ForceMovementBlock.class)) {
				putForceMovementBlock(blockSet.get(ForceMovementBlock.class), blockBuilder);
			}
			if (blockSet.contains(GraphicBlock.class)) {
				putGraphicBlock(blockSet.get(GraphicBlock.class), blockBuilder);
			}
			if (blockSet.contains(AnimationBlock.class)) {
				putAnimationBlock(blockSet.get(AnimationBlock.class), blockBuilder);
			}
			if (blockSet.contains(ForceChatBlock.class)) {
				putForceChatBlock(blockSet.get(ForceChatBlock.class), blockBuilder);
			}
			if (blockSet.contains(ChatBlock.class)) {
				putChatBlock(blockSet.get(ChatBlock.class), blockBuilder);
			}
			if (blockSet.contains(InteractingEntityBlock.class)) {
				putInteractingEntityBlock(blockSet.get(InteractingEntityBlock.class), blockBuilder);
			}
			if (blockSet.contains(AppearanceBlock.class)) {
				putAppearanceBlock(blockSet.get(AppearanceBlock.class), blockBuilder);
			}
			if (blockSet.contains(TurnToPositionBlock.class)) {
				putTurnToPositionBlock(blockSet.get(TurnToPositionBlock.class), blockBuilder);
			}
			if (blockSet.contains(HitUpdateBlock.class)) {
				putHitUpdateBlock(blockSet.get(HitUpdateBlock.class), blockBuilder);
			}
			if (blockSet.contains(SecondHitUpdateBlock.class)) {
				putSecondHitUpdateBlock(blockSet.get(SecondHitUpdateBlock.class), blockBuilder);
			}
		}
	}

	/**
	 * Puts a chat block into the specified builder.
	 * @param block The block.
	 * @param blockBuilder The builder.
	 */
	private void putChatBlock(ChatBlock block, GamePacketBuilder blockBuilder) {
		byte[] bytes = block.getCompressedMessage();
		blockBuilder.put(DataType.SHORT, DataOrder.LITTLE, (block.getTextColor() << 8) | block.getTextEffects());
		blockBuilder.put(DataType.BYTE, block.getPrivilegeLevel().toInteger());
		blockBuilder.put(DataType.BYTE, DataTransformation.NEGATE, bytes.length);
		blockBuilder.putBytesReverse(bytes);
	}

	/**
	 * Puts a force chat block into the specified builder.
	 * @param block The block.
	 * @param blockBuilder The builder.
	 */
	private void putForceChatBlock(ForceChatBlock block, GamePacketBuilder blockBuilder) {
		blockBuilder.putString(block.getText());
	}

	/**
	 * Puts a force movement block into the specified builder.
	 * @param block The block.http://puu.sh/naj1
	 * @param blockBuilder The builder.
	 */
	private void putForceMovementBlock(ForceMovementBlock block, GamePacketBuilder blockBuilder) {
		Position current = block.getCurrentPosition();
		Position reachable = block.getPosition();
		blockBuilder.put(DataType.BYTE, DataTransformation.SUBTRACT, current.getLocalX());
		blockBuilder.put(DataType.BYTE, DataTransformation.SUBTRACT, current.getLocalY());
		blockBuilder.put(DataType.BYTE, DataTransformation.SUBTRACT, reachable.getLocalX());
		blockBuilder.put(DataType.BYTE, DataTransformation.SUBTRACT, reachable.getLocalY());
		blockBuilder.put(DataType.SHORT, DataOrder.LITTLE, DataTransformation.ADD, block.getFirstSpeed());
		blockBuilder.put(DataType.SHORT, DataTransformation.ADD, block.getSecondSpeed());
		blockBuilder.put(DataType.BYTE, DataTransformation.SUBTRACT, block.getDirection());
	}

	/**
	 * Puts a graphic block into the specified builder.
	 * @param block The block.
	 * @param blockBuilder The builder.
	 */
	private void putGraphicBlock(GraphicBlock block, GamePacketBuilder blockBuilder) {
		Graphic graphic = block.getGraphic();
		blockBuilder.put(DataType.SHORT, DataOrder.LITTLE, graphic.getId());
		blockBuilder.put(DataType.INT, (graphic.getHeight() << 16) | (graphic.getDelay() & 0xFFFF));
	}

	/**
	 * Puts a hit update block into the specified builder.
	 * @param block The block.
	 * @param blockBuilder The builder.
	 */
	private void putHitUpdateBlock(HitUpdateBlock block, GamePacketBuilder blockBuilder) {
		DamageEvent damage = block.getDamage();
		blockBuilder.put(DataType.BYTE, damage.getDamageDone());
		blockBuilder.put(DataType.BYTE, DataTransformation.ADD, damage.getHitType());
		blockBuilder.put(DataType.BYTE, DataTransformation.NEGATE, damage.getHp() - damage.getDamageDone());
		blockBuilder.put(DataType.BYTE, damage.getMaxHp());
	}

	/**
	 * Puts a interacting entity block into the specified builder.
	 * @param block The block.
	 * @param blockBuilder The builder.
	 */
	private void putInteractingEntityBlock(InteractingEntityBlock block, GamePacketBuilder blockBuilder) {
		blockBuilder.put(DataType.SHORT, DataOrder.LITTLE, block.getId());
	}

	/**
	 * Puts a movement update for the specified segment.
	 * @param seg The segment.
	 * @param event The event.
	 * @param builder The builder.
	 */
	private void putMovementUpdate(SynchronizationSegment seg, PlayerSynchronizationEvent event,
			GamePacketBuilder builder) {
		boolean updateRequired = seg.getBlockSet().size() > 0;
		if (seg.getType() == SegmentType.TELEPORT) {
			Position pos = ((TeleportSegment) seg).getDestination();
			builder.putBits(1, 1);
			builder.putBits(2, 3);
			builder.putBits(2, pos.getHeight());
			builder.putBit(event.hasRegionChanged());
			builder.putBit(updateRequired);
			builder.putBits(7, pos.getLocalY(event.getLastKnownRegion()));
			builder.putBits(7, pos.getLocalX(event.getLastKnownRegion()));
		} else if (seg.getType() == SegmentType.RUN) {
			Direction[] directions = ((MovementSegment) seg).getDirections();
			builder.putBits(1, 1);
			builder.putBits(2, 2);
			builder.putBits(3, directions[0].toInteger());
			builder.putBits(3, directions[1].toInteger());
			builder.putBit(updateRequired);
		} else if (seg.getType() == SegmentType.WALK) {
			Direction[] directions = ((MovementSegment) seg).getDirections();
			builder.putBits(1, 1);
			builder.putBits(2, 1);
			builder.putBits(3, directions[0].toInteger());
			builder.putBit(updateRequired);
		} else {
			if (updateRequired) {
				builder.putBits(1, 1);
				builder.putBits(2, 0);
			} else {
				builder.putBits(1, 0);
			}
		}
	}

	/**
	 * Puts a remove character update.
	 * @param builder The builder.
	 */
	private void putRemoveCharacterUpdate(GamePacketBuilder builder) {
		builder.putBits(1, 1);
		builder.putBits(2, 3);
	}

	/**
	 * Puts a hit update block into the specified builder.
	 * @param block The block.
	 * @param blockBuilder The builder.
	 */
	private void putSecondHitUpdateBlock(SecondHitUpdateBlock block, GamePacketBuilder blockBuilder) {
		DamageEvent damage = block.getDamage();
		blockBuilder.put(DataType.BYTE, damage.getDamageDone());
		blockBuilder.put(DataType.BYTE, DataTransformation.SUBTRACT, damage.getHitType());
		blockBuilder.put(DataType.BYTE, DataTransformation.NEGATE, damage.getHp() - damage.getDamageDone());
		blockBuilder.put(DataType.BYTE, damage.getMaxHp());
	}

	/**
	 * Puts a turn to position block into the specified builder.
	 * @param block The block.
	 * @param blockBuilder The builder.
	 */
	private void putTurnToPositionBlock(TurnToPositionBlock block, GamePacketBuilder blockBuilder) {
		Position pos = block.getPosition();
		blockBuilder.put(DataType.SHORT, DataOrder.LITTLE, DataTransformation.ADD, pos.getX() * 2 + 1);
		blockBuilder.put(DataType.SHORT, DataOrder.LITTLE, pos.getY() * 2 + 1);
	}
}
