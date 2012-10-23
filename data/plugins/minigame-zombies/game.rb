# Contains the zombie minigame.
 
require 'java'
java_import 'org.apollo.game.minigame.Minigame'
java_import 'org.apollo.game.event.impl.ConfigEvent'

$points = 0
$zombie = nil
 
class Zombies < Minigame

  attr_reader :tick
 
  def initialize
    super("Zombies", 3)
    @tick = 180
  end

  def pulse
  end

  def get_tick
    return @tick
  end
 
end
 
$zombie = Zombies.new
$zombie.add_listener ZombieListener.new
World.get_world.register $zombie