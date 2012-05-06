require 'java'
java_import 'org.apollo.game.action.DistancedAction'
java_import 'org.apollo.game.model.inter.minigame.CastleWars'
java_import 'org.apollo.game.model.inter.minigame.util.GameType'
java_import 'org.apollo.game.model.inter.minigame.util.Team'

class Cwars < DistancedAction

 attr_reader :position, :object

  def initialize(character, position, object)
    super 0, true, character, position, 3
    @position = position
    @object = object
  end

  def getTeam
    team = CastleWars.get_instance.get_team character, GameType::ACTIVE
    if team != nil
      return team
    else
      return nil
    end
  end

  def executeAction
    if @object.required ## Team is required
      team = getTeam
      if team != nil && team == @object.team ## Correct team, finish whatever.
        if @object.id == 4406 ## Sara portal leave
          if CastleWars.get_instance.remove_player(Team::SARADOMIN, GameType::ACTIVE, character)
            character.teleport Position.new(2440, 3090, 0)
          end
        elsif @object.id == 4902 || @object.id == 4903
          CastleWars.get_instance.capture_flag(character)
        elsif @object.id == 4378 || @object.id == 4377
          CastleWars.get_instance.return_flag(character)
        end
      end
    else
      # Team isnt required
      if @object.id == 4387 ## Sara portal
        if CastleWars.get_instance.add_player(Team::SARADOMIN, GameType::WAITING, character)
          character.teleport CastleWars.get_instance.get_teams.get(Team::SARADOMIN).get_lobby
        end
      elsif @object.id == 4389 ## Sara portal leave
        if CastleWars.get_instance.remove_player(Team::SARADOMIN, GameType::WAITING, character)
          character.teleport Position.new(2440, 3090, 0)
        end
      elsif @object.id == 4388 ## Zamorak portal
        if CastleWars.get_instance.add_player(Team::ZAMORAK, GameType::WAITING, character)
          character.teleport CastleWars.get_instance.get_teams.get(Team::ZAMORAK).get_lobby
        end
      elsif @object.id == 4390 ## Zamorak portal leave
        if CastleWars.get_instance.remove_player(Team::ZAMORAK, GameType::WAITING, character)
          character.teleport Position.new(2440, 3090, 0)
        end
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
    object = CWOBJECT[event.id]
    if object != nil
      player.start_action Cwars.new(player, event.position, object)
    end
  end
end