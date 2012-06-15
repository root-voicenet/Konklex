require 'java'
java_import 'org.apollo.game.action.Action'
java_import 'org.apollo.game.model.Animation'
java_import 'org.apollo.game.model.Graphic'
java_import 'org.apollo.game.event.impl.ServerMessageEvent'

PICKPOCKET_ANIMATION = Animation.new(881)
PICKPOCKET_GRAPHIC = Graphic.new(348)

class Pickpocket < Action

 attr_reader :started, :npc

  def initialize(player, npc)
    super 3, true, player
    @started = false
    @npc = npc
  end

  def start_thieve
    character.turn_to npc.get_position
    character.send_message "You attempt to pick the man's pocket."
    character.play_animation PICKPOCKET_ANIMATION
    @started = true
  end
  
  def execute
    if not @started
      start_thieve
    else
      if rand(2) == 1
        character.inventory.add 995, 1
        character.skill_set.add_experience Skill::THIEVING, 17
        character.send_message "You pick the man's pocket."
      else
        npc.turn_to character.get_position
        npc.send_message "What do you think you're doing?"
        character.play_graphic PICKPOCKET_GRAPHIC
        character.damage_entity rand(2)
        character.send_message "You fail to pick the man's pocket."
      end
      stop
    end
  end

  def equals(other)
    return (get_class == other.get_class)
  end

end

on :event, :npc_option do |ctx, player, event|
  if event.get_option == 3
    player.startAction Pickpocket.new(event.get_npc, player)
    ctx.break_handler_chain
  end
end
