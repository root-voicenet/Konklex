require 'java'
java_import 'org.apollo.game.action.DistancedAction'
java_import 'org.apollo.game.model.EquipmentConstants'
java_import 'org.apollo.game.model.def.ItemDefinition'
java_import 'org.apollo.game.model.GameObject'
java_import 'org.apollo.game.model.World'
java_import 'org.apollo.game.model.def.ObjectDefinition'
java_import 'org.apollo.game.scheduling.ScheduledTask'

LOG_SIZE = 1

class WoodcuttingAction < DistancedAction

  attr_reader :position, :log, :started, :counter, :id

  def initialize(character, position, log, id)
    super 0, true, character, position, LOG_SIZE
    @position = position
    @log = log
    @started = false
    @id = id
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
    @counter = hatchet.pulses
    character.send_message "You swing your hatchet at the tree."
    character.turn_to position
    @started = true
  end

  # The chance of getting a log
  def get_success(hatchet, level)
    log_chance = 55.0
    lev_needed = log.level
    log_stop = hatchet.level
    multi_a = (log_stop - lev_needed)
    log_dec = (log_chance / multi_a)
    multi_b = (level - lev_needed)
    log_chance -= (multi_b * log_dec)
    randNum = SecureRandom.new().next_double * 100.0
    return log_chance <= randNum
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
      if counter == 0
        @counter = hatchet.pulses
        if get_success hatchet, level
          character.inventory.add log.id
          log_def = ItemDefinition.for_id log.id
          name = log_def.name.sub(/ log$/, "").downcase
          character.send_message "You manage to get some #{name}."
          skills.add_experience Skill::WOODCUTTING, log.exp
          if rand(4) == 1
            expirew position
            stop
            return
          end
        end
      else
        @counter -= 1
      end
    end

    if counter % 4 == 0 then character.play_animation hatchet.animation end
  end

  def stop
    character.stop_animation
    super
  end

  def expirew(position)
    log.objects.each do |obj, expired_obj|
      if obj == id
        ex_game_object = GameObject.new ObjectDefinition.for_id(expired_obj), position, 10, 1
        World.world.replace_object position, ex_game_object
        World.world.schedule ExpireLog.new(obj, position, log.respawn)
      end
    end
  end

  def equals(other)
    return (get_class == other.get_class and @position == other.position and @log == other.log)
  end

end

class ExpireLog < ScheduledTask

  attr_reader :log, :position

  def initialize(log, position, tick)
    super tick, false
    @log = log
    @position = position
  end

  def execute
    World.world.replace_object position, GameObject.new(ObjectDefinition.for_id(log), position, 10, 1)
    stop
  end

end

on :event, :object_action do |ctx, player, event|
  if event.option == 1
    log = LOGS[event.id]
    free = player.inventory.free_slots
    if log != nil
      player.startAction WoodcuttingAction.new(player, event.position, log, event.id)
      ctx.break_handler_chain
    end
  end
end
