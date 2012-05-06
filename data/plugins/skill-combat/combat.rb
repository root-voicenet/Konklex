require 'java'

# Handle the player option attack
on :event, :player_option do |ctx, player, event|
  if event.get_option == 1
    if Combat.isFighting(event.get_player)
      player.send_message "That player is already under attack!"
    else
      if Combat.canFight(player)
        player.start_action PlayerCombat.new(player, event.get_player, true)
        Combat.addTimer event.get_player, PlayerCombat.new(event.get_player, player, false), 1
        ctx.break_handler_chain
      else
        player.send_message "You are already attacking someone!"
      end
    end
  end
end

# Handle the npc option attack
on :event, :npc_option do |ctx, player, event|
  if event.get_option == 1
    if Combat.isFighting(event.get_npc)
      player.send_message "That npc is already under attack!"
    else
      if Combat.canFight(player)
        player.start_action NpcCombat.new(player, event.get_npc, true)
        Combat.addTimer event.get_npc, NpcCombat.new(event.get_npc, player, false), 1
        ctx.break_handler_chain
      else
        player.send_message "You are already attacking someone!"
      end
    end
  end
end