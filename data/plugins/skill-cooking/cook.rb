require 'java'
java_import 'org.apollo.game.action.Action'
java_import 'org.apollo.game.model.Animation'
java_import 'org.apollo.game.model.EquipmentConstants'
java_import 'java.security.SecureRandom'

COOK_ANIMATION = Animation.new(833)
COOK_FIRE = Animation.new(897)
COOK_GAUNTLETS = 775

class Cook < Action

 attr_reader :player, :item, :event, :gauntlets, :cooked

  def initialize(player, item, event)
    super 4, true, player
    @player = player
    @item = item
    @event = event
  end

  def getSuccess(burnBonus, levelReq, stopBurn)
    if @event.get_object == 2728
      burnBonus += 3.0
    else
      if @event.get_object == 114
        burnBonus += 3.0
      end
    end
    burn_chance = (55.0 - burnBonus)
    cook_level = @player.skill_set.get_skill(Skill::COOKING).maximum_level
    lev_needed = levelReq
    burn_stop = stopBurn
    multi_a = (burn_stop - lev_needed)
    burn_dec = (burn_chance / multi_a)
    multi_b = (cook_level - lev_needed)
    burn_chance -= (multi_b * burn_dec)
    randNum = SecureRandom.new().next_double * 100.0
    return burn_chance <= randNum
  end

  def has_gauntlets
    hand = @player.get_equipment.get(EquipmentConstants::HANDS)
    skill = @player.skill_set.get_skill(Skill::COOKING).maximum_level
    if hand != nil and hand.get_id == COOK_GAUNTLETS
      @gauntlets = true
      @cooked = getSuccess(5.0, @item.level, @item.burnlevel)
    else
      @gauntlets = false
      @cooked = getSuccess(0.0, @item.level, @item.burnlevel)
    end
  end

  def start
    if @item.level > @player.skill_set.get_skill(Skill::COOKING).maximum_level
      @player.send_message "You need a Cooking level of #{@item.level} to cook this."
      stop
      return
    end
    has_gauntlets
    @player.turn_to @event.position
    @player.play_sound 240, true
    @player.play_animation(@event.get_object == 2732 ? COOK_FIRE : COOK_ANIMATION)
    @player.inventory.remove @item.uncook
  end

  def execute
    if @player.inventory.contains @item.uncook
      start
      if @cooked
        cook
      else
        burn
      end
    else
      stop
    end
  end

  def cook
    if @player.inventory.add @item.cook
      item_def = ItemDefinition.for_id @item.cook
      name = item_def.name.sub(/ item$/, "").downcase
      @player.send_message "You cook the #{name}."
      @player.skill_set.add_experience Skill::COOKING, @item.exp
    else
      stop
    end
  end

  def burn
    if @player.inventory.add @item.burnt
      @player.send_message "You burn the fish."
    else
      stop
    end
  end

  def equals(other)
    return (get_class == other.get_class)
  end

end

on :event, :item_used_on_object do |ctx, player, event|
  if event.get_object == 2728 or event.get_object == 114 or event.get_object == 2732
    item = FOODS_[event.get_id]
    if item != nil
      player.start_action Cook.new(player, item, event)
      ctx.break_handler_chain
    end
  end
end
