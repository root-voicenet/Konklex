# Holds the listener for usefull event handling.

require 'java'
java_import 'org.apollo.game.minigame.MinigameListener'
java_import 'org.apollo.game.event.impl.ConfigEvent'

class Listener < MinigameListener

  def playerAdded(player)
    character = player.get_player
    team = player.get_team
    time = $zombie.get_tick / 60 == 0 ? 1 : $zombie.get_tick / 60
    if team == PLAYER_TEAM
      character.teleport START
      character.get_interface_set.open_walkable 4958
    elsif team == WAITING_TEAM
      # Send time
    end
  end

  def playerRemoved(player)
    character = player.get_player
    team = player.get_team
    if team == ZOMBIE_TEAM
      World.world.region_manager.remove_npc character
      $points += 500
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
    $cwars.remove_player player
  end

  def playerDied(player, source)
    player.teleport
  end

end

# send(new SetInterfaceTextEvent(4960, "Zombies"));
# send(new SetInterfaceTextEvent(4962, "0"));
# interfaceSet.openWalkable(4958);