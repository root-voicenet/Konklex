require 'java'
java_import 'org.apollo.game.action.Action'
java_import 'org.apollo.game.model.Animation'
java_import 'org.apollo.game.model.Item'
java_import 'org.apollo.game.model.def.ItemDefinition'
java_import 'org.apollo.game.model.inter.EnterAmountListener'
java_import 'org.apollo.game.model.inter.dialog.DialogueAdapter'

GRINDING_ANIM = Animation.new(364)
PESTLE_MORTAR = 233

# An ingredient which can be used for making (unfinished) potions.
class Ingredient
  attr_reader :item_id, :item

  def initialize(item)
    @item_id = item
    @item = Item.new(item) # Share item instances.
  end

  # Checks if the specified player has the specified amount of this
  # ingredient. Optionally, they can immediately be removed if that
  # amount was indeed found.
  def check_remove(player, amount, remove)
    inventory = player.inventory
	counter = 0

	inventory.items.each do |inv_item|
	  break unless counter < amount

	  if inv_item == nil
	    next
	  end

	  id = inv_item.id
	  amt = inv_item.amount

	  if id == @item_id
	    if amt >= amount
		  if remove
		    inventory.remove @item_id, amount
		  end
		  return true
		else
		  counter += amt
		end
	  end
	end

	if counter >= amount
	  if remove
	    inventory.remove @item_id, amount
	  end
	  return true
	end

	return false
  end
end

# An ingredient which needs to be grinded before being usable for Herblore.
class GrindedIngredient < Ingredient
  include HerbloreMethod

  attr_reader :raw

  def initialize(item_id, raw)
    super item_id

	@raw = raw
  end

  def invoke(player, pestle_mortar, ingredient)
    action = GrindingAction.new(player, self)
	listener = GrindingDialogueListener.new(player, action)

	open_dialogue player, @item_id, listener
  end
end

# A DialogueAdapter used for grinding ingredients. It is also used
# as an EnterAmountListener for the amount of grinding actions.
class GrindingDialogueListener < DialogueAdapter
  include EnterAmountListener

  attr_reader :player, :action

  def initialize(player, action)
    super()
	
    @player = player
	@action = action
  end

  # Called when a button has been clicked whilst the dialogue was opened.
  def buttonClicked(button)
	amount = get_amount button
	if amount == 0
	  return false
	end

	interfaces = @player.interface_set
	interfaces.close

	if amount == -1
	  interfaces.open_enter_amount_dialogue(self)
	  return true
	end

	if amount == -2
	  amount = player.inventory.get_amount @action.ingredient.raw
	end
    execute amount  
  end

  # Called when an amount of grinding actions has been entered.
  def amountEntered(amount)
    if amount <= 0
	  return
	end
	execute amount
  end

  # Called to set the action(s) in motion.
  def execute(amount)
    @action.set_amount amount
	@player.start_action @action
  end

  # Gets the amount of actions based on the specified button id.
  def get_amount(button)
    case button
      when 2799 then return 1
	  when 2798 then return 5
	  when 1748 then return -1
	  when 1747 then return -2
	end
    return 0
  end
end

# An action which makes the player grind one or more 
# GrindedIngredients from their 'raw' form.
class GrindingAction < Action
  attr_reader :ingredient, :amount, :pulses, :slot, :listener

  def initialize(player, ingredient)
    super 0, true, player

	@ingredient = ingredient
	@pulses = 0
  end

  def execute
	grind
	@pulses += 1
  end

  # Performs the grinding action once the materials have been checked.
  def grind
    if @pulses == 0
	  character.play_animation GRINDING_ANIM
	elsif @pulses == 1
	  if not gather_materials
	    stop
		return
	  end

	  player = character
	  inventory = player.inventory
	  item = inventory.get @slot

	  player.send_message "You grind the " + item.definition.name.downcase + " to dust."

	  inventory.remove_slot @slot, 1
	  inventory.add @ingredient.item
	  
	  set_delay 1
	elsif @pulses == 2
	  character.stop_animation
	  continue
	end
  end

  # Checks if the player has the required materials to
  # perform the (next) action.
  def gather_materials
    items = character.inventory.items
	
	pst_mrt = false
	ingr = false
	raw = @ingredient.raw
	(0...items.length).each do |slot|
	  item = items[slot]
	  if item == nil
	    next
	  end

	  id = item.id
	  if id == PESTLE_MORTAR and !pst_mrt
	    pst_mrt = true
	  elsif id == raw and !ingr
	    ingr = true
		@slot = slot
	  end

	  if pst_mrt and ingr
	    return true
	  end
	end

	ingr = ItemDefinition.for_id(raw).name.downcase
	character.send_message "You do not have any more " + ingr + "s."
	return false
  end
  
  # Either invokes the stop() method in Action to shut it down
  # or continues to the next ingredient.
  def continue
    @amount -= 1
	
	if @amount > 0
	  set_delay 0
	  @pulses = -1
	else
	  stop
	end
  end

  # Sets the amount of actions.
  def set_amount(amount)
    @amount = amount
  end

  def stop
    super
	if @listener != nil
	  character.inventory.remove_listener @listener
	end
  end
  
  def equals(other)
    return (get_class == other.get_class and @ingredient == other.ingredient)
  end
end

# Appends a grinded ingredient to the ItemOnItemEvent handler interception.
def append_grinded(id, raw)
  grinded = GrindedIngredient.new(id, raw)
  append_herblore_item grinded, PESTLE_MORTAR, raw
  return grinded
end

# Normal ingredients
EYE_NEWT 			= Ingredient.new(221)
RED_SPIDERS_EGGS	= Ingredient.new(223)
LIMPWURT_ROOT		= Ingredient.new(225)
SNAPE_GRASS			= Ingredient.new(231)
WHITE_BERRIES		= Ingredient.new(239)
WINE_ZAMORAK		= Ingredient.new(245)
JANGERBERRIES		= Ingredient.new(247)
TOADS_LEGS			= Ingredient.new(2152)
MORT_MYRE_FUNGI		= Ingredient.new(2970)
POTATO_CACTUS		= Ingredient.new(3138)
PHOENIX_FEATHER		= Ingredient.new(4621)
FROG_SPAWN			= Ingredient.new(5004)
PAPAYA_FRUIT		= Ingredient.new(5972)
POISON_IVY_BERRIES	= Ingredient.new(6018)
YEW_ROOTS			= Ingredient.new(6049)
MAGIC_ROOTS			= Ingredient.new(6051)

# Grinded ingredients
UNICORN_HORN_DUST	= append_grinded 235, 237
DRAGON_SCALE_DUST	= append_grinded 241, 243
CHOCOLATE_DUST		= append_grinded 1975, 1973
CRUSHED_NEST		= append_grinded 6693, 5075
GROUND_MUD_RUNE		= append_grinded 9594, 4698
