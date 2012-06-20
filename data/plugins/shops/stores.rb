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

append_store 522, 1
append_store 553, 2