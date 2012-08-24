require 'java'
java_import 'org.apollo.game.model.World'
java_import 'org.apollo.game.action.DistancedAction'

class StoreAction < DistancedAction

  attr_reader :store

  def initialize(player, position, store)
    super 1, true, player, position, 1
    @store = store
  end

  def executeAction
    World.get_world.get_stores.open_shop character, store
    stop
  end

  def equals(other)
    return (get_class == other.get_class)
  end

end

STORES = {}

def append_store(npc, id)
  STORES[npc] = id
end

on :event, :npc_option do |ctx, player, event|
  if event.get_option == 3
    store = STORES[event.get_npc.get_id]
    if store != nil
      player.start_action StoreAction.new(player, event.get_npc.get_position, store)
    end
  end
end

append_store 526, 1
append_store 527, 1
append_store 521, 1
append_store 530, 1
append_store 548, 2
append_store 546, 3
append_store 550, 7
append_store 549, 8
append_store 593, 75
append_store 553, 6
append_store 589, 28
append_store 577, 45
append_store 575, 59
append_store 519, 58
append_store 576, 10
append_store 563, 61