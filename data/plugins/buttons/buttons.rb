require 'java'
java_import 'org.apollo.game.model.Position'

on :button, 19210 do |player|
  player.teleport Position.new(3094, 3495), true
end

on :button, 21341 do |player|
  player.get_interface_set.open_window 21172
end

on :button, 21299 do |player|
  player.get_interface_set.close
end