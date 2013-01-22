# Actions called upon a object interaction

require 'java'
java_import 'org.apollo.game.action.DistancedAction'
java_import 'org.apollo.game.event.impl.ConfigEvent'

class PitActions < DistancedAction

 attr_reader :position, :object

  def initialize(character, position, object)
    super 0, true, character, position, 3
    @position = position
    @object = object
  end

  def executeAction
    team = $pits.get_team character
    case object
      when 9369
        if team == LOBBY
          $pits.remove_character LOBBY, character
        else
          $pits.add_character LOBBY, character
        end
      when 9368
        $pits.remove_character GAME, character
    end
    stop
  end

  def equals(other)
    return (get_class == other.get_class and other.position == @position and other.object == @object)
  end

end

on :event, :object_action do |ctx, player, event|
  if event.option == 1
    if event.id == 9369 or event.id == 9368
      player.start_action PitActions.new(player, event.position, event.id)
    end
  end
end