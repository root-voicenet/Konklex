require 'java'
java_import 'org.apollo.game.action.DistancedAction'
java_import 'org.apollo.game.model.Animation'

FLAX_ANIMATION = Animation.new(827)

class FlaxAction < DistancedAction
  
  attr_reader :position, :started

  def initialize(character, position)
    super 2, true, character, position, 1
    @position = position
    @started = false
  end

  def executeAction
    if not started
      character.turn_to position
      if character.inventory.add 1779
        character.play_animation FLAX_ANIMATION
        character.send_message "You pick some flax."
      end
      @started = true
    else
      stop
    end
  end

  def equals(other)
    return (get_class == other.get_class)
  end

end

on :event, :object_action do |ctx, player, event|
  if event.option == 2
    if event.id == 2646
      player.startAction FlaxAction.new(player, event.position)
      ctx.break_handler_chain
    end
  end
end