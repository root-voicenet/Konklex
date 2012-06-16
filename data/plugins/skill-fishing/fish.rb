FISH = {}

class Fish
  attr_reader :id, :equipment, :bait, :animation, :option

  def initialize(id, equipment, bait, animation)
    @id = id
    @equipment = equipment
    @bait = bait
    @animation = animation
  end

  def set_option(option)
    @option = option
  end
end

class Button
  attr_reader :fish, :exp, :level

  def initialize(fish, exp, level)
    @fish = fish
    @exp = exp
    @level = level
  end
end

def append_fish(fish, options)
  FISH[fish.id] ||= {}
  options.each do |option, optionclazz|
    nf = Fish.new fish.id, fish.equipment, fish.bait, fish.animation
    nf.set_option optionclazz
    FISH[fish.id][option] = nf
    print "#{FISH[fish.id][option]}"
  end
end

LURE = {
  2 => Button.new(335, 50, 1), # level req 20
  3 => Button.new(331, 70, 1) # level req 30
}

append_fish Fish.new(309, 309, 314, 623), LURE