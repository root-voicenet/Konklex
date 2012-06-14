require 'java'
java_import 'org.apollo.game.action.Action'
java_import 'org.apollo.game.model.Animation'
java_import 'org.apollo.game.event.impl.SetInterfaceTextEvent'
java_import 'org.apollo.game.event.impl.SetInterfaceItemModelEvent'

FLETCHING_ANIMATION = Animation.new(1248)

class FletchingAction < Action
  attr_reader :item, :fletched

  def initialize(player, item)
    super 3, true, player
    @item = item
    @fletched = 0
  end

  def getPrimary(item_1, item_2)
    return item_1 == 52 or item_1 == 53 ? item_2 : item_1
  end

  def execute
    skills = character.skill_set
    level = skills.skill(Skill::FLETCHING).maximum_level # TODO: is using max level correct?

    # verify the fletching requirements
    if item.level > level
      character.send_message "You need a Fletcing level of at least #{item.level} to fletch this."
      stop
      return
    end
    
    @fletched += 1
  end

end

class FletchingListener
  java_implements 'org.apollo.game.model.inter.dialog.DialogueListener'

  def buttonClicked(player, button)
  end

  def interfaceClosed(player, manually)
  end

  def continued(player)
  end

end

def open_window(player, item)
  player.send SetInterfaceItemModelEvent.new(8884, item.bow, 250)
  player.send SetInterfaceItemModelEvent.new(8883, item.model, 250)
  player.send SetInterfaceItemModelEvent.new(8885, 52, 250)
  player.send SetInterfaceTextEvent.new("What would you like to make?", 8879)
  player.send SetInterfaceTextEvent.new("Shortbow", 8889)
  player.send SetInterfaceTextEvent.new("Longbow", 8893)
  player.send SetInterfaceTextEvent.new("Arrow Shafts", 8897)
  player.get_interface_set.open_dialogue FletchingListener.new(), 8880
end

# Find knife
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