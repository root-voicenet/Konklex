require 'java'
java_import 'org.apollo.game.model.Position'

TALISMANS = {}

def append_talisman(id, position)
  TALISMANS[id] = position
end

append_talisman 1438, Position.new(2985, 3292)
append_talisman 1440, Position.new(3306, 3474)
append_talisman 1442, Position.new(3313, 3255)
append_talisman 1444, Position.new(3185, 3165)
append_talisman 1446, Position.new(3053, 3445)
append_talisman 1448, Position.new(2982, 3514)