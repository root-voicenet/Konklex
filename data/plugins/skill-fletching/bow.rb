BOWS = {}

class Bow
  attr_reader :count, :item, :xp, :level

  def initialize(count, item, xp, level)
    @count = count
    @item = item
    @xp = xp
    @level = level
  end
end

def append_bow(log, buttons)
  BOWS[log] ||= {}
  buttons.each do |button, bow|
    BOWS[log][button] = bow
  end
end

LOG = {
  8897 => Bow.new(1, 52, 1, 1), # Shaft 1
  8896 => Bow.new(5, 52, 1, 1), # Shaft 5
  8895 => Bow.new(10, 52, 1, 1),# Shaft 10

  8893 => Bow.new(1, 48, 10, 10), # Long 1
  8892 => Bow.new(5, 48, 10, 10), # Long 2
  8891 => Bow.new(10, 48, 10, 10),# Long 3

  8889 => Bow.new(1, 50, 5, 1), # Short 1
  8889 => Bow.new(5, 50, 5, 1), # Short 5
  8889 => Bow.new(10, 50, 5, 1) # Short 10
}

OAK = {
  8878 => Bow.new(1, 54, 17, 20), # Short 1
  8877 => Bow.new(5, 54, 17, 20), # Short 5
  8876 => Bow.new(10, 54, 17, 20),# Short 10

  8874 => Bow.new(1, 56, 25, 25), # Long 1
  8873 => Bow.new(5, 56, 25, 25), # Long 5
  8872 => Bow.new(10, 56, 25, 25) # Long 10
}

WILLOW = {
  8874 => Bow.new(1, 58, 42, 40), # Long 1
  8873 => Bow.new(5, 58, 42, 40), # Long 5
  8872 => Bow.new(10, 58, 42, 40),# Long 10

  8878 => Bow.new(1, 60, 34, 35), # Short 1
  8877 => Bow.new(5, 60, 34, 35), # Short 5
  8876 => Bow.new(10, 60, 34, 35) # Short 10
}

MAPLE = {
  8874 => Bow.new(1, 62, 59, 55), # Long 1
  8873 => Bow.new(5, 62, 59, 55), # Long 5
  8872 => Bow.new(10, 62, 59, 55),# Long 10

  8878 => Bow.new(1, 64, 50, 50), # Short 1
  8877 => Bow.new(5, 64, 50, 50), # Short 5
  8876 => Bow.new(10, 64, 50, 50) # Short 10
}

YEW = {
  8874 => Bow.new(1, 66, 75, 70), # Long 1
  8873 => Bow.new(5, 66, 75, 70), # Long 5
  8872 => Bow.new(10, 66, 75, 70),# Long 10

  8878 => Bow.new(1, 68, 68, 65), # Short 1
  8877 => Bow.new(5, 68, 68, 65), # Short 5
  8876 => Bow.new(10, 68, 68, 65) # Short 10
}

MAGIC = {
  8874 => Bow.new(1, 70, 92, 87), # Long 1
  8873 => Bow.new(5, 70, 92, 87), # Long 5
  8872 => Bow.new(10, 70, 92, 87),# Long 10

  8878 => Bow.new(1, 72, 84, 80), # Short 1
  8877 => Bow.new(5, 72, 84, 80), # Short 5
  8876 => Bow.new(10, 72, 84, 80) # Short 10
}

append_bow 1511, LOG
append_bow 1521, OAK
append_bow 1519, WILLOW
append_bow 1517, MAPLE
append_bow 1515, YEW
append_bow 1513, MAGIC