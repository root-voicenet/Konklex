require 'java'
java_import 'org.apollo.game.action.DistancedAction'
java_import 'org.apollo.game.model.EquipmentConstants'
java_import 'org.apollo.game.model.def.ItemDefinition'
java_import 'org.apollo.game.model.GameObject'
java_import 'org.apollo.game.model.World'
java_import 'org.apollo.game.model.def.ObjectDefinition'
java_import 'org.apollo.game.scheduling.ScheduledTask'

PROSPECT_PULSES = 3
ORE_SIZE = 1

# TODO: finish implementing this
class MiningAction < DistancedAction

  attr_reader :position, :ore, :started, :counter, :id

  def initialize(character, position, ore, id)
    super 0, true, character, position, ORE_SIZE
    @position = position
    @ore = ore
    @started = false
    @id = id
  end

  def find_pickaxe
    PICKAXE_IDS.each do |id|
      weapon = character.equipment.get EquipmentConstants::WEAPON
      if weapon.id == id
        return PICKAXES[id]
      end

      if character.inventory.contains id
        return PICKAXES[id]
      end
    end

    return nil
  end

  def get_mining_success(ore_level, level)
    level = level * 2
    required = ore_level
    second = required / 2
    randNum = rand(second)
    first = ((randNum * level) / 3)
    return first > second
  end

  # starts the mining animation, sets counters/flags and turns the character to
  # the ore
  def start_mine(pickaxe)
    @started = true
    character.send_message "You swing your pick at the rock."
    character.turn_to position
    character.play_animation pickaxe.animation
    @counter = pickaxe.pulses
  end

  def executeAction
    skills = character.skill_set
    level = skills.get_skill(Skill::MINING).maximum_level # TODO: is using max level correct?
    free = character.inventory.free_slots

    # Looks for free slots
    if free < 1
      character.send_message "Not enough inventory space"
      stop
      return
    end

    pickaxe = find_pickaxe

    # verify the player can mine with their pickaxe
    if not (pickaxe != nil and level >= pickaxe.level)
      character.send_message "You do not have a pickaxe for which you have the level to use."
      stop
      return
    end

    # verify the player can mine the ore
    if ore.level > level
      character.send_message "You do not have the required level to mine this rock."
      stop
      return
    end

    # check if we need to kick start things
    if not started
      start_mine(pickaxe)
    else
      if counter == 0
        character.play_animation pickaxe.animation
        @counter = pickaxe.pulses
        if get_mining_success ore.level, level
          character.inventory.add ore.id
          ore_def = ItemDefinition.for_id @ore.id # TODO: split off into some method
          name = ore_def.name.sub(/ ore$/, "").downcase
          character.send_message "You manage to mine some #{name}."
          skills.add_experience Skill::MINING, ore.exp
          expire position
          stop
        else
          @counter = counter / 2
        end
      else
        @counter -= 1
      end
    end

  end

  def stop
    character.stop_animation
    super
  end

  def expire(position)
    ore.objects.each do |obj, expired_obj|
      if obj == id
        ex_game_object = GameObject.new ObjectDefinition.for_id(expired_obj), position, 10, 1
        World.world.replace_object position, ex_game_object
        World.world.schedule ExpireOre.new(obj, position, ore.respawn)
      end
    end
  end

  def equals(other)
    return (get_class == other.get_class and @position == other.position and @ore == other.ore)
  end

end

class ExpireOre < ScheduledTask

  attr_reader :ore, :position

  def initialize(ore, position, tick)
    super tick, false
    @ore = ore
    @position = position
  end

  def execute
    World.world.replace_object position, GameObject.new(ObjectDefinition.for_id(ore), position, 10, 1)
    stop
  end

end

class ExpiredProspectingAction < DistancedAction
  attr_reader :position

  def initialize(character, position)
    super 0, true, character, position, ORE_SIZE
  end

  def executeAction
    character.send_message "There is currently no ore available in this rock."
    stop
  end

  def equals(other)
    return (get_class == other.get_class and @position == other.position)
  end

end

class ProspectingAction < DistancedAction
  attr_reader :position, :ore

  def initialize(character, position, ore)
    super PROSPECT_PULSES, true, character, position, ORE_SIZE
    @position = position
    @ore = ore
    @started = false
  end

  def executeAction
    if not @started
      @started = true

      character.send_message "You examine the rock for ores..."
      character.turn_to @position
    else
      ore_def = ItemDefinition.for_id @ore.id
      name = ore_def.name.sub(/ ore$/, "").downcase

      character.send_message "This rock contains #{name}."

      stop
    end
  end

  def equals(other)
    return (get_class == other.get_class and @position == other.position and @ore == other.ore)
  end
end

on :event, :object_action do |ctx, player, event|
  if event.option == 1
    ore = ORES[event.id]
    if ore != nil
      player.startAction MiningAction.new(player, event.position, ore, event.id)
    end
  end
end

on :event, :object_action do |ctx, player, event|
  if event.option == 2
    ore = ORES[event.id]
    if ore != nil
      player.startAction ProspectingAction.new(player, event.position, ore)
    else
      if EXPIRED_ORES[event.id] != nil
        player.startAction ExpiredProspectingAction.new(player, event.position)
      end
    end
  end
end
