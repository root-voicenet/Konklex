require 'java'
java_import 'org.apollo.game.event.impl.SpecialEvent'

on :command, :test, RIGHTS_DEV do |player, command|
  args = command.arguments
  if args.length == 2
    bar = args[0].to_i
    enabled = args[1].to_i == 1
    player.send SpecialEvent.new(enabled, bar)
  else
    player.send_message "Syntax: ::test [bar] [enabled]"
  end
end

on :command, :pits, RIGHTS_DEV do |player, command|
  player.send ConfigEvent.new(560, 1)
  player.send SetInterfaceTextEvent.new(2805, "Current Champion: Buroa")
  player.get_interface_set.open_walkable 2804
end
