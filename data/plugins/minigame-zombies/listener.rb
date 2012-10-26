# Holds the listener for usefull event handling.

require 'java'
java_import 'org.apollo.game.minigame.MinigameListener'
java_import 'org.apollo.game.event.impl.ConfigEvent'

class ZombieListener < MinigameListener

  def playerAdded(player)
    character = player.get_character
    team = player.get_team
    time = $zombie.get_tick / 60 == 0 ? 1 : $zombie.get_tick / 60
    if team == PLAYER_TEAM
      character.teleport START
      character.get_interface_set.open_walkable 4958
      character.send SetInterfaceTextEvent.new(4960, "Zombies")  
    elsif team == WAITING_TEAM
      if time > 0
        if !$zombie.get_attribute 1
          character.send_message "Next round starts in #{time} ticks!"
        else
          character.send_message "There is a game going on, you are added to the waiting queue."
        end
      end
    elsif team == ZOMBIE_TEAM
      character.send_message "Zombies...."
    end
  end

  def playerRemoved(player)
    # only used for players
  end

  def playerDisconnected(character)
    team = $zombie.get_team character
    if team == PLAYER_TEAM
      character.teleport WAIT
      $zombie.remove_character team, character
    end
  end

  def playerDied(event)
    team = event.get_player_team
    if team == ZOMBIE_TEAM
      $zombie.add_points 50
      zombie = event.get_player
      World.world.unregister zombie
      $zombie.remove_character team, zombie

      source = event.get_character
      if source != nil
        source.reset_melee_set
      end
    elsif team == PLAYER_TEAM
      player = event.get_player
      character.get_interface_set.open_walkable -1
      transfer_team event.get_player, WAITING_TEAM
    end
  end

end
