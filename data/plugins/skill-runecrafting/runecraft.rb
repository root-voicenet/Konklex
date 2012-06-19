require 'java'
java_import 'org.apollo.game.model.Animation'
java_import 'org.apollo.game.model.Graphic'
java_import 'org.apollo.game.action.DistancedAction'
java_import 'org.apollo.game.action.Action'
java_import 'org.apollo.game.model.Item'

RUNECRAFTING_ANIMATION = Animation.new(791)
RUNECRAFTING_GFX = Graphic.new(186)

class RunecraftingAction < DistancedAction

  attr_reader :rune, :position

  def initialize(player, rune, position)
    super 2, true, player, position, 4
    @rune = rune
    @position = position
  end

  def executeAction
    skills = character.skill_set
    level = skills.skill(Skill::RUNECRAFTING).maximum_level # TODO: is using max level correct?

    # verify level requirements
    if rune.level > level
      character.send_message "You need a Runecrafting level of at least #{rune.level} to craft this."
      stop
      return
    end

    # verify item requirements
    if not (character.inventory.contains rune.ess)
      character.send_message "You do not have the required essense to craft this."
      stop
      return
    end

    # configure some values
    count = character.inventory.get_item_count rune.ess
    extra = level / rune.mult

    # start runecrafting fella's
    character.send_message "You bind the temple's power into the essence."
    character.play_animation RUNECRAFTING_ANIMATION
    character.play_graphic RUNECRAFTING_GFX
    character.inventory.remove rune.ess, count
    character.inventory.add Item.new(rune.item, count * extra)
    skills.add_experience Skill::RUNECRAFTING, (rune.exp * extra)

    # stop everything
    stop
  end

  def equals(other)
    return (get_class == other.get_class and @rune == other.rune and @position == other.position)
  end

end

class TalismanAction < Action

  attr_reader :talisman, :message_x, :message_y

  def initialize(player, talisman)
    super 0, true, player
    @talisman = talisman
    @message_x = ""
    @message_y = ""
  end

  def locate
    # get character coordinates
    x = character.position.x
    y = character.position.y

    # get the altars coordinates
    target_x = talisman.x
    target_y = talisman.y

    # set the messages
    if x >= target_x then @message_x = "West" end
    if y > target_y then @message_y = "South" end
    if x < target_x then @message_x = "East" end
    if y <= target_y then @message_y = "North" end
  end

  def execute
    if character.position.is_within_distance Position.new(talisman.x, talisman.y), 6
      character.send_message "Your talisman glows brightly"
    else
      locate
      character.send_message "The talisman pulls towards #{message_y}-#{message_x}"
    end
    stop
  end

  def equals(other)
    return (get_class == other.get_class and @talisman == other.talisman)
  end

end

on :event, :item_option do |ctx, player, event|
  if event.option == 4
    talisman = TALISMANS[event.id]
    if talisman != nil
      player.start_action TalismanAction.new(player, talisman)
      ctx.break_handler_chain
    end
  end
end

on :event, :object_action do |ctx, player, event|
  if event.option == 1
    rune = RUNES[event.id]
    if rune != nil
      player.start_action RunecraftingAction.new(player, rune, event.position)
      ctx.break_handler_chain
    end
  end
end