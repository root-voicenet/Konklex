require 'java'
java_import 'org.apollo.game.model.World'
java_import 'org.apollo.game.model.def.StaticObjectDefinition'

WORLD_OBJECTS = World.get_world.get_object_manager

on :command, :object, RIGHTS_DEV do |player, command|
  args = command.get_arguments
  if args.length == 3
    event = args[0]
    objectid = args[1].to_i
    orient = args[2].to_i
    definition = StaticObjectDefinition.new player.get_position, objectid, orient, 10
    if event == "add"
      WORLD_OBJECTS.add definition
    elsif event == "del"
      WORLD_OBJECTS.remove definition
    elsif event == "rep"
      WORLD_OBJECTS.replace definition
    end
  else
    player.send_message "Syntax: ::object [event] [id] [orient]"
  end
end