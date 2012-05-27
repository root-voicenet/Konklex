# Holds the listener for usefull event handling.

require 'java'
java_import 'org.apollo.game.minigame.MinigameListener'

class Listener < MinigameListener

  def playerDisconnected(player)
    player.teleport GAME_LOBBY, false
    $cwars.remove_player player
  end

end