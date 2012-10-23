# Actions called upon a object interaction

require 'java'
java_import 'org.apollo.game.action.DistancedAction'
java_import 'org.apollo.game.event.impl.ConfigEvent'

class ZombieActions < DistancedAction

 attr_reader :position, :object

  def initialize(character, position, object)
    super 0, true, character, position, 3
    @position = position
    @object = object
  end

  def executeAction
    case object
      when 4387
        $cwars.add_player SARA_TEAM_LOBBY, character
      when 4389
        $cwars.remove_player SARA_TEAM_LOBBY, character
      when 4388
        $cwars.add_player ZAMM_TEAM_LOBBY, character
      when 4390
        $cwars.remove_player ZAMM_TEAM_LOBBY, character
    end
    stop
  end

  def equals(other)
    return (get_class == other.get_class and other.position == @position and other.object == @object)
  end

end

on :event, :object_action do |ctx, player, event|
  if event.option == 1
    if event.id == 4397 or event.id == 4389 or event.id == 4388 or event.id == 4390
      player.start_action ZombieActions.new(player, event.position, event.id)
    end
  end
end