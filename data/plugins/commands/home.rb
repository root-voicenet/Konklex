require 'java'
java_import 'org.apollo.game.model.Position'

on :command, :home, RIGHTS_MOD do |player, command|
  player.teleport Position.new(3094, 3495), true
end