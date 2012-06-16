require 'java'
java_import 'org.apollo.game.action.DistancedAction'
java_import 'org.apollo.game.model.Animation'

class FishingAction < DistancedAction
  attr_reader :fish, :fished, :started, :position

  def initialize(player, fish, position)
    super 10, true, player, position, 1
    @fish = fish
    @fished = 0
    @started = false
    @position = position
  end

  def execute
    skills = character.skill_set
    level = skills.skill(Skill::FISHING).maximum_level # TODO: is using max level correct?
    button = fish.option

    # verify level requirements
    if button.level > level
      character.send_message "You need a Fishing level of at least #{button.level} to fish this."
      stop
      return
    end

    # verify item requirements
    if not (character.inventory.contains fish.equipment)
      character.send_message "You do not have the required equipment to fish this."
      stop
      return
    end

    # verify item requirements
    if not (character.inventory.contains fish.bait)
      character.send_message "You do not have the required bait to fish this."
      stop
      return
    end

    if not started
      character.send_message "You attempt to catch a fish.."
      @started = true
    end

    # start fishing fella's
    character.play_animation Animation.new(fish.animation)
    character.turn_to position
    if rand(10) == 1
      character.send_message "You catch a fish."
      character.inventory.remove fish.bait
      character.inventory.add button.fish
      skills.add_experience Skill::FISHING, button.exp
      if rand(15) == 1
        stop
        return
      else
        @fished += 1
      end
    end

  end

  def stop
    character.stop_animation
    super
  end

  def equals(other)
    return (get_class == other.get_class and @item == other.item and @log == other.log)
  end

end

on :event, :npc_option do |ctx, player, event|
  fish = FISH[event.get_npc.get_id]
  if fish != nil and fish[event.get_option] != nil
    player.start_action FishingAction.new(player, fish[event.get_option], event.get_npc.get_position)
  end
  player.send_message "Option: #{event.get_option}"
end