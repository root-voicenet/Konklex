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

append_npc Npcz.new(1 ,  1,  8 , { 995 => 3 })
append_npc Npcz.new(4 ,  1,  8 , { 995 => 3 })
append_npc Npcz.new(18, 25, 26 , { 995 => 18})
append_npc Npcz.new(9 , 40, 47 , { 995 => 30})
append_npc Npcz.new(26, 55, 84 , { 995 => 50})
append_npc Npcz.new(20, 70, 152, { 995 => 80, 562 => 2})
append_npc Npcz.new(21, 80, 273, { 995 => 200, 995 => 300, 560 => 2,  565 => 1, 569 => 1, 1601 => 1, 444 => 1, 245 => 1 })