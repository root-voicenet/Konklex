require 'java'
java_import 'org.apollo.game.model.Position'

LOBBY = 1
GAME = 2
LOBBY_ENTER = Position.new 2399, 5175, 0
LOBBY_LEAVE = Position.new 2399, 5177, 0
GAME_LEAVE  = Position.new 2399, 5169, 0
GAME_ENTER  = Position.new 2399, 5158, 0