# Let scheduled tasks execute these...
# Brainstorm...

STATES = {}

class State
  attr_reader :start, :finish, :stage, :seed, :patch

  def initialize(start, finish)
    @start = start
    @finish = finish
    @stage = start
  end

  def set_stage(stage)
    if stage > finish
      @stage = finish
    elsif stage < start
      @stage = start
    else
      @stage = stage
    end
  end

  def set_seed(seed)
    @seed = seed
  end

  def set_patch(patch)
    @patch = patch
  end

  def completed
    return stage == finish
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