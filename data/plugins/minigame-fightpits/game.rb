# Contains the tzar fight pits minigame.
 
require 'java'
java_import 'org.apollo.game.minigame.Minigame'
java_import 'org.apollo.game.event.impl.ConfigEvent'
 
$pits = nil
$champion = "Nobody"

# Attribute 1 starts game
 
class Pits < Minigame

  attr_reader :tick

  def initialize
    super("Fight pits", 2)
    @tick = 180
  end
 
  def pulse
    if get_attribute 1 # Active game
      if get_characters(GAME).size == 1
        winner = get_characters(GAME).get(0)
        transfer_team winner, LOBBY
        $champion = winner.name
        set_attribute 2, true
        set_attribute 0, false
      end
    else
      if get_attribute 2
        get_characters(LOBBY).each do |player|
          player.send SetInterfaceTextEvent.new(6572, "Champion: #{$champion}")
        end
        set_attribute 2, false
      end
      if get_attribute 3
        if get_characters(LOBBY).each do |player|
          player.send SetInterfaceTextEvent.new(6570, "Next Game Begins In : #{tick} seconds.")
        end
        set_attribute 3, false
      end
      if tick == 0
        if get_characters(LOBBY).size > 1
          set_attribute 1, true
        end
      end
    end
    if tick > 0
      set_attribute 3, true
      @tick = tick - 1
    end
  end
end

  def get_tick
    return @ticktick
  end
 
end
 
$pits = Pits.new
$pits.add_listener PitListener.new
World.get_world.register $pits