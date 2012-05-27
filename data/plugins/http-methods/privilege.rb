## Defines a privilege level set.

require 'java'
java_import 'org.apollo.backend.method.impl.ResponseMethod'
java_import 'org.apollo.game.model.World'

on :http, :set_privilege_level do |ctx, channel, method|
  if method.get_key == "abcdef"
    user = method.get_player.sub("_", " ")
    other = World.get_world.get_player user
    success = other != nil
    channel.send ResponseMethod.new(ctx.get_requested, success ? "Executed" : "#{user} is offline", !success)
    if success
      other.set_privilege_level method.get_level
    end
  else
    channel.send ResponseMethod.new(ctx.get_requested, "Invalid key", true)
  end
end