require 'java'
java_import 'org.apollo.game.action.DistancedAction'
java_import 'org.apollo.game.model.EquipmentConstants'
java_import 'org.apollo.game.model.def.ItemDefinition'

LOG_SIZE = 1

class WoodcuttingAction < DistancedAction

  attr_reader :position, :log, :started

  def initialize(character, position, log)
    super 3, true, character, position, LOG_SIZE
    @position = position
    @log = log
    @started = false
  end

  # Finds if you have the hatchet
  def find_hatchet
    HATCHET_ID.each do |id|
      if character.equipment.contains id
        return HATCHET[id]
      elsif character.inventory.contains id
        return HATCHET[id]
      end
    end
    return nil
  end

  # The Chopping action/animation/message
  def start_chopping(hatchet)
    character.send_message "You swing your hatchet at the tree."
    character.turn_to @position
    character.play_animation hatchet.animation
    @counter = hatchet.pulses
    @started = true
  end

  def executeAction
    skills = character.skill_set
    level = skills.get_skill(Skill::WOODCUTTING).maximum_level # TODO: is using max level correct?
    free = character.inventory.free_slots

    # Looks for free slots
    if free < 1
      character.send_message "Not enough inventory space"
      stop
      return
    end

    hatchet = find_hatchet

    # verify the player has a hatchet
    if hatchet == nil
      character.send_message "You do not have a hatchet to cut this tree with."
      stop
      return
    end

    # verify the player can chop with their axe
    if not (level >= hatchet.level)
      character.send_message "You do not have the correct hatchet with your woodcutting level."
      stop
      return
    end

    # verify the player can chop the tree
    if log.level > level
      character.send_message "You do not have the required level to cut this tree."
      stop
      return
    end
  
    if not started
      start_chopping(hatchet)
    else
      character.play_animation hatchet.animation
      if rand(4) == 1
        character.inventory.add log.id
	log_def = ItemDefinition.for_id log.id
        name = log_def.name.sub(/ log$/, "").downcase
        character.send_message "You manage to get some #{name}."
        skills.add_experience Skill::WOODCUTTING, log.exp
        if rand(10) == 1
          stop
          return
        end
      end
    end
  end

  def stop
    character.stop_animation
    super
  end

  def equals(other)
    return (get_class == other.get_class and @position == other.position and @log == other.log)
  end

end

class ExpiredCuttingAction < DistancedAction
  
  attr_reader :position

  def initialize(character, position)
    super 0, true, character, position, LOG_SIZE
  end

  def equals(other)
    return (get_class == other.get_class and @position == other.position)
  end

end

on :event, :object_action do |ctx, player, event|
  if event.option == 1
    log = LOGS[event.id]
    free = player.inventory.freeSlots
    if log != nil
      player.startAction WoodcuttingAction.new(player, event.position, log)
      ctx.break_handler_chain
    end
  end
end
