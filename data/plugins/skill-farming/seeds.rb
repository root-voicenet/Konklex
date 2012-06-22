SEEDS = {}

class Seed
  attr_reader :id, :seed, :item, :exp, :level, :start, :water_start, :disease_start, :dead_start, :finish, :water_finish, :disease_finish, :dead_finish

  def initialize(id, seed, item, exp, level, start, water_start, disease_start, dead_start, finish, water_finish, disease_finish, dead_finish)
    @id = id
    @seed = seed
    @item = item
    @exp = exp
    @level = level
    @start = start
    @water_start = water_start
    @disease_start = disease_start
    @dead_start = dead_start
    @finish = finish
    @water_finish = water_finish
    @disease_finish = disease_finish
    @dead_finish = dead_finish
  end

end

def append_seed(seed)
  SEEDS[seed.seed] = seed
end

# Allotments
append_seed Seed.new(4, 5318, 1942, 8 ,   1,   6..9,   70..72, 135..137, 199..201, 10,  73, 137, 201) # Potato
append_seed Seed.new(4, 5319, 1957, 9 ,   5, 13..16,   77..79, 142..144, 206..208, 17,  80, 144, 208) # Onion
append_seed Seed.new(4, 5324, 1965, 10,  10, 20..23,   84..86, 149..151, 213..215, 24,  87, 151, 215) # Cabbage
append_seed Seed.new(4, 5322, 1982, 12,  12, 27..30,   91..93, 156..158, 220..222, 31,  94, 158, 222) # Tomato
append_seed Seed.new(4, 5320, 5986, 17,  20, 34..39,  98..102, 163..167, 227..231, 40, 103, 167, 231) # Sweetcorn
append_seed Seed.new(4, 5323, 5504, 26,  31, 43..48, 107..111, 172..176, 236..240, 49, 112, 176, 240) # Strawberry
append_seed Seed.new(4, 5321, 5982, 48,  47, 52..59, 116..122, 181..187, 245..251, 60, 123, 187, 251) # Watermelon

# Flowers
append_seed Seed.new(2, 5096, 6010,  8,   2,  8..11, 72..74, 137..139, 201..204, 12, 75, 139, 204) # Marigold
append_seed Seed.new(2, 5097, 6014, 12,  11, 13..16, 77..79, 142..144, 206..209, 17, 80, 144, 209) # Rosemary
append_seed Seed.new(2, 5098, 6012, 19,  24, 18..21, 82..84, 147..149, 211..214, 22, 85, 149, 214) # Nasturtium
append_seed Seed.new(2, 5099, 1793, 20,  25, 23..26, 87..89, 152..154, 216..219, 27, 90, 154, 219) # Woad
append_seed Seed.new(2, 5100,  255, 21,  26, 28..31, 92..94, 157..159, 221..224, 32, 95, 159, 224) # Limpwurt

# Herbs
append_seed Seed.new(3, 5291,  249, 11,   9,   4..7, nil, nil, nil,  8, -1, -1, -1)
append_seed Seed.new(3, 5292,  251, 13,  14, 11..15, nil, nil, nil, 16, -1, -1, -1)
append_seed Seed.new(3, 5293,  253, 16,  19, 18..21, nil, nil, nil, 22, -1, -1, -1)
append_seed Seed.new(3, 5294,  255, 21,  26, 25..28, nil, nil, nil, 29, -1, -1, -1)
append_seed Seed.new(3, 5295,  257, 27,  32, 32..36, nil, nil, nil, 37, -1, -1, -1)
append_seed Seed.new(3, 5296, 2998, 34,  38, 39..43, nil, nil, nil, 44, -1, -1, -1)
append_seed Seed.new(3, 5317,  259, 43,  44, 46..50, nil, nil, nil, 51, -1, -1, -1)
append_seed Seed.new(3, 5298,  261, 54,  50, 53..58, nil, nil, nil, 59, -1, -1, -1)
append_seed Seed.new(3, 5299,  263, 69,  56,   4..8, nil, nil, nil,  9, -1, -1, -1)
append_seed Seed.new(3, 5300, 3000, 87,  62, 11..16, nil, nil, nil, 17, -1, -1, -1)
append_seed Seed.new(3, 5301,  265, 106, 67, 18..22, nil, nil, nil, 23, -1, -1, -1)
append_seed Seed.new(3, 5302, 2481, 134, 73, 25..29, nil, nil, nil, 30, -1, -1, -1)
append_seed Seed.new(3, 5303,  267, 170, 79, 25..30, nil, nil, nil, 31, -1, -1, -1)
append_seed Seed.new(3, 5304,  269, 199, 85, 11..17, nil, nil, nil, 18, -1, -1, -1)

# Trees
append_seed Seed.new(1, 5312, 1521,  14 , 15,  8..12, nil,   73..75, 137..141,  13, -1,  75, 141) # Oak
append_seed Seed.new(1, 5313, 1519,  25 , 30, 15..21, nil,   80..86, 144..150,  22, -1,  86, 150) # Willow
append_seed Seed.new(1, 5314, 1517,  45 , 45, 24..32, nil,   89..97, 153..161,  33, -1,  97, 161) # Maple
append_seed Seed.new(1, 5315, 1515,  81 , 60, 35..45, nil, 100..110, 164..174,  46, -1, 110, 174) # Yew
append_seed Seed.new(1, 5316, 1513,  145, 75, 48..60, nil, 113..125, 177..189,  61, -1, 125, 189) # Magic