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
    want = get_fish(fish, level)

    # verify level requirements
    if want == -1 or fish.level[want] > level
      character.send_message "You need a Fishing level of at least #{fish.level[want]} to fish this."
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
    if not (fish.bait != -1) and (character.inventory.contains fish.bait)
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
      if fish.bait != -1 then character.inventory.remove fish.bait end
      character.inventory.add fish.fish[want]
      skills.add_experience Skill::FISHING, fish.exp[want]
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

  def get_fish(fish, level)
    tempInt = 0
    fish.level.each do |id|
      if level == id or level > id
        tempInt += 1
      end
    end
    return tempInt != -1 ? rand(tempInt) : -1
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