NPCZ = {}

class Npcz
  attr_reader :id, :level, :exp, :loot
  
  def initialize(id, level, exp, loot)
    @id = id
    @level = level
    @exp = exp
    @loot = loot
  end
end

def append_npc(npc)
  NPCZ[npc.id] = npc
end

append_npc Npcz.new(1, 1, 8, { 995 => 3 })