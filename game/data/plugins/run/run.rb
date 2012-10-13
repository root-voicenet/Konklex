require 'java'

on :button, 153 do |player|
  player.get_walking_queue.set_running true
end

on :button, 152 do |player|
  player.get_walking_queue.set_running false
end

on :button, 19158 do |player|
  running = !player.get_walking_queue.get_running
  player.get_walking_queue.set_running running
end