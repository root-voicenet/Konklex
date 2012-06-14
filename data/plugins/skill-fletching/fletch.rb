require 'java'
java_import 'org.apollo.game.action.Action'
java_import 'org.apollo.game.model.Animation'

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
    level = skills.get_skill(Skill::FLETCHING).maximum_level # TODO: is using max level correct?

    # verify the fletching requirements
    if item.level > level
      character.send_message "You need a Fletcing level of at least #{bar.level} to fletch this."
      stop
      return
    end
    
    @fletched += 1
  end

end

# Find knife
on :event, :ItemOnItem do |ctx, player, event|
end