require 'java'
java_import 'org.apollo.game.action.DistancedAction'
java_import 'org.apollo.game.model.Animation'
java_import 'org.apollo.game.event.impl.ConfigEvent'
java_import 'org.apollo.game.model.World'
java_import 'org.apollo.game.scheduling.ScheduledTask'

DIBBER_ANIMATION = Animation.new(2291)
RAKING_ANIMATION = Animation.new(2273)
PICKING_ANIMATION = Animation.new(2282)

class FarmingAction < DistancedAction

  attr_reader :patch, :position, :id

  def initialize(player, patch, position, id)
    super 0, true, player, position, (patch.id == 4) ? 1 : 3
    @patch = patch
    @position = position
    @id = id
  end

  def executeAction
    state = STATES[character.name]
    if state != nil and state[id] != nil
      state = state[id]
      if state.stage < 3
        character.send_message "Looks like this needs some raking."
      elsif state.stage == 3
        character.send_message "Looks like I can grow something.."
      elsif stage.completed
        character.send_message "Looks like its ready to be gathered."
      elsif state.seed != nil
        character.send_message "Looks like something is growing."
      end
    else
      character.send_message "Looks like this needs some raking."
    end
    stop
  end

  def equals(other)
    return (get_class == other.get_class and @patch == other.patch and @position == other.position)
  end

end

class PickingAction < DistancedAction

  attr_reader :patch, :position, :id

  def initialize(player, patch, position, id)
    super 3, true, player, position, (patch.id == 4) ? 1 : 3
    @patch = patch
    @position = position
    @id = id
  end

  def executeAction
    state = STATES[character.name]
    if state != nil and state[id] != nil
      state = state[id]
      if state.completed
        if state.object_id == 1
          cut(state)
        else
          a_herb_pick(state)
        end
      end
    else
      stop
      return
    end
  end

  def cut(state)
    log = TREES[state.seed.item]
    character.startAction FarmingWoodcuttingAction.new(character, position, log, id, patch, state)
    stop
  end

  def a_herb_pick(state)
    if character.inventory.add state.seed.item
      character.play_animation PICKING_ANIMATION
      skills = character.skill_set
      skills.add_experience Skill::FARMING, state.seed.exp
      if rand(4) == 1
        others = find_other character, id
        character.send ConfigEvent.new(state.patch.config, others)
        STATES[character.name][id] = nil
      end
    end
  end

  def equals(other)
    return (get_class == other.get_class and @id == other.id and @state == other.state)
  end

end

class RakeAction < DistancedAction

  attr_reader :patch, :position, :id, :started

  def initialize(player, patch, position, id)
    super 4, true, player, position, (patch.id == 4) ? 1 : 3
    @patch = patch
    @position = position
    @id = id
    @started = false
  end

  def executeAction
    if not started
      character.turn_to position
      @started = true
    end
    state = STATES[character.name][id]
    if state.completed
      stop
      return
    elsif state.stage <= 3
      state.set_stage(state.stage + 1)
      others = find_other character, id
      character.send ConfigEvent.new(patch.config, (patch.value * state.stage) + others)
      character.play_animation RAKING_ANIMATION
    end
  end

  def stop
    character.stop_animation
    super
  end

  def equals(other)
    return (get_class == other.get_class and @patch == other.patch and @position == other.position)
  end

end

class DibberAction < DistancedAction

  attr_reader :seed, :patch, :position, :id

  def initialize(player, seed, patch, position, id)
    super 0, true, player, position, (patch.id == 4) ? 1 : 3
    @seed = seed
    @patch = patch
    @position = position
    @id = id
  end

  def executeAction
    skills = character.skill_set
    level = skills.get_skill(Skill::FARMING).maximum_level # TODO: is using max level correct?

    if patch.id != seed.id
      stop
      return
    end

    if seed.level > level
      character.send_message "You do not have the required level to plant this seed."
      stop
      return
    end

    state = STATES[character.name]
    if state != nil and state[id] != nil
      state = state[id]
      if state.stage == 3
        if character.inventory.contains seed.seed, 3
          character.inventory.remove seed.seed, 3
          newstate = State.new(seed.start, seed.finish)
          newstate.set_patch state.patch
          replace_stage character, id, newstate
          character.play_animation DIBBER_ANIMATION
          others = find_other character, id
          value = (patch.value * seed.start)
          finalvalue = value + others
          character.send ConfigEvent.new(patch.config, finalvalue)
          character.send_message "You plant the seed."
          skills = character.skill_set
          skills.add_experience Skill::FARMING, seed.exp/2
          newstate.set_seed seed
          World.world.schedule GrowCrop.new(character, id, patch, newstate)
        end
      end
    end
    stop
  end

  def equals(other)
    return (get_class == other.get_class and @patch == other.patch and @position == other.position)
  end

end

class GrowCrop < ScheduledTask

  attr_reader :player, :id, :patch, :state

  def initialize(player, id, patch, state)
    super 100,  false
    @player = player
    @id = id
    @patch = patch
    @state = state
  end

  def execute
    if state.completed
      stop
      return
    else
      state.set_stage(state.stage + 1)
      if player.is_active
        others = find_other player, id
        player.send ConfigEvent.new(patch.config, (patch.value * state.stage) + others)
      end
    end
  end

end

def find_other(character, object)
  allotment = STATES[character.name]
  code = 0
  if allotment != nil
    current = allotment[object]
    allotment.each do |key, state|
      if key != object
        if state.patch != nil and current.patch != nil
          if state.patch.config == current.patch.config
            code += state.patch.value*state.stage
          end
        end
      end
    end 
  end
  return code
end
      
# This varies between flowers and herbs
on :event, :object_action do |ctx, player, event|
  patch = PATCHES[event.id]
  if event.option == 2
    if patch != nil
      player.start_action FarmingAction.new(player, patch, event.position, event.id)
      ctx.break_handler_chain
    end
  elsif event.option == 1
    if patch != nil
      player.start_action PickingAction.new(player, patch, event.position, event.id)
      ctx.break_handler_chain
    end
  end
end

# Every patch uses this exact method.
on :event, :item_used_on_object do |ctx, player, event|
  patch = PATCHES[event.object]
  if patch != nil and event.id == 5341
    newstate = State.new(-1, 3)
    newstate.set_patch patch
    append_state player, event.object, newstate # Ensures there is a patch
    player.start_action RakeAction.new(player, patch, event.position, event.object)
    ctx.break_handler_chain
  else
    seed = SEEDS[event.id]
    if patch != nil and seed != nil
      if player.inventory.contains 5343
        player.start_action DibberAction.new(player, seed, patch, event.position, event.object)
        ctx.break_handler_chain
      else
        player.send_message "You do not have a seed dibber."
        ctx.break_handler_chain
      end
    end
  end
end