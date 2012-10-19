# Contains the castle wars minigame.
 
require 'java'
java_import 'org.apollo.game.minigame.Minigame'
java_import 'org.apollo.game.event.impl.ConfigEvent'
 
$cwars = nil
 
class Game < Minigame

  attr_reader :tick
 
  def initialize
    super("Castle wars", 4)
    @tick = 180
  end

  def before_pulse
    tick = @tick
    if tick != 0 and tick % 60 == 0
      set_attribute 2, true
    end
  end
 
  def pulse
    before_pulse
    tick = @tick
    if get_attribute 0
      if tick < 0 or tick == 0
        # Clearing active game
        get_characters(ZAMM_TEAM_GAME).each do |player|
          remove_player ZAMM_TEAM_GAME, player
        end
        get_characters(SARA_TEAM_GAME).each do |player|
          remove_player SARA_TEAM_GAME, player
        end
        set_attribute 0, false
      else
        # Get active players
        if get_attribute 1
          # For players sendConfig
          set_attribute 1, false
        end
        if get_attribute 2
          time = tick / 60 == 0 ? 1 : tick / 60
          get_characters(SARA_TEAM_LOBBY, SARA_TEAM_GAME, ZAMM_TEAM_LOBBY, ZAMM_TEAM_GAME).each do |player|
            player.send ConfigEvent.new(380, time)
          end
          set_attribute 2, false
        end
        if get_attribute 3
          # For players send updateFlag sara
          set_attribute 3, false
        end
        if get_attribute 4
          # For players send updateFlag zamm
          set_attribte 4, false
        end
        @tick = tick - 1
      end
    elsif tick < 0 or tick == 0
      get_characters(SARA_TEAM_LOBBY).each do |player|
        transfer_team player, SARA_TEAM_GAME
      end
      get_characters(ZAMM_TEAM_LOBBY).each do |player|
        transfer_team player, ZAMM_TEAM_GAME
      end
      set_attribute 0, true
      @tick = 1200
    else
      if get_attribute 2
        time = tick / 60 == 0 ? 1 : tick / 60
        get_characters(SARA_TEAM_LOBBY, ZAMM_TEAM_LOBBY).each do |player|
          player.send ConfigEvent.new(380, time)
        end
        set_attribute 2, false
      end
      @tick = tick - 1
    end
  end

  def get_tick
    return @tick
  end
 
end
 
$cwars = Game.new
$cwars.add_listener Listener.new
World.get_world.register $cwars