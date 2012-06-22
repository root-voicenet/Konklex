# Let scheduled tasks execute these...
# Brainstorm...

STATES = {}

class State
  attr_reader :start, :finish, :water_finish, :disease_finish, :dead_finish, :stage, :seed, :patch, :compost

  def initialize(start, finish, water_finish, disease_finish, dead_finish)
    @start = start
    @finish = finish
    @water_finish = water_finish
    @disease_finish = disease_finish
    @dead_finish = dead_finish
    @stage = start
    @compost = 0
  end

  def set_stage(stage)
    @stage = stage
  end

  def set_seed(seed)
    @seed = seed
  end

  def set_patch(patch)
    @patch = patch
  end

  def set_compost(compost)
    @compost = compost
  end

  def completed
    return (stage == finish or stage == water_finish or stage == disease_finish or stage == dead_finish)
  end

  def watered
    return seed.water_start.include?(stage)
  end

  def diseased
    return seed.disease_start.include?(stage)
  end

  def dead
    return seed.dead_start.include?(stage)
  end

  def fertile
    return (compost == 1 or compost == 2)
  end

end

def set_stage(player, object, stage)
  if (STATES[player.name]) != nil
    if (STATES[player.name][object]) != nil
      state = STATES[player.name][object]
      state.set_stage stage
    end
  end
end

def append_state(player, object, state)
  if (STATES[player.name]) == nil
    STATES[player.name] ||= {}
  end
  if (STATES[player.name][object]) == nil
    STATES[player.name][object] = state
  end
end

def replace_stage(player, object, state)
  if STATES[player.name] != nil
    if STATES[player.name][object] != nil
      STATES[player.name][object] = state
    end
  end
end