require 'java'
java_import 'org.apollo.game.event.impl.ConfigEvent'
java_import 'org.apollo.game.model.NPC'
java_import 'org.apollo.game.model.NPCDefinition'
java_import 'org.apollo.game.model.World'

on :command, :npc, RIGHTS_OWNER do |player, command|
  args = command.arguments
  if args.length == 1
    id = args[0].to_i
    World.get_world.get_npc_repository.add NPC.new(NPCDefinition.new(id), player.get_position)
  else
    player.send_message "Syntax: ::npc [id]"
  end
end