require 'java'
java_import 'org.apollo.game.action.Action'

class NpcCombat < Action
  
  attr_reader :started, :other, :retaliate
  
  def initialize(player, other, retaliate)
    super 3, true, player
    @started = false # Always set this to false.
    @other = other # The other entity.
    @retaliate = retaliate # True if retaliated, false if not.
  end
  
  def execute
    if not @started
      other.start_facing get_character.get_index
      other.get_melee_set.set_attacker get_character
      @started = true
    end
    if Combat.isFighting(other)
      damageCharacter(other)
    else
      stop
    end
  end
  
  def damageCharacter(character)
    if character.is_dead
      character.send_message "Oh dear, you are dead."
      Combat.stopAction(character, get_character)
    else
      if character.is_active
        weapon = Combat.getWeapon get_character
        get_character.play_animation retaliate ? Combat.getNpcAttackAnimation(weapon) : Combat.getPlayerAnimation(weapon)
        set_delay Combat.getWeaponDelay(weapon)
        character.damage_entity rand(3)
      else
        stop
      end
    end
  end
  
  def equals(other)
    return (get_class == other.get_class)
  end
  
end