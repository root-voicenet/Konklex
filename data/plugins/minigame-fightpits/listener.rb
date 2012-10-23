# Holds the listener for usefull event handling.

require 'java'
java_import 'org.apollo.game.minigame.MinigameListener'
java_import 'org.apollo.game.event.impl.ConfigEvent'

class PitListener < MinigameListener

  def playerAdded(player)
    character = player.get_character
    team = player.get_team
    if team == LOBBY
      character.teleport LOBBY_ENTER
      character.send SetInterfaceTextEvent.new(6664, "")
      character.send SetInterfaceTextEvent.new(6570, "Next Game Begins In : #{$pits.get_tick} seconds.")
      character.send SetInterfaceTextEvent.new(6572, "Champion: #{$champion}")
      character.get_interface_set.open_walkable 6673
    elsif team == GAME
      character.teleport GAME_ENTER
      character.get_interface_set.open_walkable -1
    end
  end

  def playerRemoved(player)
    character = player.get_character
    team = player.get_team
    if team == LOBBY
      character.teleport LOBBY_LEAVE
      character.get_interface_set.open_walkable -1
    elsif team == GAME
      character.teleport LOBBY_ENTER
    end
  end

  def playerDisconnected(player)
    player.teleport LOBBY_LEAVE
    $pits.remove_character player
  end

  def playerDied(player, source)
    transfer_team player, LOBBY
  end

end