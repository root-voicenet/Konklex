require 'java'

STALLS = {}

class Stall

  attr_reader :item, :level, :exp

  def initialize(item, level, exp)
    @item = item
    @level = level
    @exp = exp
 end

end

def append_stall(id, stall)
  STALLS[id] = stall
end

GEMS_TH = [1623, 1621, 1619, 1617, 1631]

append_stall 2561, Stall.new([1891],   5,  16)
append_stall 2560, Stall.new([950] ,  20,  24)
append_stall 2563, Stall.new([7680],  35,  36)
append_stall 2564, Stall.new([1613],  65,  81)
append_stall 2562, Stall.new(GEMS_TH, 75, 160)