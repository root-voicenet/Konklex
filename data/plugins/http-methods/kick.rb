## Defines a player kick.

require 'java'
java_import 'org.apollo.backend.method.impl.ResponseMethod'
java_import 'org.apollo.game.model.World'

on :http, :kick do |ctx, channel, method|
  if method.get_key == "abcdef"
    user = method.get_player.sub("_", " ")
    other = World.get_world.get_player user
    success = other != nil
    channel.send ResponseMethod.new(ctx.get_requested, success ? "Executed" : "#{user} is offline", !success)
    if success
      other.logout
    end
  else
    channel.send ResponseMethod.new(ctx.get_requested, "Invalid key", true)
  end
end