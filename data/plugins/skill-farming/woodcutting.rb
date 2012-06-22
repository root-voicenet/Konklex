require 'java'
java_import 'org.apollo.game.event.impl.ConfigEvent'

class FarmingWoodcuttingAction < WoodcuttingAction

  attr_reader :patch, :state

  def initialize(character, position, log, id, patch, state)
    super character, position, log, id
    @patch = patch
    @state = state
  end

  def expirew(position)
    state.set_stage(state.stage + 1)
    others = find_other character, id
    character.send ConfigEvent.new(patch.config, (patch.value * state.stage) + others)
  end

  def equals(other)
    return (get_class == other.get_class and @state == other.state)
  end

end