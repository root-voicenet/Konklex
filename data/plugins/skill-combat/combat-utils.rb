require 'java'
java_import 'org.apollo.game.model.EquipmentConstants'
java_import 'org.apollo.game.model.Item'

NPC_ATTACK_ANIMATIONS = {}
NPC_DEATH_ANIMATIONS = {}
PLAYER_ATTACK_ANIMATIONS = {}
PLAYER_WEAPON_DELAYS = {}
DEFAULT_ATTACK_ANIMATION = Animation.new(423)
DEFAULT_DEATH_ANIMATION = Animation.new(2304)

class Combat
  
  def self.isFighting(character)
    return character.get_melee_set.get_attacker != nil
  end
  
  def self.canFight(character)
    return character.get_melee_set.get_attacker == nil
  end
  
  def self.addTimer(character, event, time)
    Timer.new character, event, time
  end
  
  def self.addWeaponDelay(weapon, delay)
    PLAYER_WEAPON_DELAYS[weapon] = delay
  end
  
  def self.getWeaponDelay(weapon)
    delay = PLAYER_WEAPON_DELAYS[weapon]
    return delay != nil ? delay : 3
  end
  
  def self.addNpcAttackAnimation(npc, animation)
    NPC_ATTACK_ANIMATIONS[npc] = animation
  end
  
  def self.addNpcDeathAnimation(npc, animation)
    NPC_DEATH_ANIMATIONS[npc] = animation
  end
  
  def self.addPlayerAnimation(weapon, animation)
    PLAYER_ATTACK_ANIMATIONS[weapon] = animation
  end
  
  def self.getNpcAttackAnimation(npc)
    animation = NPC_ATTACK_ANIMATIONS[npc]
    return animation != nil ? Animation.new(animation) : DEFAULT_ATTACK_ANIMATION
  end
  
  def self.getNpcDeathAnimation(npc)
    animation = NPC_DEATH_ANIMATIONS[npc]
    return animation != nil ? Animation.new(animation) : DEFAULT_DEATH_ANIMATION
  end
  
  def self.getPlayerAnimation(weapon)
    animation = PLAYER_ATTACK_ANIMATIONS[weapon]
    return animation != nil ? Animation.new(animation) : DEFAULT_ATTACK_ANIMATION
  end
  
  # Entity1 lost, Entity2 won.
  def self.stopAction(entity1, entity2)
    Death.new entity1, entity2
  end
  
  def self.getWeapon(character)
    equipment = character.get_equipment
    weapon = equipment.get(EquipmentConstants::WEAPON) != nil ? equipment.get(EquipmentConstants::WEAPON) : Item.new(1)
    return weapon.get_id
  end

  # a canceled the fight
  def self.cancelFight(a, b)
    if a != nil
      a.get_melee_set.set_attacker nil
    end
    if b != nil
      b.get_melee_set.set_attacker nil
    end
  end
  
end

class Timer < ScheduledTask
  
  attr_reader :character, :event
  
  def initialize(character, event, time)
    super time, false
    @character = character
    @event = event
    World.get_world.schedule self
  end
  
  def execute
    character.start_action event
    stop
  end
  
end