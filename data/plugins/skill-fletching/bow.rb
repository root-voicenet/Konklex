BOWS = {}

class Bow
  attr_reader :log, :item, :xp, :level

  def initialize(log, item, xp, level)
    @log = log
    @item = item
    @xp = xp
    @level = level
  end
end

def append_bow(bow)
  BOWS[bow.item] = bow
end

append_bow Bow.new(1511, 52, 5, 15)  # Shafts
append_bow Bow.new(1511, 841, 5, 5)  # Short
append_bow Bow.new(1511, 839, 10, 10)# Long
append_bow Bow.new(1521, 843, 17, 20)# OakS
append_bow Bow.new(1521, 845, 25, 25)# OakL
append_bow Bow.new(1519, 849, 34, 35)# WilS
append_bow Bow.new(1519, 847, 42, 40)# WilL
append_bow Bow.new(1517, 853, 50, 50)# MapS
append_bow Bow.new(1517, 851, 59, 55)# MapL
append_bow Bow.new(1515, 857, 68, 65)# YewS
append_bow Bow.new(1515, 855, 75, 70)# YewL
append_bow Bow.new(1513, 861, 84, 80)# MagS
append_bow Bow.new(1513, 859, 92, 87)# MagL