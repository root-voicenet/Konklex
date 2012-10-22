# Contains the zombie minigame.
 
require 'java'
java_import 'org.apollo.game.minigame.Minigame'
java_import 'org.apollo.game.event.impl.ConfigEvent'

$points = 0
$zombie = nil
 
class Game < Minigame

  attr_reader :tick
 
  def initialize
    super("Zombies", 4)
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
        get_characters(PLAYER_TEAM).each do |player|
          remove_player PLAYER_TEAM, player
        end
        get_characters(ZOMBIE_TEAM).each do |player|
          remove_player ZOMBIE_TEAM, player
        end
        $points = 0
        set_attribute 0, false
      else
        # Get active players
        if get_attribute 1
          # For players sendConfig
          set_attribute 1, false
        end
        if get_attribute 2
          time = tick / 60 == 0 ? 1 : tick / 60
          get_characters(PLAYER_TEAM, WAITING_TEAM).each do |player|
            # Lets notify the waiting & current team about the time left
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
      get_characters(WAITING_TEAM).each do |player|
        transfer_team player, PLAYER_TEAM
      end
      set_attribute 0, true
      @tick = 1200
    else
      if get_attribute 2
        time = tick / 60 == 0 ? 1 : tick / 60
        get_characters(WAITING_TEAM).each do |player|
          # Lets notify the waiting team on the time
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
 
$zombie = Game.new
$zombie.add_listener Listener.new
World.get_world.register $zombie