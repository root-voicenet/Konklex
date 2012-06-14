ITEMS = {}

class Item
  attr_reader :log, :bow, :model

  def initialize(log, bow, model)
    @log = log
    @bow = bow
    @model = model
  end
end

def append_item(item)
  ITEMS[item.log] = item
end

append_item Item.new(1511, 839, 841)
append_item Item.new(1521, 845, 843)
append_item Item.new(1519, 847, 849)
append_item Item.new(1517, 851, 853)
append_item Item.new(1515, 855, 857)
append_item Item.new(1513, 859, 861)