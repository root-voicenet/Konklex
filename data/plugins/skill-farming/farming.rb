require 'java'
java_import 'org.apollo.game.action.DistancedAction'
java_import 'org.apollo.game.model.Animation'
java_import 'org.apollo.game.event.impl.ConfigEvent'
java_import 'org.apollo.game.model.World'
java_import 'org.apollo.game.scheduling.ScheduledTask'

DIBBER_ANIMATION = Animation.new(2291)
RAKING_ANIMATION = Animation.new(2273)
HERB_ANIMATION = Animation.new(2274)
PICKING_ANIMATION = Animation.new(2282)
COMPOST_ANIMATION = Animation.new(2283)
WATER_ANIMATION = Animation.new(2293)
CURE_ANIMATION = Animation.new(2288)

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
      elsif state.completed
        if patch.id == 1
          state.set_stage state.stage+1
          others = find_other character, id
          character.send ConfigEvent.new(patch.config, (patch.value * state.stage) + others)
        else
          character.send_message "Looks like its ready to be gathered."
        end
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
      if state.completed or (patch.id == 1 and state.stage-1 == state.finish)
        if patch.id == 1
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
      character.play_animation((patch.id == 3) ? HERB_ANIMATION : PICKING_ANIMATION)
      skills = character.skill_set
      skills.add_experience Skill::FARMING, state.seed.exp
      if rand(4) == 1
        others = find_other character, id
        character.send ConfigEvent.new(state.patch.config, others)
        STATES[character.name][id] = State.new(-1, 3, 3, 3, 3)
        stop
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

class CompostAction < DistancedAction

  attr_reader :patch, :position, :object, :item

  def initialize(player, patch, position, object, item)
    super 0, true, player, position, (patch.id == 4) ? 1 : 3
    @patch = patch
    @position = position
    @object = object
    @item = item
  end

  def executeAction
    state = STATES[character.name]
    if state != nil
      state = state[object]
      if state != nil and (state.stage <= 3 or state.seed != nil)
        state.set_compost(item == 6032 ? 1 : 2)
        character.play_animation COMPOST_ANIMATION
      end
    end
    stop
  end

  def equals(other)
    return (get_class == other.get_class and @patch == other.patch and @position == other.position)
  end

end

class CureAction < DistancedAction

  attr_reader :patch, :position, :id, :started

  def initialize(player, patch, position, id)
    super 1, true, player, position, (patch.id == 4) ? 1 : 3
    @patch = patch
    @position = position
    @id = id
    @started = false
  end

  def executeAction
    if patch.id == 3
      stop
      return
    end
    if not started
      character.play_animation CURE_ANIMATION
      @started = true
    else
      state = STATES[character.name]
      if state != nil
        state = state[id]
        if state != nil and state.stage > 3 and state.seed != nil and state.disease_finish != -1 and not state.completed and state.diseased and not state.dead
          stage_slot = get_id state
          if stage_slot == nil # Check
            stop
            return
          end
          normalstage = state.seed.start.to_a[stage_slot]
          state.set_stage normalstage
          others = find_other character, id
          character.send ConfigEvent.new(patch.config, (patch.value * normalstage) + others)
          character.inventory.remove 6036, 1
        else
          stop
        end
      end
      stop
    end
  end

  def equals(other)
    return (get_class == other.get_class and @patch == other.patch and @position == other.position)
  end

end

class WaterAction < DistancedAction

  attr_reader :patch, :position, :id, :item, :started

  def initialize(player, patch, position, id, item)
    super 1, true, player, position, (patch.id == 4) ? 1 : 3
    @patch = patch
    @position = position
    @id = id
    @item = item
    @started = false
  end

  def executeAction
    if patch.id == 3 or patch.id == 1
      stop
      return
    end
    if not started
      character.play_animation WATER_ANIMATION
      @started = true
    else
      state = STATES[character.name]
      if state != nil
        state = state[id]
        if state != nil and state.stage > 3 and state.seed != nil and state.water_finish != -1 and not state.completed and not state.diseased and not state.dead
          stage_slot = get_id state
          if stage_slot == nil # Check
            stop
            return
          end
          waterstage = state.seed.water_start.to_a[stage_slot]
          if waterstage == nil
            waterstage = state.seed.start.to_a[stage_slot]
          end
          state.set_stage waterstage
          others = find_other character, id
          character.send ConfigEvent.new(patch.config, (patch.value * waterstage) + others)
          character.inventory.remove item, 1
          if item-1 == 5332
            character.inventory.add 5331, 1
          else
            character.inventory.add item-1, 1
          end
        else
          stop
        end
      end
      stop
    end
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
          newstate = State.new(seed.start.to_a[0], seed.finish, seed.water_finish, seed.disease_finish, seed.dead_finish)
          newstate.set_patch state.patch
          newstate.set_compost state.compost
          newstate.set_seed seed
          replace_stage character, id, newstate
          character.inventory.remove seed.seed, 3
          character.play_animation DIBBER_ANIMATION
          character.send_message "You plant the seed."
          skills = character.skill_set
          skills.add_experience Skill::FARMING, seed.exp/2
          World.world.schedule GrowCrop.new(character, id, patch)
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

  attr_reader :player, :id, :patch

  def initialize(player, id, patch)
    super 100,  false
    @player = player
    @id = id
    @patch = patch
  end

  def execute
    state = STATES[player.name]
    if state != nil
      state = state[id]
      if state != nil and player.is_active
        others = find_other player, id
        stage_slot = get_id state
        if stage_slot != nil then stage_slot += 1 end
        if patch.id == 4 or patch.id == 2 # Flower & Allotment
          allot_flower state, stage_slot, others
        elsif patch.id == 3 # Herb
          herb state, others
        elsif patch.id == 1 # Trees
          tree state, stage_slot, others
        end
      else
        stop
      end
    else
      stop
    end
  end

  def allot_flower(state, stage_slot, others)
    if not state.dead
      if (state.completed or state.stage == state.finish-1) and not state.diseased
        state.set_stage(state.finish)
        player.send ConfigEvent.new(patch.config, (patch.value * state.stage) + others)
        stop
        return
      end
      if state.diseased
        deadstage = state.seed.dead_start.to_a[stage_slot]
        state.set_stage deadstage
      elsif not state.watered
        if not state.fertile
          if stage_slot != nil
            diseasestage = state.seed.disease_start.to_a[stage_slot]
            state.set_stage diseasestage
          else
            state.set_stage nil
          end
        else
          normalstage = state.seed.start.to_a[stage_slot]
          state.set_stage normalstage
          state.set_compost(state.compost-1)
        end
      elsif state.watered
        normalstage = state.seed.start.to_a[stage_slot]
        state.set_stage normalstage
      end
      if (state.stage == nil)
        stage_slot = state.seed.start.to_a.length
        tempstage = state.seed.start.to_a[stage_slot-1]
        player.send ConfigEvent.new(patch.config, (patch.value * tempstage) + others)
        state.set_stage state.finish
      else
        player.send ConfigEvent.new(patch.config, (patch.value * state.stage) + others)
      end
    else
      stop
    end
  end

  def herb(state, others)
    if state.completed
      state.set_stage(state.finish)
      player.send ConfigEvent.new(patch.config, (patch.value * state.stage) + others)
      stop
      return
    else
      state.set_stage(state.stage+1)
      player.send ConfigEvent.new(patch.config, (patch.value * state.stage) + others)
    end
  end

  def tree(state, stage_slot, others)
    if (state.completed or state.stage == state.finish-1) and not state.diseased
      state.set_stage(state.finish)
      player.send ConfigEvent.new(patch.config, (patch.value * state.stage) + others)
      stop
      return
    end
    if state.fertile
      state.set_stage(state.stage+1)
      state.set_compost(state.compost-1)
    elsif state.diseased
      deadstage = state.seed.dead_start.to_a[stage_slot]
      state.set_stage deadstage
    elsif !state.fertile
      diseasestage = state.seed.disease_start.to_a[stage_slot]
      state.set_stage diseasestage
    end
    if (state.stage == nil)
      stage_slot = state.seed.start.to_a.length
      tempstate = state.seed.start.to_a[stage_slot-1]
      state.set_stage state.finish
      player.send ConfigEvent.new(patch.config, (patch.value * tempstate) + others)
    else
      player.send ConfigEvent.new(patch.config, (patch.value * state.stage) + others)
    end
  end

end

def get_id(state)
  for i in 0..state.seed.start.to_a.length
    if state.seed.start.to_a[i] == state.stage
      return i
    elsif state.seed.water_start.to_a[i] == state.stage
      return i
    elsif state.seed.disease_start.to_a[i] == state.stage
      return i
    elsif state.seed.dead_start.to_a[i] == state.stage
      return i
    end
  end
  return nil
end

def find_other(character, object)
  allotment = STATES[character.name]
  code = 0
  if allotment != nil
    current = allotment[object]
    if current != nil
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
  if patch != nil
    if event.id == 5341
      newstate = State.new(-1, 3, 3, 3, 3)
      newstate.set_patch patch
      append_state player, event.object, newstate # Ensures there is a patch
      player.start_action RakeAction.new(player, patch, event.position, event.object)
      ctx.break_handler_chain
    elsif event.id == 6036
      player.start_action CureAction.new(player, patch, event.position, event.object)
      ctx.break_handler_chain
    elsif (event.id == 6032 or event.id == 6034)
      player.start_action CompostAction.new(player, patch, event.position, event.object, event.id)
      ctx.break_handler_chain
    elsif (event.id >= 5333 and event.id <= 5340)
      player.start_action WaterAction.new(player, patch, event.position, event.object, event.id)
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
end