# Contains the zombie minigame.
 
require 'java'
java_import 'org.apollo.game.minigame.Minigame'
java_import 'org.apollo.game.event.impl.ConfigEvent'
java_import 'org.apollo.game.model.Skill'
java_import 'org.apollo.game.model.inter.melee.Combat'

$zombie = nil
 
class Zombies < Minigame

  attr_reader :tick, :points, :round, :shop
 
  def initialize
    super("Zombies", 3)
    @tick = 0
    @round = 1
    @points = 0
    @shop = Npc.new 534, SHOP
  end

  def pulse
    if get_attribute 1
      if get_characters(PLAYER_TEAM).size == 0
        get_characters(ZOMBIE_TEAM).each do |zombie|
          World.world.unregister zombie
	  remove_character zombie
	end
	if round > 9
	  World.world.unregister shop
	end
        set_attribute 1, false
        @tick == 120
	@round = 1
      else
	if tick > 0 and get_characters(ZOMBIE_TEAM).size == 0
          @tick -= 1
	end
        if get_attribute 2
          get_characters(PLAYER_TEAM).each do |player|
            player.send SetInterfaceTextEvent.new(4962, "#{points}")
          end
          set_attribute 2, false
        end
        if get_characters(ZOMBIE_TEAM).size == 0
          if tick == 0
	     if round == 10
	       World.world.register shop
	       shop.send_message "We are now open!"
	     end
	     get_characters(PLAYER_TEAM).each do |player|
              player.send_message "Starting round #{round}, good luck!"
            end
	    start_round
	  else
	    if tick == 20
              get_characters(PLAYER_TEAM).each do |player|
                player.send_message "Next round starting in 20 seconds!"
              end
	    end
	  end
	end
      end
    else
      if tick == 0 and get_characters(WAITING_TEAM).size > 0
        get_characters(WAITING_TEAM).each do |character|
          transfer_team character, PLAYER_TEAM
        end
        set_attribute 1, true
      end
      if tick > 0
        @tick -= 1
      end
    end
  end

  def get_tick
    return @tick
  end

  def add_points(points)
    @points += points
    set_attribute 2, true
  end

  def remove_points(points)
    @points -= points
    set_attribute 2, true
  end

  def get_round
    return @round
  end

  def get_points
    return @points
  end

  def start_round
    strength = Skill.new 0, round * 3, round * 2
    health   = Skill.new 0, round * 3, round * 2
    get_characters(PLAYER_TEAM).each do |player|
      npc = Npc.new ZOMBIE, player.get_position
      npc.skill_set.set_skill Skill::STRENGTH, strength
      npc.skill_set.set_skill Skill::HITPOINTS, health
      add_character ZOMBIE_TEAM, npc
      World.world.register npc
      Combat.attack npc, player
    end
    @round += 1
    @tick = 21
  end

end
 
$zombie = Zombies.new
$zombie.add_listener ZombieListener.new
World.get_world.register $zombie

on :command, :zombies do |player, command|
  $zombie.add_character WAITING_TEAM, player
end

on :event, :npc_option do |ctx, player, event|
  if event.get_option == 3
    if event.get_npc.get_id == 534
      if $zombie.get_round >= 10
	if $zombie.get_attribute 1 and $zombie.get_characters(PLAYER_TEAM).contains(player)
          World.world.get_stores.open_shop player, 200
	end
      end
    end
  end
end
