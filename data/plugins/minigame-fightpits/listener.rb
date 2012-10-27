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
      if $pits.get_time > 0
        character.send_message "Next round starts in #{$pits.get_time} minute#{$pits.get_time == 1 ? "" : "s"}!"
      end
    elsif team == GAME
      $pits.set_attribute 3, true
      character.teleport GAME_ENTER
    end
    character.send SetInterfaceTextEvent.new(2805, "Current Champion: #{$pits.get_champion}")
    character.interface_set.open_overlay 2804
  end

  def playerRemoved(player)
    character = player.get_character
    team = player.get_team
    if team == LOBBY
      character.teleport LOBBY_LEAVE
      character.interface_set.close true
    end
  end

  def playerDisconnected(player)
    player.teleport LOBBY_LEAVE
    $pits.remove_character player
  end

  def playerDied(event)
    victim = event.get_player
    source = event.get_source
    $pits.transfer_team victim, LOBBY
    $pits.set_attribute 3, true

    victim.reset_melee_set
    if source != nil
      source.reset_melee_set
    end
  end

end
