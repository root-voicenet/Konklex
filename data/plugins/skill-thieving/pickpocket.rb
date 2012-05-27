require 'java'
java_import 'org.apollo.game.action.Action'
java_import 'org.apollo.game.model.Animation'
java_import 'org.apollo.game.model.Graphic'

PICKPOCKET_ANIMATION = Animation.new(881)
PICKPOCKET_GRAPHIC = Graphic.new(348)

class Pickpocket < Action

 attr_reader :started, :npc, :player

  def initialize(npc, player)
    super 3, true, player
    @started = false
    @npc = npc
    @player = player
  end

  def start_thieve
    @started = true
    player.face_entity npc.get_index
    player.send_message "You attempt to pick the man's pocket."
    player.play_animation PICKPOCKET_ANIMATION
  end
  
  def execute
    if not @started
      start_thieve
    else
      if rand(2) == 1
        if player.inventory.add 995, 1
          player.skill_set.add_experience Skill::THIEVING, 17
          player.send_message "You pick the man's pocket."
          stop_thieve
        else
          player.send_message "Full inventory."
          stop_thieve
        end
      else
        npc.start_facing player.get_index
        player.play_graphic PICKPOCKET_GRAPHIC
        player.damage_entity rand(2)
        player.send_message "You fail to pick the man's pocket."
        stop_thieve
      end
    end
  end

  def stop_thieve
    player.stop_facing
    npc.stop_facing
    stop
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
