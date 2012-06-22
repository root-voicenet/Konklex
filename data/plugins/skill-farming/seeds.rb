SEEDS = {}

class Seed
  attr_reader :id, :seed, :item, :exp, :level, :start, :finish, :water_start, :water_finish

  def initialize(id, seed, item, exp, level, start, finish, water_start, water_finish)
    @id = id
    @seed = seed
    @item = item
    @exp = exp
    @level = level
    @start = start
    @finish = finish
    @water_start = water_start
    @water_finish = water_finish
  end

end

def append_seed(seed)
  SEEDS[seed.seed] = seed
end

# Allotments
append_seed Seed.new(4, 5318, 1942, 8 ,   1, 6 , 10, 70 ,  73) # Potato
append_seed Seed.new(4, 5319, 1957, 9 ,   5, 13, 17, 77 ,  80) # Onion
append_seed Seed.new(4, 5324, 1965, 10,  10, 20, 24, 84 ,  87) # Cabbage
append_seed Seed.new(4, 5322, 1982, 12,  12, 27, 31, 91 ,  94) # Tomato
append_seed Seed.new(4, 5320, 5986, 17,  20, 34, 40, 98 , 103) # Sweetcorn
append_seed Seed.new(4, 5323, 5504, 26,  31, 43, 49, 107, 112) # Strawberry
append_seed Seed.new(4, 5321, 5982, 48,  47, 52, 60, 116, 123) # Watermelon

# Flowers
append_seed Seed.new(2, 5096, 6010,  8,   2, 8 , 12, 72, 75) # Marigold
append_seed Seed.new(2, 5097, 6014, 12,  11, 13, 17, 77, 80) # Rosemary
append_seed Seed.new(2, 5098, 6012, 19,  24, 18, 22, 82, 85) # Nasturtium
append_seed Seed.new(2, 5099, 1793, 20,  25, 23, 27, 87, 90) # Woad
append_seed Seed.new(2, 5100,  255, 21,  26, 28, 32, 92, 95) # Limpwurt

# Herbs
append_seed Seed.new(3, 5291,  249, 11,   9, 4 , 8 , -1, -1)
append_seed Seed.new(3, 5292,  251, 13,  14, 11, 16, -1, -1)
append_seed Seed.new(3, 5293,  253, 16,  19, 18, 22, -1, -1)
append_seed Seed.new(3, 5294,  255, 21,  26, 25, 29, -1, -1)
append_seed Seed.new(3, 5295,  257, 27,  32, 32, 37, -1, -1)
append_seed Seed.new(3, 5296, 2998, 34,  38, 39, 44, -1, -1)
append_seed Seed.new(3, 5317,  259, 43,  44, 46, 51, -1, -1)
append_seed Seed.new(3, 5298,  261, 54,  50, 53, 59, -1, -1)
append_seed Seed.new(3, 5299,  263, 69,  56, 4, 9, -1, -1)
append_seed Seed.new(3, 5300, 3000, 87,  62, 11, 17, -1, -1)
append_seed Seed.new(3, 5301,  265, 106, 67, 18, 23, -1, -1)
append_seed Seed.new(3, 5302, 2481, 134, 73, 25, 30, -1, -1)
append_seed Seed.new(3, 5303,  267, 170, 79, 25, 31, -1, -1)
append_seed Seed.new(3, 5304,  269, 199, 85, 11, 18, -1, -1)

# Trees
append_seed Seed.new(1, 5312, 1521,  14 , 15, 8 , 13, -1, -1) # Oak
append_seed Seed.new(1, 5313, 1519,  25 , 30, 15, 22, -1, -1) # Willow
append_seed Seed.new(1, 5314, 1517,  45 , 45, 24, 33, -1, -1) # Maple
append_seed Seed.new(1, 5315, 1515,  81 , 60, 35, 46, -1, -1) # Yew
append_seed Seed.new(1, 5316, 1513,  145, 75, 48, 61, -1, -1) # Magic