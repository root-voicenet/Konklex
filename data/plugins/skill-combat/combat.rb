require 'java'
java_import 'org.apollo.game.model.inter.melee.Combat'

# Handle the player option attack
on :event, :player_option do |ctx, player, event|
  if event.option == 1
    player.get_melee_set.set_magic_spell_id 0
    Combat.attack player, event.get_player
  end
end

# Handle the npc option attack
on :event, :npc_option do |ctx, player, event|
  if event.option == 1
    player.get_melee_set.set_magic_spell_id 0
    Combat.attack player, event.get_npc
  end
end

# Handles the magic on characters
on :event, :magic do |ctx, player, event|
  player.get_melee_set.set_magic_spell_id event.get_spell_id
  Combat.attack player, event.get_victim
end