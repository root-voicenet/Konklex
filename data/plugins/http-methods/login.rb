## Defines a player login.

require 'java'
java_import 'org.apollo.backend.method.impl.ResponseMethod'
java_import 'org.apollo.game.model.World'

on :http, :login do |ctx, channel, method|
  player = method.player
  success = player != nil
  channel.send ResponseMethod.new(ctx.get_requested, success ? "#{player.privilege_level.to_integer}" : nil, !success)
end