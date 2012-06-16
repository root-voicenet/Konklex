require 'java'
java_import 'org.apollo.game.action.DistancedAction'
java_import 'org.apollo.game.model.Animation'
java_import 'org.apollo.game.model.Graphic'
java_import 'org.apollo.game.event.impl.ServerMessageEvent'

PICKPOCKET_ANIMATION = Animation.new(881)
PICKPOCKET_GRAPHIC = Graphic.new(348)

class Pickpocket < DistancedAction

 attr_reader :started, :npc, :npcz

  def initialize(player, npc, npcz)
    super 3, true, player, npc.get_position, 1
    @started = false
    @npc = npc
    @npcz = npcz
  end

  def start_thieve
    character.turn_to npc.get_position
    character.send_message "You attempt to pick the man's pocket."
    character.play_animation PICKPOCKET_ANIMATION
    @started = true
  end
  
  def executeAction
    if not @started
      start_thieve
    else
      if rand(2) == 1
        gave = false
        while not gave
          npcz.loot.each do |item, amount|
            if not gave
              if rand(4) == 1
                character.inventory.add item, amount
                gave = true
              end
            end
          end
        end
        character.skill_set.add_experience Skill::THIEVING, 17
        character.send_message "You pick the man's pocket."
      else
        npc.turn_to character.get_position
        npc.send_message "What do you think you're doing?"
        character.play_graphic PICKPOCKET_GRAPHIC
        character.damage_entity rand(3)
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
    npcz = NPCZ[event.slot]
    if npcz != nil
      player.startAction Pickpocket.new(player, event.get_npc, npcz)
      ctx.break_handler_chain
    end
  end
end
