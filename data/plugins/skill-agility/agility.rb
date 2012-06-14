require 'java'
java_import 'org.apollo.game.action.DistancedAction'
java_import 'org.apollo.game.model.Position'
java_import 'org.apollo.game.sync.block.SynchronizationBlock'

class AgilityAction < DistancedAction

  attr_reader :position, :id, :started

  def initialize(player, position, id)
    super 4, true, player, position, 1
    @position = position
    @id = id
    @started = false
  end

  def executeAction
    if not @started
      walkto @id
      @started = true
    else
      stop
      # reward exp for finishing
    end
  end

  def walkto(id)
    player = get_character
    if id == 2295
      newpos = Position.new(2474, 3429, 0)
      player.get_block_set.add SynchronizationBlock.createForceMovementBlock(@position, newpos, 1001, 1000, 2)
    elsif id == 2
    end
  end

  def equals(other)
    return (get_class == other.get_class)
  end

end

on :event, :object_action do |ctx, player, event|
  if player.get_name == "Buroa"
    player.send_message "Object id: #{event.id} , #{event.option}"
  end
  if event.option == 1
    player.startAction AgilityAction.new(player, event.position, event.id)
  end
end