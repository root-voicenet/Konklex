# Actions called upon a object interaction

require 'java'
java_import 'org.apollo.game.action.DistancedAction'
java_import 'org.apollo.game.event.impl.ConfigEvent'

class Actions < DistancedAction

 attr_reader :position, :object

  def initialize(character, position, object)
    super 0, true, character, position, 3
    @position = position
    @object = object
  end

  def getTeam(player)
    return $cwars.get_team player
  end

  def executeAction
    time = $cwars.get_tick / 60 == 0 ? 1 : $cwars.get_tick / 60
    case object
      when 4387
        if $cwars.add_player SARA_TEAM_LOBBY, character
          character.teleport ENTER_SARA_PORTAL, false
          character.send ConfigEvent.new(380, time)
          character.get_interface_set.open_walkable 11479
        end
      when 4389
        if $cwars.remove_player SARA_TEAM_LOBBY, character
          character.teleport LEAVE_SARA_PORTAL, false
          character.get_interface_set.open_walkable -1
        end
      when 4388
        if $cwars.add_player ZAMM_TEAM_LOBBY, character
          character.teleport ENTER_ZAMM_PORTAL, false
          character.send ConfigEvent.new(380, time)
          character.get_interface_set.open_walkable 11479
        end
      when 4390
        if $cwars.remove_player ZAMM_TEAM_LOBBY, character
          character.teleport LEAVE_ZAMM_PORTAL, false
          character.get_interface_set.open_walkable -1
        end
    end
    stop
  end

  def equals(other)
    return (get_class == other.get_class)
  end

end

on :event, :object_action do |ctx, player, event|
  if event.option == 1
    player.start_action Actions.new(player, event.position, event.id)
  end
end