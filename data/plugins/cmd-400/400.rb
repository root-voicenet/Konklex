require 'java'
java_import 'org.apollo.game.sync.block.SynchronizationBlock'
java_import 'org.apollo.game.model.Position'
java_import 'org.apollo.game.event.impl.OpenWelcomeScreenEvent'
java_import 'org.apollo.game.event.impl.ConstructMapRegionEvent'

on :command, :four do |player, command|
  args = command.arguments
  if args.length == 2
    x = args[0].to_i
    y = args[1].to_i
    #player.get_block_set.add SynchronizationBlock.createForceMovementBlock(player.get_position, Position.new(x, y), 200, 201, 1)
    player.send ConstructMapRegionEvent.new(Position.new(x, y), true)
  else
    player.send_message "Syntax: ::400 [x] [y]"
  end
  #player.send OpenWelcomeScreenEvent.new(200, 1, true, 1, 1)
end