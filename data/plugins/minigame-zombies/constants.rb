require 'java'
java_import 'org.apollo.game.model.Position'
java_import 'org.apollo.game.model.Graphic'

START = Position.new 3551, 9693, 0
PLACE = { Position.new(3556, 9690, 0),
	  Position.new(3555, 9599, 0),
	  Position.new(3547, 9699, 0),
	  Position.new(3547, 9690, 0)
        }
WAIT  = Position.new 2610, 3148, 0
SHOP  = Position.new 3553, 9695, 0
ZOMBIE = 77
CHEST = 6774
PLAYER_TEAM = 1
ZOMBIE_TEAM = 2
WAITING_TEAM = 3

NUKE = Graphic.new 346, 0, 100
