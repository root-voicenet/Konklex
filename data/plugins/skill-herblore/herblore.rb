# Thanks to Sillhouette <http://www.rune-server.org/members/silhouette/> for posting
# a large amount of Herblore skill data which has been thankfully used in this plugin.

require 'java'
java_import 'org.apollo.game.event.impl.SetInterfaceItemModelEvent'
java_import 'org.apollo.game.model.Skill'

HERBLORE_ID = Skill::HERBLORE
HERBLORE_DIALOGUE = 4429

HERBLORE_ITEM = {}
HERBLORE_ITEM_ITEM = {}

# A module which describes an invocable method of the Herblore skill.
module HerbloreMethod
  def self.new
    raise "You cannot instantiate this module!"
  end

  def invoke(player, primary, secondary)
    raise NotImplementedError.new("You must implement the invocation of HerbloreMethod!")
  end
end

# The ItemOnItemEvent handler for all Herblore-related functions.
on :event, :item_on_item do |ctx, player, event|
  primary = event.id
  secondary = event.target_id
  hash = HERBLORE_ITEM_ITEM[primary]

  if hash == nil
    secondary = event.id
    primary = event.target_id
	hash = HERBLORE_ITEM_ITEM[primary]
  end

  if hash != nil
    method = hash[secondary]
	if method != nil
	  method.invoke player, primary, secondary
	  ctx.break_handler_chain
	end
  end
end

# The ItemOptionEvent handler for all Herblore-related functions.
on :event, :item_option do |ctx, player, event|
  if event.option == 1
    id = event.id
    method = HERBLORE_ITEM[id]
	if method != nil
	  method.invoke player, id, event.slot
	  ctx.break_handler_chain
	end
  end
end

# Utility for adding the various Herblore methods to the handled constant arrays.
def append_herblore_item(method, key, secondary = -1)
  if secondary == -1
    HERBLORE_ITEM[key] = method
  else
	hash = HERBLORE_ITEM_ITEM[key]
	if hash == nil
	  hash = {}
	end

	hash[secondary] = method
	HERBLORE_ITEM_ITEM[key] = hash
  end
end

# Utility method for checking if a player's inventory has an item of the specified
# id, with optionally the specified amount (1 by default), at the specified slot.
def check_slot(player, slot, id, amount = 1)
  item = player.inventory.get slot
  return (item != nil and item.id == id and item.amount >= amount)
end

# Utility method for checking if a player's Herblore (maximum) level is at a
# required height. Also informs the player if this is not the case with use
# of the action variable. Like so:
#   "You need a Herblore level of at least #{required.to_s} to #{action}."
def check_skill(player, required, action)
  if required > player.skill_set.skill(HERBLORE_ID).maximum_level
    character.send_message "You need a Herblore level of at least " + required.to_s + " to " + action + "."
    return false
  end
  return true
end

# Opens a 'make' dialogue for the specified player, displaying the specified item.
# Optionally, a listener can be used for the dialogue.
def open_dialogue(player, item, listener=nil)
  player.send SetInterfaceItemModelEvent.new(1746, item, 170)
  player.interface_set.open_dialogue listener, HERBLORE_DIALOGUE
end