require 'java'

java_import 'org.apollo.game.action.DistancedAction'
java_import 'org.apollo.game.model.EquipmentConstants'
java_import 'org.apollo.game.model.def.ItemDefinition'

# Not fully done yet.. getting there
# Bugs need to be in a certain spot to cut
# Jimmy pointed a fix to it but I haven't done it yet..

LOG_SIZE = 1

class WoodcuttingAction < DistancedAction

attr_reader :position, :log, :counter, :started, :again

def initialize(character, position, log)
 super 0, true, character, position, ORE_SIZE
 @position = position
 @log = log
 @started = false
 @again = false
 @counter = 0
end

# Finds if you have the hatchet
def find_hatchet
  HATCHET_ID.each do |id|
  weapon = character.equipment.get EquipmentConstants::WEAPON
  if weapon.id == id
    return HATCHET[id]
  end
  if character.inventory.contains id
    return HATCHET[id]
  end
 end
 return nil
end

# The Chopping action/animation/message
def start_chopping(hatchet)
  @started = true
  if not @again
  	character.send_message "You swing your hatchet at the tree."
  end
  character.turn_to @position
  character.play_animation hatchet.animation
  @counter = hatchet.pulses
end

def restart
	@again = true
	@started = false
	character.startAction WoodcuttingAction.new(character, @position, @log)
end

def executeAction
  skills = character.skill_set
  level = skills.get_skill(Skill::WOODCUTTING).maximum_level # TODO: is using max level correct?
  hatchet = find_hatchet
  free = character.inventory.freeSlots

  # verify the player can chop with their axe
  if not (hatchet != nil and level >= hatchet.level)
    character.send_message "You do not have the correct hatchet with your woodcutting level."
    stop
    return
  end

  # verify the player can chop the tree
  if log.level > level
    character.send_message "You do not have the required level to cut this tree."
    stop
    return
  end
  
  if free < 1
    character.send_message "Not enough inventory space"
    stop
    return
  end
  
if not @started
	start_chopping(hatchet)
else
	if @counter == 0
		chance = rand(4)
		if chance == 1
			if character.inventory.add log.id
				cutoff = rand(2)
				log_def = ItemDefinition.for_id @log.id
				name = log_def.name.sub(/ log$/, "").downcase
				character.send_message "You manage to get some #{name}."
				skills.add_experience Skill::WOODCUTTING, log.exp
				if cutoff == 1
					stop
					return
				end
			end
		end
		restart
	else
		if @counter == 4
			character.play_animation hatchet.animation
		end
	end
    	@counter -= 1
end
end

def equals(other)
  return (get_class == other.get_class and @position == other.position and @log == other.log)
 end
end

class ExpiredCuttingAction < DistancedAction
  
 attr_reader :position

 def initialize(character, position)
   super 0, true, character, position, LOG_SIZE
 end

 def equals(other)
  return (get_class == other.get_class and @position == other.position)
 end
end

on :event, :object_action do |ctx, player, event|
 if event.option == 1
  log = LOGS[event.id]
  free = player.inventory.freeSlots
  if log != nil
    if free > 0
      player.startAction WoodcuttingAction.new(player, event.position, log)
    else
      player.send_message "Not enough inventory space"
    end
  end
 end
end
