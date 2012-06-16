require 'java'
java_import 'org.apollo.game.action.DistancedAction'
java_import 'org.apollo.game.model.Animation'

STEAL_ANIMATION = Animation.new(0x340)

STALLS = {}

class Stall

 attr_reader :object, :id, :level, :exp

  def initialize(object, id, level, exp)
    @object = object
    @id = id
    @level = level
    @exp = exp
 end

end

class Steal < DistancedAction

attr_reader :started, :object, :player, :position, :counter

  def initialize(player, object, position)
    super 0, true, player, position, 1
    @object = object
    @player = player
    @position = position
    @started = false
    @counter = 0
  end

  def start_thieve
    @started = true
    player.turn_to @position
    player.send_message "You take from the stall.."
    player.play_animation STEAL_ANIMATION
    @counter = 1
  end
  
  def executeAction
    skills = character.skill_set
    level = skills.get_skill(Skill::THIEVING).maximum_level

    if object.level > level
      player.send_message "You do not have the required level to thieve this stall."
      stop_thieve
      return
    end

    if not started
      start_thieve
    else
      if rand(2) == 1
        if character.inventory.add object.id, 1
          item_def = ItemDefinition.for_id object.id
          name = item_def.name.sub(/ object$/, "").downcase
          character.send_message "and recieve #{name}."
          character.skill_set.add_experience Skill::THIEVING, object.exp
        else
          character.send_message "Full inventory."
        end
      else
        character.send_message "You fail to thieve the stall."
       end
    end
    stop
  end

  def equals(other)
    return (get_class == other.get_class and @position == other.position)
  end

end

def append_stall(log)
  STALLS[log.object] = log
end

on :event, :object_action do |ctx, player, event|
  if event.option == 2
    object = STALLS[event.id]
    if object != nil
      player.start_action Steal.new(player, object, event.position)
      ctx.break_handler_chain
    end
  end
end

append_stall Stall.new(2561, 1891, 5, 16) # Cake
