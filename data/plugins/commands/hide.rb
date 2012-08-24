require 'java'

on :command, :hide, RIGHTS_DEV do |player, command|
  player.set_hide true
end

on :command, :show, RIGHTS_DEV do |player, command|
  player.set_hide false
end