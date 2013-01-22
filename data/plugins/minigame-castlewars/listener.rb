# Holds the listener for usefull event handling.

require 'java'
java_import 'org.apollo.game.minigame.MinigameListener'
java_import 'org.apollo.game.event.impl.ConfigEvent'

class Listener < MinigameListener

  def playerAdded(player)
    character = player.get_character
    team = player.get_team
    time = $cwars.get_tick / 60 == 0 ? 1 : $cwars.get_tick / 60
    if team == SARA_TEAM_LOBBY
      character.teleport ENTER_SARA_PORTAL, false
      character.send ConfigEvent.new(380, time)
      character.get_interface_set.open_walkable 11479
    elsif team == ZAMM_TEAM_LOBBY
      character.teleport ENTER_ZAMM_PORTAL, false
      character.send ConfigEvent.new(380, time)
      character.get_interface_set.open_walkable 11479
    elsif team == SARA_TEAM_GAME
      character.send ConfigEvent.new(380, time)
      character.teleport SARA_GAME, false
      character.get_interface_set.open_walkable 11146
    elsif team == ZAMM_TEAM_GAME
      character.send ConfigEvent.new(380, time)
      character.teleport ZAMM_GAME, false
      character.get_interface_set.open_walkable 11146
    end
  end

  def playerRemoved(player)
    character = player.get_character
    team = player.get_team
    time = $cwars.get_tick / 60 == 0 ? 1 : $cwars.get_tick / 60
    if team == SARA_TEAM_LOBBY
      character.teleport LEAVE_SARA_PORTAL, false
      character.get_interface_set.open_walkable -1
    elsif team == ZAMM_TEAM_LOBBY
      character.teleport LEAVE_ZAMM_PORTAL, false
      character.get_interface_set.open_walkable -1
    elsif team == ZAMM_TEAM_GAME or team == SARA_TEAM_GAME
      character.teleport GAME_LOBBY, false
      character.get_interface_set.open_walkable -1
    end
  end

  def playerDisconnected(player)
    player.teleport GAME_LOBBY, false
    $cwars.remove_character player
  end

  def playerDied(player, source)
    player.teleport
  end

end