require 'java'
java_import 'org.apollo.game.model.Position'

ENTER_SARA_PORTAL = Position.new(2382, 9488, 0)
LEAVE_SARA_PORTAL = Position.new(2440, 3090, 0)
ENTER_ZAMM_PORTAL = Position.new(2423, 9522, 0)
LEAVE_ZAMM_PORTAL = Position.new(2440, 3090, 0)
SARA_GAME = Position.new(2427, 3076, 1)
ZAMM_GAME = Position.new(2372, 3131, 1)
SARA_TEAM_LOBBY = 1
SARA_TEAM_GAME = 2
ZAMM_TEAM_LOBBY = 3
ZAMM_TEAM_GAME = 4
GAME_LOBBY = Position.new(2440, 3090, 0)