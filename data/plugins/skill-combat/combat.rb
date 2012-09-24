require 'java'
java_import 'org.apollo.game.model.inter.melee.Combat'

# Handle the player option attack
on :event, :player_option do |ctx, player, event|
  if event.option == 1
    Combat.attack player, event.get_player
  end
end

# Handle the npc option attack
on :event, :npc_option do |ctx, player, event|
  if event.option == 1
    Combat.attack player, event.get_npc
  end
end