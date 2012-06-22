PATCHES = {}

class Patch
  attr_reader :id, :config, :value

  def initialize(id, config, value)
    @id = id
    @config = config
    @value = value
  end
end

def append_patch(object, id, config, value)
  PATCHES[object] = Patch.new(id, config, value)
end

append_patch 8388, 1, 502, 1        # Taverly tree
append_patch 8389, 1, 502, 256      # Falador tree
append_patch 8390, 1, 502, 65536    # Varrock tree
append_patch 8391, 1, 502, 16777216 # Lumbridge tree
append_patch 7847, 2, 508, 1        # Falador flower
append_patch 7848, 2, 508, 256      # Catherby flower
append_patch 7849, 2, 508, 65536    # Ardougne flower
append_patch 7850, 2, 508, 16777216 # Canifis flower
append_patch 8150, 3, 515, 1        # Falador herb
append_patch 8151, 3, 515, 256      # Catherby herb
append_patch 8152, 3, 515, 65536    # Ardougne flower
append_patch 8153, 3, 515, 16777216 # Canifis flower
append_patch 8550, 4, 504, 1        # Falador allotment north
append_patch 8551, 4, 504, 256      # Falador allotment south
append_patch 8552, 4, 504, 65536    # Catherby allotment north
append_patch 8553, 4, 504, 16777216 # Catherby allotment south
append_patch 8554, 4, 505, 1        # Ardougne allotment north
append_patch 8555, 4, 505, 256      # Ardougne allotment south
append_patch 8556, 4, 505, 65536    # Canifis allotment north
append_patch 8557, 4, 505, 16777216 # Canifis allotment south