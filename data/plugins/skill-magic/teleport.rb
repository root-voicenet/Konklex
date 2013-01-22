# Thanks to phl0w <http://www.rune-server.org/members/phl0w/> for providing
# the correct destination coordinates of the ancient teleports.

require 'java'
java_import 'org.apollo.game.model.Animation'
java_import 'org.apollo.game.model.Graphic'
java_import 'org.apollo.game.model.Position'

TELEPORT_SPELLS = {}

MODERN_TELE_ANIM = Animation.new(714)
MODERN_TELE_END_ANIM = Animation.new(715)
MODERN_TELE_GFX = Graphic.new(308, 15, 100)

ANCIENT_TELE_END_GFX = Graphic.new(455)
ANCIENT_TELE_ANIM = Animation.new(1979)
ANCIENT_TELE_GFX = Graphic.new(392)

class TeleportSpell < Spell
  attr_reader :ancient, :destination, :experience, :name
  
  def initialize(ancient, level, elements, destination, experience, name)
    super level, elements, experience
    @ancient = ancient
	@destination = destination
	@name = name
	@name.freeze
  end
end

class TeleportingAction < SpellAction
  
  def initialize(character, spell)
    super character, spell
  end
  
  def execute_action
    if !(@spell.ancient) then execute_modern else execute_ancient end
  end
  
  def execute_modern
    player = character
    if @pulses == 0
	  player.play_animation MODERN_TELE_ANIM
	  player.skill_set.add_experience MAGIC_ID, @spell.experience
	elsif @pulses == 1
	  player.play_graphic MODERN_TELE_GFX
	  set_delay 1
	elsif @pulses == 2
	  player.stop_graphic
	  player.play_animation MODERN_TELE_END_ANIM
	  
	  destination = @spell.destination
	  
	  oldPosition = player.position
	  player.teleport destination
	  
	  if oldPosition.is_within_distance destination, 50
	    character.send_message "Welcome to " + @spell.name + "."
      end
	  stop
	end
  end
  
  def execute_ancient
    player = character
    if @pulses == 0
	  player.play_graphic ANCIENT_TELE_GFX
	  player.play_animation ANCIENT_TELE_ANIM
	  set_delay 2
	elsif @pulses == 1
	  player.stop_graphic
	  player.stop_animation
	  player.teleport @spell.destination, true
	  player.skill_set.add_experience MAGIC_ID, @spell.experience
	  stop
	end
  end
end

def append_tele(ancient, button, level, elements, x, y, experience, name)
  teleport = TeleportSpell.new(ancient, level, elements, Position.new(x, y, 0), experience, name)
  TELEPORT_SPELLS[button] = teleport
end

# Modern teleports
append_tele false, 1164, 25, { FIRE => 1, AIR => 3, LAW => 1 }, 3213, 3424, 35, "Varrock"
append_tele false, 1167, 31, { EARTH => 1, AIR => 3, LAW => 1 }, 3222, 3219, 41, "Lumbridge"
append_tele false, 1170, 37, { WATER => 1, AIR => 3, LAW => 1 }, 2965, 3379, 47, "Falador"
append_tele false, 1174, 45, { AIR => 5, LAW => 1 }, 2757, 3478, 55.5, "Camelot"
append_tele false, 1540, 51, { WATER => 2, LAW => 2 }, 2662, 3306, 61, "Ardougne"
append_tele false, 1541, 58, { EARTH => 2, LAW => 2 }, 2549, 3114, 68, "the Watchtower"
append_tele false, 7455, 61, { FIRE => 2, LAW => 2 }, 2871, 3590, 68, "Trollheim"
append_tele false, 18470, 64, { FIRE => 2, WATER => 2, LAW => 2, Elemen.new([1963], nil, "Banana") => 1 }, 2754, 2785, 76, "Ape Atoll"

# Ancient teleports
append_tele true, 13035, 54, { LAW => 2, FIRE => 1, AIR => 1 }, 3098, 9882, 64, "Paddewwa"
append_tele true, 13045, 60, { LAW => 2, SOUL => 2 }, 3320, 3338, 70, "Senntisten"
append_tele true, 13053, 66, { LAW => 2, BLOOD => 1 }, 3493, 3472, 76, "Kharyll"
append_tele true, 13061, 72, { LAW => 2, WATER => 4 }, 3003, 3470, 82, "Lassar"
append_tele true, 13069, 78, { LAW => 2, FIRE => 3, AIR => 2 }, 2966, 3696, 88, "Dareeyak"
append_tele true, 13079, 84, { LAW => 2, SOUL => 2 }, 3163, 3664, 94, "Carrallangar"
append_tele true, 13087, 90, { LAW => 2, BLOOD => 2 }, 3287, 3883, 100, "Annakarl"
append_tele true, 13095, 96, { LAW => 2, WATER => 8 }, 2972, 3873, 106, "Ghorrock"
