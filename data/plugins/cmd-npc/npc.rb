require 'java'
java_import 'org.apollo.game.event.impl.ConfigEvent'
java_import 'org.apollo.game.model.Npc'
java_import 'org.apollo.game.model.def.NpcDefinition'
java_import 'org.apollo.game.model.World'

on :command, :npc, RIGHTS_OWNER do |player, command|
  args = command.arguments
  if args.length == 1
    id = args[0].to_i
	if id >= 0 and id < NpcDefinition.count
	  npc = Npc.new(id, player.position)
	  World.world.npc_repository.add npc
    else
	  player.send_message "NPC id out of bounds: #{id}."
	end
  else
    player.send_message "Syntax: ::npc [id]"
  end
end