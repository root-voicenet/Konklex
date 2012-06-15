require 'java'
java_import 'org.apollo.game.action.Action'
java_import 'org.apollo.game.model.Animation'
java_import 'org.apollo.game.event.impl.SetInterfaceTextEvent'
java_import 'org.apollo.game.event.impl.SetInterfaceItemModelEvent'

FLETCHING_ANIMATION = Animation.new(1248)

class FletchingAction < Action
  attr_reader :item, :fletched, :log

  def initialize(player, item, log)
    super 3, true, player
    @item = item
    @log = log
    @fletched = 0
  end

  def execute
    skills = character.skill_set
    level = skills.skill(Skill::FLETCHING).maximum_level # TODO: is using max level correct?

    # verify counted fletches
    if fletched == item.count
      stop
      return
    end

    # verify level requirements
    if item.level > level
      character.send_message "You need a Fletching level of at least #{item.level} to fletch this."
      stop
      return
    end

    # verify item requirements
    if not (character.inventory.contains log)
      character.send_message "You do not have the required logs to fletch this."
      stop
      return
    end

    # start fletcing fella's
    if fletched % 2 == 0 then character.play_animation FLETCHING_ANIMATION end
    character.inventory.remove log, 1
    character.inventory.add item.item
    skills.add_experience Skill::FLETCHING, item.xp
    @fletched += 1

  end

end

class FletchingListener
  java_implements 'org.apollo.game.model.inter.dialog.DialogueListener'

  attr_reader :log

  def initialize(log)
    @log = log
  end

  def buttonClicked(player, button)
    bow = BOWS[log][button]
    if bow != nil
      player.start_action FletchingAction.new(player, bow, log)
    end
    player.get_interface_set.close
  end

  def interfaceClosed(player, manually)
  end

  def continued(player)
  end

end

class StringingAction < Action
  attr_reader :item

  def initialize(player, item)
    super 0, true, player
    @item = item
  end

  def execute
    skills = character.skill_set
    level = skills.skill(Skill::FLETCHING).maximum_level # TODO: is using max level correct?

    # verify level requirements
    if item.level > level
      character.send_message "You need a Fletching level of at least #{item.level} to string this."
      stop
      return
    end

    # verify item requirements
    if not (character.inventory.contains item.bow)
      character.send_message "You do not have the required bow to string this."
      stop
      return
    end

    # verify item requirements
    if not (character.inventory.contains 1777)
      character.send_message "You do not have any bow string to string this."
      stop
      return
    end

    # start fletcing fella's
    if fletched % 2 == 0 then character.play_animation FLETCHING_ANIMATION end
    character.inventory.remove 1777, 1  
    character.inventory.remove item.bow, 1
    character.inventory.add item.item
    skills.add_experience Skill::FLETCHING, item.exp
    @fletched += 1

  end

end

def open_window(player, item)
  short_def = ItemDefinition.for_id item.bow # TODO: split off into some method
  long_def = ItemDefinition.for_id item.model # TODO: split off into some method
  if item.bow == 839
    player.send SetInterfaceTextEvent.new(8879, "What would you like to make?")
    player.send SetInterfaceItemModelEvent.new(8885, 52, 190)
    player.send SetInterfaceTextEvent.new(8897, "Arrow Shafts")
  end
  player.send SetInterfaceTextEvent.new(item.bow == 839 ? 8889 : 8874, short_def.name)
  player.send SetInterfaceTextEvent.new(item.bow == 839 ? 8893 : 8878, long_def.name)
  player.send SetInterfaceItemModelEvent.new(item.bow == 839 ? 8884 : 8869, item.bow, 190)
  player.send SetInterfaceItemModelEvent.new(item.bow == 839 ? 8883 : 8870, item.model, 190)
  player.get_interface_set.open_dialogue FletchingListener.new(item.log), item.bow == 839 ? 8880 : 8866
end

on :event, :item_on_item do |ctx, player, event|
  primary = event.id
  secondary = event.target_id

  if primary == 946
    item = ITEMS[secondary]
    if item != nil
      open_window player, item
    end
  end
end

on :event, :item_on_item do |ctx, player, event|
  primary = event.id
  secondary = event.target_id

  if primary == 1777
    item = UBOWS[secondary]
    if item != nil
      player.start_action StringingAction.new(player, item)
    end
  end
end