require 'java'
java_import 'org.apollo.game.model.World'

on :http, :send_yell do |ctx, channel, method|
  if method.get_key == "abcdef"
    World.push_notification "yell", "Backend: #{method.message}"
    World.get_world.get_player_repository.each do |player|
      player.send_message "[Owner] Backend: #{method.message}"
    end
  end
  channel.close
end