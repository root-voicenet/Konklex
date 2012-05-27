require 'java'
java_import 'org.apollo.game.scheduling.ScheduledTask'
java_import 'org.apollo.game.model.Animation'
java_import 'org.apollo.game.model.Position'
java_import 'org.apollo.game.model.World'
java_import 'org.apollo.game.model.Player'
java_import 'org.apollo.game.model.Npc'
java_import 'org.apollo.game.model.GroundItem'

# TODO Redo this class, its made very messy.

DEATH_POSITION = Position.new(3094, 3495)

class Death < ScheduledTask
  
  attr_reader :character, :killer, :started, :npc
  
  def initialize(character, killer)
    super 3, true
    @character = character
    @killer = killer
    @started = false
    @npc = character.instance_of? Npc
    World.get_world.schedule self
  end
  
  def execute
    if not @started
      stopall
      character.play_animation npc ? Combat.getNpcDeathAnimation(character.get_definition.get_id) : DEFAULT_DEATH_ANIMATION
      @started = true
    else
      if npc
        World.get_world.get_npc_repository.remove character
        GroundItem.get_instance.create killer, 526, 1, character.get_position
        GroundItem.get_instance.create killer, 995, 5000, character.get_position
      else
        character.set_health character.get_health_max
        position = character.get_position
        character.teleport DEATH_POSITION, false
        if killer.instance_of? Player
          GroundItem.get_instance.create killer, 526, 1, position
        else
          GroundItem.get_instance.create character, 526, 1, position
        end
      end
      release
      stop
    end
  end

  def stopall
    character.stop_action
    killer.stop_action
  end
  
end