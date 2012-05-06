require 'java'
java_import 'org.apollo.game.event.impl.SoundEvent'

on :command, :sound do |player, command|
  args = command.arguments
  if (1..2).include? args.length
    id = args[0].to_i

    player.play_sound id, true
  else
    player.send_message "Syntax: ::sound [id]"
  end
end