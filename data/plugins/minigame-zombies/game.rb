# Contains the zombie minigame.
 
require 'java'
java_import 'org.apollo.game.minigame.Minigame'
java_import 'org.apollo.game.event.impl.ConfigEvent'
java_import 'org.apollo.game.model.Skill'
java_import 'org.apollo.game.model.inter.melee'

$zombie = nil
 
class Zombies < Minigame

  attr_reader :tick, :points, :round
 
  def initialize
    super("Zombies", 3)
    @tick = 20
  end

  def pulse
    if tick == 0
      @tick = 20
      spawn
    else
      tick -= 1
    end
  end

  def get_tick
    return @tick
  end

  def get_points
    return @points
  end

  def spawn
    size = get_characters(PLAYER_TEAM)
    0.upto(size) do |n|
      victim = get_characters(PLAYER_TEAM).get(n)
      skill = Skill.new 0, ROUND * 3, ROUND * 2
      npc = Npc.new ZOMBIE, PLACE
      npc.skill_set.set_skill Skill::STRENGTH, skill
      World.world.register npc
      Combat.attack npc, victim
    end
  end

end
 
$zombie = Zombies.new
$zombie.add_listener ZombieListener.new
World.get_world.register $zombie